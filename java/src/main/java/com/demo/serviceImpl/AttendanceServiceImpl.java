package com.demo.serviceImpl;

import com.demo.common.*;
import com.demo.dao.*;
import com.demo.entity.*;
import com.demo.dao.RequestMapper;
import com.demo.entity.AtndStting;
import com.demo.entity.AttendanceHistory;
import com.demo.entity.Category;
import com.demo.pojo.atndApply.AtndApplyRpnDTO;
import com.demo.pojo.atndApply.AtndApplySearchDTO;
import com.demo.pojo.atndStting.AtndSttingDTO;
import com.demo.pojo.atndStting.AtndSttingRpnDTO;
import com.demo.pojo.attendance.*;
import com.demo.pojo.attendance.ApplyAttendanceDTO;
import com.demo.pojo.attendance.AttendanceDTO;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.attendance.AttendanceTimeDTO;
import com.demo.pojo.common.LoginUserInfo;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;
import com.demo.pojo.requestAttendance.GetStatusAttendanceDTO;
import com.demo.pojo.user.UserDetailDTO;
import com.demo.service.IAttendanceService;
import com.demo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import net.sf.jasperreports.components.iconlabel.IconLabelElementRtfHandler;
import net.sf.jasperreports.engine.*;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.*;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.demo.entity.Attendance;
import org.springframework.util.ResourceUtils;

import static java.awt.Color.red;
import static javax.swing.UIManager.put;
/*
 * Author  : libin
   Time    : 2021/07/12
   Function: 勤務表処理
*/
@Service
@Slf4j
public class AttendanceServiceImpl implements IAttendanceService {

    @Autowired
    AttendanceMapper attendanceMapper;

    @Autowired
    AtndSttingMapper atndSttingMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    HolidayMapper holidayMapper;

    @Autowired
    AttendanceHistoryMapper attendanceHistoryMapper;

    @Autowired
    private UserMasterMapper userMasterMapper;

    @Autowired
    RequestMapper requestMapper;

    @Autowired
    RequestAttendanceMapper requestAttendanceMapper;


    /**
     * 勤務表詳細情報取得
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<List<AttendanceRPnDTO>> getDetail(AttendanceDTO attendanceDTO,
                                                            HttpServletRequest request, HttpServletResponse response) {
        // 会社コード
        String companyCode = attendanceDTO.getCompanyCode();
        // 用户ID
        String userId = attendanceDTO.getUserId();
        // 年月
        String workDateYm = attendanceDTO.getWorkDateYm();

        List<AttendanceRPnDTO> attendanceList = attendanceMapper.selectById(companyCode, userId, workDateYm);

        return ServerResponse.createBySuccess(attendanceList);
    }

    @Override
    public ServerResponse<List<ApplyAttendanceDTO>> selectByApply(AttendanceDTO attendanceDTO,
                                                              HttpServletRequest request, HttpServletResponse response) {
        // 会社コード
        String companyCode = attendanceDTO.getCompanyCode();
        // 用户ID
        String userId = attendanceDTO.getUserId();
        // 年月
        String workDateYm = attendanceDTO.getWorkDateYm();

        List<ApplyAttendanceDTO> applyAttendanceList = attendanceMapper.selectByApply(companyCode, userId, workDateYm);

        return ServerResponse.createBySuccess(applyAttendanceList);
    }

    @Override
    public ServerResponse<List<ApplyAttendanceDTO>> selectByApplyActivity(AttendanceDTO attendanceDTO,
                                                                          HttpServletRequest request, HttpServletResponse response) {
        // 会社コード
        String companyCode = attendanceDTO.getCompanyCode();
        // 用户ID
        String userId = attendanceDTO.getUserId();
        // 年月
        String workDateYm = attendanceDTO.getWorkDateYm();

        List<ApplyAttendanceDTO> applyAttendanceList = attendanceMapper.selectByApplyActivity(companyCode, userId, workDateYm);

        return ServerResponse.createBySuccess(applyAttendanceList);
    }

    /**
     * 勤務表検索
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<List<AttendanceRPnDTO>> search(AttendanceDTO attendanceDTO,
                                                        HttpServletRequest request, HttpServletResponse response) {


        return null;
    }

    /**
     * 勤務表登録
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<String> create(List<AttendanceRPnDTO> attendanceRPnDTOList,
                                         HttpServletRequest request, HttpServletResponse response) {


        SimpleDateFormat formatY = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        SimpleDateFormat formatH = new SimpleDateFormat("HH:mm");
        String dateYmd = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        for (AttendanceRPnDTO attendanceRPnDTO: attendanceRPnDTOList) {

            Attendance attendance = new Attendance();
            // 属性copy
            BeanCopier attendanceCopier = BeanCopier.create(AttendanceRPnDTO.class,
                    Attendance.class, false);
            attendanceCopier.copy(attendanceRPnDTO, attendance, null);
            String workDate = "";
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkDate()) && attendanceRPnDTO.getWorkDate().length() == 8){
                String date1 = attendanceRPnDTO.getWorkDate().substring(0,4);
                String date2 = attendanceRPnDTO.getWorkDate().substring(4,6);
                String date3 = attendanceRPnDTO.getWorkDate().substring(6);
                workDate = date1 + "/" + date2 + "/" + date3;
            }

            // 勤怠区分
            attendance.setWorkKbn(attendanceRPnDTO.getWorkKbn());
            // 出勤時間_1
            attendance.setWorkStart1(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkStart1(), new ParsePosition( 0 )));
            // 退勤時間_1
            attendance.setWorkEnd1(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkEnd1(), new ParsePosition( 0 )));
            // 出勤時間_2
            attendance.setWorkStart2(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkStart2(), new ParsePosition( 0 )));
            // 退勤時間_2
            attendance.setWorkEnd2(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkEnd2(), new ParsePosition( 0 )));
            // 出勤時間_3
            attendance.setWorkStart3(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkStart3(), new ParsePosition( 0 )));
            // 退勤時間_3
            attendance.setWorkEnd3(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkEnd3(), new ParsePosition( 0 )));
            // 労働時間
            attendance.setWorkTime(formatH.parse(attendanceRPnDTO.getWorkTime(),new ParsePosition( 0 )));
            // 休憩時間
            attendance.setWorkBreakTime1(formatH.parse(attendanceRPnDTO.getWorkBreakTime1(),new ParsePosition( 0 )));
            attendance.setWorkBreakTime2(formatH.parse(attendanceRPnDTO.getWorkBreakTime2(),new ParsePosition( 0 )));
            attendance.setWorkBreakTime3(formatH.parse(attendanceRPnDTO.getWorkBreakTime3(),new ParsePosition( 0 )));
            // 普通残業
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkOverTime())){
                attendance.setWorkOverTime(formatH.parse(attendanceRPnDTO.getWorkOverTime(),new ParsePosition( 0 )));
            }
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkHoliTime())){
                attendance.setWorkHoliTime(formatH.parse(attendanceRPnDTO.getWorkHoliTime(),new ParsePosition( 0 )));
            }
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkMidnightHoliTime())){
                attendance.setWorkMidnightHoliTime(formatH.parse(attendanceRPnDTO.getWorkMidnightHoliTime(),new ParsePosition( 0 )));
            }
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkMidnightTime())){
                attendance.setWorkMidnightTime(formatH.parse(attendanceRPnDTO.getWorkMidnightTime(),new ParsePosition( 0 )));
            }

            // 休憩合計時間
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkSumHoliTime())){
                attendance.setWorkSumHoliTime(formatH.parse(attendanceRPnDTO.getWorkSumHoliTime(),new ParsePosition( 0 )));
            }
            // 実働勤務合計時間
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkSumTime())){
                attendance.setWorkSumTime(formatH.parse(attendanceRPnDTO.getWorkSumTime(),new ParsePosition( 0 )));
            }

            attendance.setDelFlg(ConstCode.STRING_ZERO);

            Attendance attendanceCount = attendanceMapper.selectByPrimaryKey(attendanceRPnDTO.getCompanyCode(),
                    attendanceRPnDTO.getUserId(), attendanceRPnDTO.getWorkDate());
            LoginUserInfo loginUserInfo = userMasterMapper.getUserInfo(attendanceRPnDTO.getCompanyCode(),
                    attendanceRPnDTO.getUserId());
            AttendanceHistory attendanceHistory = new AttendanceHistory();
            // 属性copy
            BeanCopier attendanceHistoryCopier = BeanCopier.create(Attendance.class,
                    AttendanceHistory.class, false);
            attendanceHistoryCopier.copy(attendance, attendanceHistory, null);
            String historyNo = attendanceHistoryMapper.selectNo(attendanceRPnDTO.getCompanyCode(),
                    attendanceRPnDTO.getUserId(), attendanceRPnDTO.getWorkDate());
            int no = Integer.parseInt(historyNo) + 1;
            if (no < 10){
                historyNo = "0" + no;
            }else {
                historyNo = "" + no;
            }
            attendanceHistory.setHistoryNo(historyNo);
            if (!"100".equals(loginUserInfo.getPermissionKbn())){
                // 登録日時
                attendanceHistory.setRegTime(new Date());
                // 登録者
                attendanceHistory.setRegAccount(attendance.getUserId());
                // 更新日時
                attendanceHistory.setUpdTime(new Date());
                // 更新者
                attendanceHistory.setUpdAccount(attendance.getUserId());

                attendanceHistoryMapper.insert(attendanceHistory);
            }
            if (attendanceCount != null) {

                // 更新日時
                attendance.setUpdTime(new Date());
                // 更新者
                attendance.setUpdAccount(attendance.getUserId());

                attendanceMapper.updateByPrimaryKey(attendance);
            } else {

                // 登録日時
                attendance.setRegTime(new Date());
                // 登録者
                attendance.setRegAccount(attendance.getUserId());
                // 更新日時
                attendance.setUpdTime(new Date());
                // 更新者
                attendance.setUpdAccount(attendance.getUserId());

                attendanceMapper.insert(attendance);
            }
        }
        return ServerResponse.createBySuccess();
    }
    /**
     * 勤務表登録　更新
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<String> attendanceCreateUpdate (List<AttendanceRPnDTO> attendanceRPnDTOList,
                                                          HttpServletRequest request, HttpServletResponse response) {


        SimpleDateFormat formatY = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        SimpleDateFormat formatH = new SimpleDateFormat("HH:mm");
        String dateYmd = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        for (AttendanceRPnDTO attendanceRPnDTO: attendanceRPnDTOList) {

            Attendance attendance = new Attendance();
            // 属性copy
            BeanCopier attendanceCopier = BeanCopier.create(AttendanceRPnDTO.class,
                    Attendance.class, false);
            attendanceCopier.copy(attendanceRPnDTO, attendance, null);
            String workDate = "";
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkDate()) && attendanceRPnDTO.getWorkDate().length() == 8){
                String date1 = attendanceRPnDTO.getWorkDate().substring(0,4);
                String date2 = attendanceRPnDTO.getWorkDate().substring(4,6);
                String date3 = attendanceRPnDTO.getWorkDate().substring(6);
                workDate = date1 + "/" + date2 + "/" + date3;
            }

            // 勤怠区分
            attendance.setWorkKbn(attendanceRPnDTO.getWorkKbn());
            // 出勤時間_1
            attendance.setWorkStart1(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkStart1(), new ParsePosition( 0 )));
            // 退勤時間_1
            attendance.setWorkEnd1(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkEnd1(), new ParsePosition( 0 )));
            // 出勤時間_2
            attendance.setWorkStart2(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkStart2(), new ParsePosition( 0 )));
            // 退勤時間_2
            attendance.setWorkEnd2(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkEnd2(), new ParsePosition( 0 )));
            // 出勤時間_3
            attendance.setWorkStart3(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkStart3(), new ParsePosition( 0 )));
            // 退勤時間_3
            attendance.setWorkEnd3(formatY.parse(workDate + " " +attendanceRPnDTO.getWorkEnd3(), new ParsePosition( 0 )));
            // 労働時間
            attendance.setWorkTime(formatH.parse(attendanceRPnDTO.getWorkTime(),new ParsePosition( 0 )));
            // 休憩時間
            attendance.setWorkBreakTime1(formatH.parse(attendanceRPnDTO.getWorkBreakTime1(),new ParsePosition( 0 )));
            attendance.setWorkBreakTime2(formatH.parse(attendanceRPnDTO.getWorkBreakTime2(),new ParsePosition( 0 )));
            attendance.setWorkBreakTime3(formatH.parse(attendanceRPnDTO.getWorkBreakTime3(),new ParsePosition( 0 )));
            // 普通残業
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkOverTime())){
                attendance.setWorkOverTime(formatH.parse(attendanceRPnDTO.getWorkOverTime(),new ParsePosition( 0 )));
            }
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkHoliTime())){
                attendance.setWorkHoliTime(formatH.parse(attendanceRPnDTO.getWorkHoliTime(),new ParsePosition( 0 )));
            }
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkMidnightHoliTime())){
                attendance.setWorkMidnightHoliTime(formatH.parse(attendanceRPnDTO.getWorkMidnightHoliTime(),new ParsePosition( 0 )));
            }
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkMidnightTime())){
                attendance.setWorkMidnightTime(formatH.parse(attendanceRPnDTO.getWorkMidnightTime(),new ParsePosition( 0 )));
            }

            // 休憩合計時間
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkSumHoliTime())){
                attendance.setWorkSumHoliTime(formatH.parse(attendanceRPnDTO.getWorkSumHoliTime(),new ParsePosition( 0 )));
            }
            // 実働勤務合計時間
            if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkSumTime())){
                attendance.setWorkSumTime(formatH.parse(attendanceRPnDTO.getWorkSumTime(),new ParsePosition( 0 )));
            }

            attendance.setDelFlg(ConstCode.STRING_ZERO);

            Attendance attendanceCount = attendanceMapper.selectByPrimaryKey(attendanceRPnDTO.getCompanyCode(),
                    attendanceRPnDTO.getUserId(), attendanceRPnDTO.getWorkDate());
            LoginUserInfo loginUserInfo = userMasterMapper.getUserInfo(attendanceRPnDTO.getCompanyCode(),
                    attendanceRPnDTO.getUserId());

            if (attendanceCount != null) {

                // 更新日時
                attendance.setUpdTime(new Date());
                // 更新者
                attendance.setUpdAccount(attendance.getUserId());

                attendanceMapper.updateByPrimaryKey(attendance);
            } else {

                // 登録日時
                attendance.setRegTime(new Date());
                // 登録者
                attendance.setRegAccount(attendance.getUserId());
                // 更新日時
                attendance.setUpdTime(new Date());
                // 更新者
                attendance.setUpdAccount(attendance.getUserId());

                attendanceMapper.insert(attendance);
            }
        }
        return ServerResponse.createBySuccess();
    }
    /**
     * 勤務表　上司承認
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<String> aprove(AttendanceRPnDTO attendanceRPnDTO,
                                         HttpServletRequest request, HttpServletResponse response) {

        Attendance attendance = new Attendance();
        // 属性copy
        BeanCopier attendanceCopier = BeanCopier.create(AttendanceRPnDTO.class,
                Attendance.class, false);
        attendanceCopier.copy(attendanceRPnDTO, attendance, null);

        // 更新日時
        attendance.setUpdTime(new Date());
        // 更新者
        attendance.setUpdAccount(attendance.getUserId());

        int result = attendanceMapper.updateByPrimaryKey(attendance);

        if (result == 0) {
            ServerResponse.createByError();
        }

        return ServerResponse.createBySuccess();
    }

    /**
     * 出勤登録
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<String> workStart(AttendanceDTO attendanceDTO,
                                            HttpServletRequest request, HttpServletResponse response) {

        // 会社コード
        String companyCode = attendanceDTO.getCompanyCode();
        // 用户ID
        String userId = attendanceDTO.getUserId();

        // システム日付
        Date dateNow = new Date();
        String dateYmd = new SimpleDateFormat("yyyyMMdd").format(dateNow);
        String dateYmd1 = new SimpleDateFormat("yyyy/MM/dd").format(dateNow);
        DateFormat formatY = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        // 同日判断
        Category category = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "011", "001");
        String categoryName = category.getCategoryKbnName();
        if (dateNow.after(formatY.parse(dateYmd1 + " " + "00:00", new ParsePosition( 0 )))
                && dateNow.before(formatY.parse(dateYmd1 + " " + categoryName, new ParsePosition( 0 )))){
            Calendar calendarNew = Calendar.getInstance();
            calendarNew.setTime(dateNow);
            calendarNew.add(Calendar.DAY_OF_MONTH, -1);
            Date dBefore = calendarNew.getTime();
            dateYmd = new SimpleDateFormat("yyyyMMdd").format(dBefore);
        }
        // 勤怠設定データ取得処理
        AtndSttingRpnDTO atndStting = atndSttingMapper.searchData(companyCode, userId);
        Attendance attendanceStart =  attendanceMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(),attendanceDTO.getUserId(),dateYmd);
        // 出退勤回数
        Category category1 = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "012", "001");
        String categoryName1 = category1.getCategoryKbnName();
        if ("1".equals(categoryName1)){
            // 対象日データなし
            if (attendanceStart == null){
                workStartCreat(attendanceDTO);
                return ServerResponse.createBySuccess();
            } else {
                if (attendanceStart.getWorkEnd1() != null && (attendanceStart.getWorkEnd1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 )))
                        || attendanceStart.getWorkEnd1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 ))))){
                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0006.getCode(),MsgCodeEnum.I_0006.getMessage());
                } else {
                    if (attendanceStart.getWorkStart1() != null && (attendanceStart.getWorkStart1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 )))
                            || attendanceStart.getWorkStart1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 ))))){
                        return ServerResponse.createBySuccess(MsgCodeEnum.I_0001.getCode(),MsgCodeEnum.I_0001.getMessage());
                    } else {
                        attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0001.getCode());
                        workStartCreat(attendanceDTO);
                        return ServerResponse.createBySuccess();
                    }
                }
            }
        } else if ("2".equals(categoryName1)){
            // 対象日データなし
            if (attendanceStart == null){
                workStartCreat(attendanceDTO);
                return ServerResponse.createBySuccess();
            } else {
                if (attendanceStart.getWorkEnd2() != null){
                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0006.getCode(),MsgCodeEnum.I_0006.getMessage());
                } else {
                    if (attendanceStart.getWorkStart2() != null){
                        return ServerResponse.createBySuccess(MsgCodeEnum.I_0003.getCode(),MsgCodeEnum.I_0003.getMessage());
                    } else {
                        if (attendanceStart.getWorkEnd1() != null && (attendanceStart.getWorkEnd1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 )))
                                || attendanceStart.getWorkEnd1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 ))))){
                            return ServerResponse.createBySuccess(MsgCodeEnum.I_0002.getCode(),MsgCodeEnum.I_0002.getMessage());
                        } else {
                            if (attendanceStart.getWorkStart1() != null && (attendanceStart.getWorkStart1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 )))
                                    || attendanceStart.getWorkStart1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 ))))){
                                return ServerResponse.createBySuccess(MsgCodeEnum.I_0001.getCode(),MsgCodeEnum.I_0001.getMessage());
                            } else {
                                attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0001.getCode());
                                workStartCreat(attendanceDTO);
                                return ServerResponse.createBySuccess();
                            }
                        }
                    }
                }
            }
        } else {
            // 対象日データなし
            if (attendanceStart == null){
                workStartCreat(attendanceDTO);
                return ServerResponse.createBySuccess();
            } else {
                if (attendanceStart.getWorkEnd3() != null){
                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0006.getCode(),MsgCodeEnum.I_0006.getMessage());
                } else {
                    if (attendanceStart.getWorkStart3() != null){
                        return ServerResponse.createBySuccess(MsgCodeEnum.I_0005.getCode(),MsgCodeEnum.I_0005.getMessage());
                    } else {
                        if (attendanceStart.getWorkEnd2() != null){
                            return ServerResponse.createBySuccess(MsgCodeEnum.I_0004.getCode(),MsgCodeEnum.I_0004.getMessage());
                        } else {
                            if (attendanceStart.getWorkStart2() != null){
                                return ServerResponse.createBySuccess(MsgCodeEnum.I_0003.getCode(),MsgCodeEnum.I_0003.getMessage());
                            } else {
                                if (attendanceStart.getWorkEnd1() != null && (attendanceStart.getWorkEnd1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 )))
                                        || attendanceStart.getWorkEnd1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 ))))){
                                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0002.getCode(),MsgCodeEnum.I_0002.getMessage());
                                } else {
                                    if (attendanceStart.getWorkStart1() != null && (attendanceStart.getWorkStart1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 )))
                                            || attendanceStart.getWorkStart1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 ))))){
                                        return ServerResponse.createBySuccess(MsgCodeEnum.I_0001.getCode(),MsgCodeEnum.I_0001.getMessage());
                                    } else {
                                        attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0001.getCode());
                                        workStartCreat(attendanceDTO);
                                        return ServerResponse.createBySuccess();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void workStartCreat(AttendanceDTO attendanceDTO) {
        // 会社コード
        String companyCode = attendanceDTO.getCompanyCode();
        // 用户ID
        String userId = attendanceDTO.getUserId();
        // システム日付
        Date dateNow = new Date();
        String dateYm = new SimpleDateFormat("yyyyMM").format(dateNow);
        String dateYmd = new SimpleDateFormat("yyyyMMdd").format(dateNow);
        String dateYmd1 = new SimpleDateFormat("yyyy/MM/dd").format(dateNow);
        DateFormat formatY = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        DateFormat formatH = new SimpleDateFormat("HH:mm");
        // 曜日取得処理
        String[] week_name = {"日", "月", "火", "水", "木", "金", "土"};

        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String workDateWeek = week_name[week];
        // 同日判断
        Category category = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "011", "001");
        String categoryName = category.getCategoryKbnName();
        if (dateNow.after(formatY.parse(dateYmd1 + " " + "00:00", new ParsePosition( 0 )))
                && dateNow.before(formatY.parse(dateYmd1 + " " + categoryName, new ParsePosition( 0 )))){
            Calendar calendarNew = Calendar.getInstance();
            calendarNew.setTime(dateNow);
            calendarNew.add(Calendar.DAY_OF_MONTH, -1);
            Date dBefore = calendarNew.getTime();
            dateYmd = new SimpleDateFormat("yyyyMMdd").format(dBefore);
            dateYmd1 = new SimpleDateFormat("yyyy/MM/dd").format(dBefore);
            workDateWeek = week_name[week-1];
        }
        // 勤怠設定データ取得処理
        AtndSttingRpnDTO atndStting = atndSttingMapper.searchData(companyCode, userId);

        Attendance attendanceStart =  attendanceMapper.selectByPrimaryKey(companyCode,userId,dateYmd);
        Attendance attendance = new Attendance();
        if (attendanceStart != null){
            BeanCopier attendanceCopier = BeanCopier.create(Attendance.class,
                    Attendance.class, false);
            attendanceCopier.copy(attendanceStart, attendance, null);
        }
        if (MsgCodeEnum.I_0001.getCode().equals(attendanceDTO.getWorkDateYm())){
            // 出勤時間_1
            attendance.setWorkStart1(dateNow);
            // ＴＣ出勤時間_1
            attendance.setWorkTcStart1(dateNow);
        } else if (MsgCodeEnum.I_0002.getCode().equals(attendanceDTO.getWorkDateYm())
                || MsgCodeEnum.I_0003.getCode().equals(attendanceDTO.getWorkDateYm())){
            // 出勤時間_2
            attendance.setWorkStart2(dateNow);
            // ＴＣ出勤時間_2
            attendance.setWorkTcStart2(dateNow);
        } else if (MsgCodeEnum.I_0004.getCode().equals(attendanceDTO.getWorkDateYm())
                || MsgCodeEnum.I_0005.getCode().equals(attendanceDTO.getWorkDateYm())){
            // 出勤時間_3
            attendance.setWorkStart3(dateNow);
            // ＴＣ出勤時間_3
            attendance.setWorkTcStart3(dateNow);
        } else {
            // 会社コード
            attendance.setCompanyCode(companyCode);
            // ユーザーID
            attendance.setUserId(userId);
            // 日付
            attendance.setWorkDate(dateYmd);
            // 年月
            attendance.setWorkDateYm(dateYm);
            // 曜日
            attendance.setWorkDateWeek(workDateWeek);
            // 勤怠区分
            int count = holidayMapper.selectByDate(companyCode,dateYmd);
            Date workEndTime = formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 ));
            if (count != 0 || "土".equals(workDateWeek) || "日".equals(workDateWeek)){
                attendance.setWorkKbn("002");
            } else if (dateNow.after(workEndTime)) {
                attendance.setWorkKbn("004");
            } else {
                attendance.setWorkKbn("001");
            }
            // 出勤時間_1
            attendance.setWorkStart1(dateNow);
            // 退勤時間_1
//            attendance.setWorkEnd1(workEndTime);
            // ＴＣ出勤時間_1
            attendance.setWorkTcStart1(dateNow);

            // 労働時間
            attendance.setWorkTime(formatH.parse(atndStting.getWorkPrescribedTime(), new ParsePosition( 0 )));

            attendance.setDelFlg("0");
            attendance.setRegAccount(userId);
            attendance.setRegTime(dateNow);
            attendance.setUpdAccount(userId);
            attendance.setUpdTime(dateNow);
            attendanceMapper.insert(attendance);
        }
        attendance.setUpdAccount(userId);
        attendance.setUpdTime(dateNow);
        attendanceMapper.updateByPrimaryKey(attendance);
    }
    /**
     * 退勤登録
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<String> workEnd(AttendanceDTO attendanceDTO,
                                            HttpServletRequest request, HttpServletResponse response) {

        // 会社コード
        String companyCode = attendanceDTO.getCompanyCode();
        // 用户ID
        String userId = attendanceDTO.getUserId();
        // システム日付
        Date dateNow = new Date();
        String dateYmd = new SimpleDateFormat("yyyyMMdd").format(dateNow);
        String dateYmd1 = new SimpleDateFormat("yyyy/MM/dd").format(dateNow);
        DateFormat formatY = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        // 同日判断
        Category category1 = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "011", "001");
        String categoryName = category1.getCategoryKbnName();
        if (dateNow.after(formatY.parse(dateYmd1 + " " + "00:00", new ParsePosition( 0 )))
                && dateNow.before(formatY.parse(dateYmd1 + " " + categoryName, new ParsePosition( 0 )))){
            Calendar calendarNew = Calendar.getInstance();
            calendarNew.setTime(dateNow);
            calendarNew.add(Calendar.DAY_OF_MONTH, -1);
            Date dBefore = calendarNew.getTime();
            dateYmd = new SimpleDateFormat("yyyyMMdd").format(dBefore);
        }
        // 勤怠設定データ取得処理
        AtndSttingRpnDTO atndStting = atndSttingMapper.searchData(companyCode, userId);
        // 出勤データ取得
        Attendance attendanceEnd =  attendanceMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(),attendanceDTO.getUserId(),dateYmd);
        // 出退勤回数
        Category category = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "012", "001");
        String categoryName1 = category.getCategoryKbnName();
        if ("1".equals(categoryName1)){
            // 対象日データなし
            if (attendanceEnd == null){
                return ServerResponse.createBySuccess(MsgCodeEnum.I_0007.getCode(),MsgCodeEnum.I_0007.getMessage());
            } else {
                if (attendanceEnd.getWorkEnd1() != null && (attendanceEnd.getWorkEnd1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 )))
                        || attendanceEnd.getWorkEnd1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 ))))){
                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0010.getCode(),MsgCodeEnum.I_0010.getMessage());
                } else {
                    if (attendanceEnd.getWorkStart1() != null && (attendanceEnd.getWorkStart1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 )))
                            || attendanceEnd.getWorkStart1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 ))))){
                        attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0010.getCode());
                        int i = workEndCreat(attendanceDTO);
                        if (i == 1){
                            return ServerResponse.createByBizError("退勤時間は出勤時間よりも早いです。手入力してください。");
                        }
                        return ServerResponse.createBySuccess();
                    } else {
                        if (attendanceEnd.getWorkTcStart1() != null){
                            attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0010.getCode());
                            int i = workEndCreat(attendanceDTO);
                            if (i == 1){
                                return ServerResponse.createByBizError("退勤時間は出勤時間よりも早いです。手入力してください。");
                            }
                            return ServerResponse.createBySuccess();
                        } else {
                            return ServerResponse.createBySuccess(MsgCodeEnum.I_0007.getCode(),MsgCodeEnum.I_0007.getMessage());
                        }
                    }
                }
            }
        } else if ("2".equals(categoryName1)){
            if (attendanceEnd == null){
                return ServerResponse.createBySuccess(MsgCodeEnum.I_0007.getCode(),MsgCodeEnum.I_0007.getMessage());
            } else {
                if (attendanceEnd.getWorkEnd2() != null){
                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0009.getCode(),MsgCodeEnum.I_0009.getMessage());
                } else {
                    if (attendanceEnd.getWorkStart2() != null){
                        attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0009.getCode());
                        int i = workEndCreat(attendanceDTO);
                        if (i == 1){
                            return ServerResponse.createByBizError("退職時間は出席時間よりも早いです。手入力してください。");
                        }
                        return ServerResponse.createBySuccess();
                    } else {
                        if (attendanceEnd.getWorkEnd1() != null && (attendanceEnd.getWorkEnd1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 )))
                                || attendanceEnd.getWorkEnd1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 ))))){
                            return ServerResponse.createBySuccess(MsgCodeEnum.I_0010.getCode(),MsgCodeEnum.I_0010.getMessage());
                        } else {
                            if (attendanceEnd.getWorkStart1() != null && (attendanceEnd.getWorkStart1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 )))
                                    || attendanceEnd.getWorkStart1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 ))))){
                                attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0010.getCode());
                                int i = workEndCreat(attendanceDTO);
                                if (i == 1){
                                    return ServerResponse.createByBizError("退勤時間は出勤時間よりも早いです。手入力してください。");
                                }
                                return ServerResponse.createBySuccess();
                            } else {
                                if (attendanceEnd.getWorkTcStart1() != null){
                                    attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0010.getCode());
                                    int i = workEndCreat(attendanceDTO);
                                    if (i == 1){
                                        return ServerResponse.createByBizError("退勤時間は出勤時間よりも早いです。手入力してください。");
                                    }
                                    return ServerResponse.createBySuccess();
                                } else {
                                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0007.getCode(),MsgCodeEnum.I_0007.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // 対象日データなし
            if (attendanceEnd == null){
                return ServerResponse.createBySuccess(MsgCodeEnum.I_0007.getCode(),MsgCodeEnum.I_0007.getMessage());
            } else {
                if (attendanceEnd.getWorkEnd3() != null){
                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0008.getCode(),MsgCodeEnum.I_0008.getMessage());
                } else {
                    if (attendanceEnd.getWorkStart3() != null){
                        attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0008.getCode());
                        int i = workEndCreat(attendanceDTO);
                        if (i == 1){
                            return ServerResponse.createByBizError("退職時間は出席時間よりも早いです。手入力してください。");
                        }
                        return ServerResponse.createBySuccess();
                    } else {
                        if (attendanceEnd.getWorkEnd2() != null){
                            return ServerResponse.createBySuccess(MsgCodeEnum.I_0009.getCode(),MsgCodeEnum.I_0009.getMessage());
                        } else {
                            if (attendanceEnd.getWorkStart2() != null){
                                attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0009.getCode());
                                int i = workEndCreat(attendanceDTO);
                                if (i == 1){
                                    return ServerResponse.createByBizError("退職時間は出席時間よりも早いです。手入力してください。");
                                }
                                return ServerResponse.createBySuccess();
                            } else {
                                if (attendanceEnd.getWorkEnd1() != null && (attendanceEnd.getWorkEnd1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 )))
                                        || attendanceEnd.getWorkEnd1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkEndTime(), new ParsePosition( 0 ))))){
                                    return ServerResponse.createBySuccess(MsgCodeEnum.I_0010.getCode(),MsgCodeEnum.I_0010.getMessage());
                                } else {
                                    if (attendanceEnd.getWorkStart1() != null && (attendanceEnd.getWorkStart1().after(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 )))
                                            || attendanceEnd.getWorkStart1().before(formatY.parse(dateYmd1 + " " + atndStting.getWorkStartTime(), new ParsePosition( 0 ))))){
                                        attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0010.getCode());
                                        int i = workEndCreat(attendanceDTO);
                                        if (i == 1){
                                            return ServerResponse.createByBizError("退勤時間は出勤時間よりも早いです。手入力してください。");
                                        }
                                        return ServerResponse.createBySuccess();
                                    } else {
                                        if (attendanceEnd.getWorkTcStart1() != null){
                                            attendanceDTO.setWorkDateYm(MsgCodeEnum.I_0010.getCode());
                                            int i = workEndCreat(attendanceDTO);
                                            if (i == 1){
                                                return ServerResponse.createByBizError("退勤時間は出勤時間よりも早いです。手入力してください。");
                                            }
                                            return ServerResponse.createBySuccess();
                                        } else {
                                            return ServerResponse.createBySuccess(MsgCodeEnum.I_0007.getCode(),MsgCodeEnum.I_0007.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ServerResponse<String> workStartSetting(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response) {
        workStartCreat(attendanceDTO);
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<String> workEndSetting(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response) {
        int i = workEndCreat(attendanceDTO);
        if (i == 1){
            return ServerResponse.createByBizError("退勤時間は出勤時間よりも早いです。手入力してください。");
        }
        return ServerResponse.createBySuccess();
    }

    public int workEndCreat(AttendanceDTO attendanceDTO) {

        // 会社コード
        String companyCode = attendanceDTO.getCompanyCode();
        // 用户ID
        String userId = attendanceDTO.getUserId();
        // システム日付
        Date dateNow = new Date();
        String dateYm = new SimpleDateFormat("yyyyMM").format(dateNow);
        String dateYmd = new SimpleDateFormat("yyyyMMdd").format(dateNow);
        String dateYmd1 = new SimpleDateFormat("yyyy/MM/dd").format(dateNow);
        String dateYmd2 = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(dateNow);
        DateFormat formatY = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        DateFormat formatH = new SimpleDateFormat("HH:mm");

        // 同日判断
        Category category1 = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "011", "001");
        String categoryName = category1.getCategoryKbnName();
        if (dateNow.after(formatY.parse(dateYmd1 + " " + "00:00", new ParsePosition( 0 )))
                && dateNow.before(formatY.parse(dateYmd1 + " " + categoryName, new ParsePosition( 0 )))){
            Calendar calendarNew = Calendar.getInstance();
            calendarNew.setTime(dateNow);
            calendarNew.add(Calendar.DAY_OF_MONTH, -1);
            Date dBefore = calendarNew.getTime();
            dateYmd = new SimpleDateFormat("yyyyMMdd").format(dBefore);
        }
        // 出勤データ取得
        Attendance attendanceEnd =  attendanceMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(),attendanceDTO.getUserId(),dateYmd);
        if (attendanceEnd != null){
            Attendance attendance = new Attendance();
            BeanCopier attendanceCopier = BeanCopier.create(Attendance.class,
                    Attendance.class, false);
            attendanceCopier.copy(attendanceEnd, attendance, null);

            // 勤怠設定データ取得処理
            AtndSttingRpnDTO atndStting = atndSttingMapper.searchData(companyCode, userId);

            if (MsgCodeEnum.I_0008.getCode().equals(attendanceDTO.getWorkDateYm())){
                if (formatY.parse(dateYmd2, new ParsePosition( 0 )).before(attendanceEnd.getWorkStart3())){
                    return 1;
                }
                // 退勤時間_3
                attendance.setWorkEnd3(formatY.parse(dateYmd2, new ParsePosition( 0 )));
                // TC退勤時間_3
                attendance.setWorkTcEnd3(formatY.parse(dateYmd2, new ParsePosition( 0 )));
            } else if (MsgCodeEnum.I_0009.getCode().equals(attendanceDTO.getWorkDateYm())){
                if (formatY.parse(dateYmd2, new ParsePosition( 0 )).before(attendanceEnd.getWorkStart2())){
                    return 1;
                }
                // 退勤時間_2
                attendance.setWorkEnd2(formatY.parse(dateYmd2, new ParsePosition( 0 )));
                // TC退勤時間_2
                attendance.setWorkTcEnd2(formatY.parse(dateYmd2, new ParsePosition( 0 )));
            } else if (MsgCodeEnum.I_0010.getCode().equals(attendanceDTO.getWorkDateYm())){
                if (formatY.parse(dateYmd2, new ParsePosition( 0 )).before(attendanceEnd.getWorkStart1())){
                    return 1;
                }
                // 退勤時間_1
                attendance.setWorkEnd1(formatY.parse(dateYmd2, new ParsePosition( 0 )));
                // TC退勤時間_1
                attendance.setWorkTcEnd1(formatY.parse(dateYmd2, new ParsePosition( 0 )));
            }
            attendanceMapper.updateByPrimaryKey(attendance);
            AttendanceTimeDTO attendanceTime =  attendanceMapper.selectTime(attendanceDTO.getCompanyCode(),attendanceDTO.getUserId(),dateYmd);
            // 労働時間
            attendance.setWorkTime(formatH.parse(atndStting.getWorkPrescribedTime(), new ParsePosition( 0 )));
            long workTime1 = 0;
            long workTime2 = 0;
            long workTime3 = 0;
            long time1 = 0;
            long time2 = 0;
            long time3 = 0;
            Date workBreakStart1 = formatY.parse(dateYmd1 + " " + atndStting.getWorkBreakStart1(), new ParsePosition( 0 ));
            Date workBreakStart2 = formatY.parse(dateYmd1 + " " + atndStting.getWorkBreakStart2(), new ParsePosition( 0 ));
            Date workBreakStart3 = formatY.parse(dateYmd1 + " " + atndStting.getWorkBreakStart3(), new ParsePosition( 0 ));
            Date workBreakEnd1 = formatY.parse(dateYmd1 + " " + atndStting.getWorkBreakEnd1(), new ParsePosition( 0 ));
            Date workBreakEnd2 = formatY.parse(dateYmd1 + " " + atndStting.getWorkBreakEnd2(), new ParsePosition( 0 ));
            Date workBreakEnd3 = formatY.parse(dateYmd1 + " " + atndStting.getWorkBreakEnd3(), new ParsePosition( 0 ));

            // 休憩時間
            if (!StringUtils.isEmpty(attendanceTime.getWorkStart1()) && !StringUtils.isEmpty(attendanceTime.getWorkEnd1())) {
                Date workStart1 = formatY.parse(attendanceTime.getWorkStart1().replace("-","/"), new ParsePosition( 0 ));
                Date workEnd1 = formatY.parse(attendanceTime.getWorkEnd1().replace("-","/"), new ParsePosition( 0 ));
                if((workBreakStart1 != null && workBreakEnd1 != null) && attendance.getWorkStart1().before( workBreakStart1)
                        && attendance.getWorkEnd1().after(workBreakEnd1)){
                    time1 = formatH.parse(atndStting.getWorkBreakEnd1(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart1(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime1(formatH.parse(formatTime(time1), new ParsePosition( 0 )));
                }
                if(workBreakEnd2 != null && workBreakStart2 != null && attendance.getWorkStart1().before( workBreakStart2)
                        && attendance.getWorkEnd1().after(workBreakEnd2)){
                    time2 = formatH.parse(atndStting.getWorkBreakEnd2(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart2(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime2(formatH.parse(formatTime(time2), new ParsePosition( 0 )));
                }
                if(workBreakEnd3 != null && workBreakStart3 != null && attendance.getWorkStart1().before( workBreakStart3)
                        && attendance.getWorkEnd1().after(workBreakEnd3) ){
                    time3 = formatH.parse(atndStting.getWorkBreakEnd3(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart3(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime3(formatH.parse(formatTime(time3), new ParsePosition( 0 )));
                }
                if (workBreakEnd1 != null && workBreakStart1 != null && attendance.getWorkStart1().before(workBreakStart1)
                        && attendance.getWorkEnd1().before(workBreakEnd1)
                        && attendance.getWorkEnd1().after(workBreakStart1)){
                    time1 = formatH.parse(formatH.format(workEnd1), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart1(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime1(formatH.parse(formatTime(time1), new ParsePosition( 0 )));
                }
                if (workBreakEnd2 != null && workBreakStart2 != null && attendance.getWorkStart1().before(workBreakStart2)
                        && attendance.getWorkEnd1().before(workBreakEnd2)
                        && attendance.getWorkEnd1().after(workBreakStart2)){
                    time2 = formatH.parse(formatH.format(workEnd1), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart2(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime2(formatH.parse(formatTime(time2), new ParsePosition( 0 )));
                }
                if (workBreakEnd3 != null && workBreakStart3 != null && attendance.getWorkStart1().before(workBreakStart3)
                        && attendance.getWorkEnd1().before(workBreakEnd3)
                        && attendance.getWorkEnd1().after(workBreakStart3)){
                    time3 = formatH.parse(formatH.format(workEnd1), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart3(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime3(formatH.parse(formatTime(time3), new ParsePosition( 0 )));
                }
                workTime1 = workEnd1.getTime() - workStart1.getTime()- time1 - time2 - time3;
            }
            // 休憩時間_2
            if (!StringUtils.isEmpty(attendanceTime.getWorkStart2()) && !StringUtils.isEmpty(attendanceTime.getWorkEnd2())) {
                Date workStart2 = formatY.parse(attendanceTime.getWorkStart2().replace("-","/"), new ParsePosition( 0 ));
                Date workEnd2 = formatY.parse(attendanceTime.getWorkEnd2().replace("-","/"), new ParsePosition( 0 ));
                if(attendance.getWorkStart2().before( workBreakStart1)
                        && attendance.getWorkEnd2().after(workBreakEnd1) ){
                    time1 = formatH.parse(atndStting.getWorkBreakEnd1(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart1(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime1(formatH.parse(formatTime(time1), new ParsePosition( 0 )));
                }
                if(workBreakEnd2 != null && workBreakStart2 != null && attendance.getWorkStart2().before( workBreakStart2)
                        && attendance.getWorkEnd2().after(workBreakEnd2) ){
                    time2 = formatH.parse(atndStting.getWorkBreakEnd2(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart2(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime2(formatH.parse(formatTime(time2), new ParsePosition( 0 )));
                }
                if(workBreakEnd3 != null && workBreakStart3 != null && attendance.getWorkStart2().before( workBreakStart3)
                        && attendance.getWorkEnd2().after(workBreakEnd3) ){
                    time3 = formatH.parse(atndStting.getWorkBreakEnd3(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart3(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime3(formatH.parse(formatTime(time3), new ParsePosition( 0 )));
                }
                if (workBreakEnd1 != null && workBreakStart1 != null && attendance.getWorkStart2().before(workBreakStart1)
                        && attendance.getWorkEnd2().before(workBreakEnd1)
                        && attendance.getWorkEnd2().after(workBreakStart1)){
                    time1 = formatH.parse(formatH.format(workEnd2), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart1(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime1(formatH.parse(formatTime(time1), new ParsePosition( 0 )));
                }
                if (workBreakEnd2 != null && workBreakStart2 != null && attendance.getWorkStart2().before(workBreakStart2)
                        && attendance.getWorkEnd2().before(workBreakEnd2)
                        && attendance.getWorkEnd2().after(workBreakStart2)){
                    time2 = formatH.parse(formatH.format(workEnd2), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart2(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime2(formatH.parse(formatTime(time2), new ParsePosition( 0 )));
                }
                if (workBreakEnd3 != null && workBreakStart3 != null && attendance.getWorkStart2().before(workBreakStart3)
                        && attendance.getWorkEnd2().before(workBreakEnd3)
                        && attendance.getWorkEnd2().after(workBreakStart3)){
                    time3 = formatH.parse(formatH.format(workEnd2), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart3(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime3(formatH.parse(formatTime(time3), new ParsePosition( 0 )));
                }
                workTime2 = workEnd2.getTime() - workStart2.getTime()- time1 - time2 - time3;
            }
            // 休憩時間_3
            if (!StringUtils.isEmpty(attendanceTime.getWorkStart3()) && !StringUtils.isEmpty(attendanceTime.getWorkEnd3())) {
                Date workStart3 = formatY.parse(attendanceTime.getWorkStart3().replace("-","/"), new ParsePosition( 0 ));
                Date workEnd3 = formatY.parse(attendanceTime.getWorkEnd3().replace("-","/"), new ParsePosition( 0 ));
                if(workBreakEnd1 != null && workBreakStart1 != null && attendance.getWorkStart3().before( workBreakStart1)
                        && attendance.getWorkEnd3().after(workBreakEnd1) ){
                    time1 = formatH.parse(atndStting.getWorkBreakEnd1(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart1(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime1(formatH.parse(formatTime(time1), new ParsePosition( 0 )));
                }
                if(workBreakEnd2 != null && workBreakStart2 != null && attendance.getWorkStart3().before( workBreakStart2)
                        && attendance.getWorkEnd3().after(workBreakEnd2) ){
                    time2 = formatH.parse(atndStting.getWorkBreakEnd2(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart2(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime2(formatH.parse(formatTime(time2), new ParsePosition( 0 )));
                }
                if(workBreakEnd3 != null && workBreakStart3 != null && attendance.getWorkStart3().before( workBreakStart3)
                        && attendance.getWorkEnd3().after(workBreakEnd3) ){
                    time3 = formatH.parse(atndStting.getWorkBreakEnd3(), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart3(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime3(formatH.parse(formatTime(time3), new ParsePosition( 0 )));
                }
                if (workBreakEnd1 != null && workBreakStart1 != null && attendance.getWorkStart3().before(workBreakStart1)
                        && attendance.getWorkEnd3().before(workBreakEnd1)
                        && attendance.getWorkEnd3().after(workBreakStart1)){
                    time1 = formatH.parse(formatH.format(workEnd3), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart1(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime1(formatH.parse(formatTime(time1), new ParsePosition( 0 )));
                }
                if (workBreakEnd2 != null && workBreakStart2 != null && attendance.getWorkStart3().before(workBreakStart2)
                        && attendance.getWorkEnd3().before(workBreakEnd2)
                        && attendance.getWorkEnd3().after(workBreakStart2)){
                    time2 = formatH.parse(formatH.format(workEnd3), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart2(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime2(formatH.parse(formatTime(time2), new ParsePosition( 0 )));
                }
                if (workBreakEnd3 != null && workBreakStart3 != null && attendance.getWorkStart3().before(workBreakStart3)
                        && attendance.getWorkEnd3().before(workBreakEnd3)
                        && attendance.getWorkEnd3().after(workBreakStart3)){
                    time3 = formatH.parse(formatH.format(workEnd3), new ParsePosition( 0 )).getTime()
                            - formatH.parse(atndStting.getWorkBreakStart3(), new ParsePosition( 0 )).getTime();
                    attendance.setWorkBreakTime3(formatH.parse(formatTime(time3), new ParsePosition( 0 )));
                }
                workTime3 = workEnd3.getTime() - workStart3.getTime()- time1 - time2 - time3;
            }

            // 普通残業
            long workTime = workTime1 + workTime2 + workTime3;
            long time = formatY.parse(dateYmd1 + " " + formatTime(workTime), new ParsePosition( 0 )).getTime()
                    - formatY.parse(dateYmd1 + " " + atndStting.getWorkPrescribedTime(), new ParsePosition( 0 )).getTime();
            if (time > 0){
                attendance.setWorkOverTime(formatH.parse(formatTime(time), new ParsePosition( 0 )));
            }else{
                attendance.setWorkOverTime(formatH.parse(formatTime(0), new ParsePosition( 0 )));
            }

            Category category = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "011", "002");
            String midnightTime = category.getCategoryKbnName();
            //深夜残業
            if (dateNow.after(
                    formatY.parse(dateYmd1 + " " + midnightTime, new ParsePosition( 0 )))){
                long workMidnightTime = dateNow.getTime()
                        - formatY.parse(dateYmd1 + " " + midnightTime, new ParsePosition( 0 )).getTime();
                attendance.setWorkMidnightTime(formatH.parse(formatTime(workMidnightTime), new ParsePosition( 0 )));
            }

            // 労働時間（休日）
            int count = holidayMapper.selectByDate(companyCode,dateYmd);
            if (count != 0 || "土".equals(attendanceEnd.getWorkDateWeek()) || "日".equals(attendanceEnd.getWorkDateWeek())){
                attendance.setWorkHoliTime(formatH.parse(formatTime(workTime), new ParsePosition( 0 )));
                if (dateNow.after(
                        formatY.parse(dateYmd1 + " " + midnightTime, new ParsePosition( 0 )))){
                    long workMidnightTime = dateNow.getTime()
                            - formatY.parse(dateYmd1 + " " + midnightTime, new ParsePosition( 0 )).getTime();
                    attendance.setWorkMidnightHoliTime(formatH.parse(formatTime(workMidnightTime), new ParsePosition( 0 )));
                }
            }
            // 休憩合計時間
            attendance.setWorkSumHoliTime(formatH.parse(formatTime(time1 + time2 + time3), new ParsePosition( 0 )));

            // 実働勤務合計時間
            attendance.setWorkSumTime(formatH.parse(formatTime(workTime), new ParsePosition( 0 )));

            attendance.setDelFlg("0");
            attendance.setRegAccount(userId);
            attendance.setRegTime(dateNow);
            attendance.setUpdAccount(userId);
            attendance.setUpdTime(dateNow);

            attendanceMapper.updateByPrimaryKey(attendance);
        }
        return 0;
    }
    /**
     * 勤怠設定情報取得
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<List<AtndSttingRpnDTO>> getAtndStting(AtndSttingDTO atndSttingDTO,
                                                          HttpServletRequest request, HttpServletResponse response) {
        List<AtndSttingRpnDTO> atndSttingRpnDTO = atndSttingMapper.searchByDate(atndSttingDTO.getCompanyCode(),
                atndSttingDTO.getUserId(), atndSttingDTO.getApplicationDate());
        return ServerResponse.createBySuccess(atndSttingRpnDTO);
    }

    private  String formatTime(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        if (minute < 15) {
            minute = 0;
        }
        else if (minute < 30) {
            minute = 15;
        }
        else if (minute < 45) {
            minute = 30;
        }
        else if (minute > 45) {
            minute = 45;
        }

        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strHour+":"+strMinute + " : " + strSecond+","+strMilliSecond ;
    }

    //15分単位切り捨て処理
    private  String formatTime15(String hhmm) {

        String[] strHM = hhmm.split(":");
        String minute = "";
        //基本的にはhh:mmの形で入ってくる、念のためチェック
        if(strHM.length == 2){
            Integer intM = Integer.parseInt(strHM[1]);

            if (intM < 15) {
                minute = "00";
            }
            else if (intM >= 15 && intM < 30) {
                minute = "15";
            }
            else if (intM >= 30 && intM < 45) {
                minute = "30";
            }
            else if (intM >= 45) {
                minute = "45";
            }
            return strHM[0]+":" + minute;
        }else{
            return hhmm;
        }
    }

    private  String formatTime1(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        if (minute < 15 && minute > 0) {
            minute = 0;
        }
        else if (minute < 30 && minute > 15) {
            minute = 15;
        }
        else if (minute < 45 && minute > 30) {
            minute = 30;
        }
        else if (minute > 45) {
            minute = 45;
        }

        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strHour+":"+strMinute + " : " + strSecond+","+strMilliSecond ;
    }

    private  String formatTime2(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        if (minute < 15 && minute > 0) {
            minute = 15;
        }
        else if (minute < 30 && minute > 15) {
            minute = 30;
        }
        else if (minute < 45 && minute > 30) {
            minute = 45;
        }
        else if (minute > 45) {
            minute = 0;
            hour += 1;
        }

        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strHour+":"+strMinute + " : " + strSecond+","+strMilliSecond ;
    }
    /**
     * 勤怠表エクスポート(検索押下時の処理）
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<List<BulkExportDTO>> searchBulkExport(BulkExportDTO bulkExportDTO, HttpServletRequest request, HttpServletResponse response) {

        List<BulkExportDTO> BulkExport = attendanceMapper.searchBulkExport(bulkExportDTO.companyCode,
                ('%'+ bulkExportDTO.departmentCode + '%'),
                ('%'+ bulkExportDTO.workDateYm + '%'),
                ('%'+ bulkExportDTO.requestStatusKbn + '%'),
                ('%'+ bulkExportDTO.transportationCode + '%'),
                ('%'+ bulkExportDTO.userId +'%'),
                ('%'+ bulkExportDTO.userName +'%'));

        return ServerResponse.createBySuccess(BulkExport);
    }
    /**
     * 勤怠表エクスポート(Excel一括エクスポート）
     *
     * @return
     */
    @Override
    public AttendanceExcelFileDTO createAttendanceFileStream(AttendanceDTO attendanceDTO) {
        AttendanceExcelFileDTO attendanceExcelFile = new AttendanceExcelFileDTO();
        attendanceExcelFile.fileContent = new ByteArrayOutputStream();
        try {

            //対象社員の対象月の勤怠情報を取得する
            Map<String, Object> cMap = getAttendanceOutPutData(attendanceDTO.getCompanyCode()
                    ,attendanceDTO.getUserId()
                    ,attendanceDTO.getWorkDateYm()
                    ,""
                    ,"1");

            //出力Excelファイル名を定義する
            UserDetailDTO userDetailDTO = userMasterMapper.getUser(attendanceDTO.getCompanyCode(), attendanceDTO.getUserId());
            String newFileName = attendanceDTO.getUserId() + "_" + attendanceDTO.getWorkDateYm() + "勤務表" + "_" + userDetailDTO.getUserName() + ".xlsx";
            attendanceExcelFile.fileName = newFileName;
            File destFile = File.createTempFile(newFileName, ".xlsx");

            //トランスフォーマーオブジェクト宣言
            XLSTransformer transformer = new XLSTransformer();
            //テンプレート区分取得
            Category category = categoryMapper.selectByPrimaryKey(attendanceDTO.getCompanyCode(), "012", "001");
//                リリース用
//            File file = ResourceUtils.getFile("/home/demo/勤務表_" + category.getCategoryKbnName() + ".xlsx");
//                開発用
            File file = ResourceUtils.getFile("classpath:templates/勤務表_" + category.getCategoryKbnName() + ".xlsx");
            // 勤怠情報をExcelテンプレートへ書き込み、その結果をバイナリ―データで返す
            transformer.transformXLS(file.getPath(), cMap, destFile.getAbsolutePath());
            InputStream inputStream = new FileInputStream(destFile);

            int ch;
            while ((ch = inputStream.read()) != -1) {
                attendanceExcelFile.fileContent.write(ch);
            }
            destFile.delete();
            inputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {

        }
        return attendanceExcelFile;
    }

    /**
     * 一括出力画面で勤怠表PDF出力処理
     * @param bulkExportDTO
     * @return
     */
    @Override
    public Map<String, Object> outPutAttendancePdfStream(BulkExportDTO bulkExportDTO)
    {
        Map<String, Object> cMap = new HashMap();
        try {
            cMap = getAttendanceOutPutData(bulkExportDTO.getCompanyCode()
                    ,bulkExportDTO.getUserId()
                    ,bulkExportDTO.getWorkDateYm()
                    ,bulkExportDTO.getRequestStatusKbn()
                    ,bulkExportDTO.getAccessMode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {

        }
        return cMap;
    }

    /**
     * 一括出力画面で勤怠表出力対象データ取得共通処理
     * @param companyCode   ：会社コード
     * @param userId        ：社員ID
     * @param workDateYm    ：出力対象月
     * @param requestStatusKbn：勤怠申請状態
     * @param mode          :呼び出し元　1 excel 2 pdf（一括出力画面用）、3 pdf（勤怠入力画面用）
     * @return  勤怠表出力情報
     */
    public Map<String, Object> getAttendanceOutPutData(String companyCode, String userId, String workDateYm, String requestStatusKbn, String mode){
        Map<String, Object> cMap = new HashMap();
        try {

            List<AttendanceRPnDTO> attendanceList = attendanceMapper.selectById(companyCode, userId, workDateYm);

            // ユーザー情報設定
            UserDetailDTO userDetailDTO = userMasterMapper.getUser(companyCode, userId);
            userDetailDTO.setYear(workDateYm.substring(0, 4));
            userDetailDTO.setMonth(workDateYm.substring(4));

            // 2021/08/05 追加
            // 勤怠設定データ取得処理
            AtndSttingRpnDTO atndStting = atndSttingMapper.searchData(companyCode, userId);
            String workStartTime = atndStting.getWorkStartTime();

            DateFormat formatY = new SimpleDateFormat("yyyy/MM");
            String dateYmd1 = new SimpleDateFormat("yyyy/MM").format(new Date());

            if(!StringUtils.isEmpty(userDetailDTO.getJoinDate())){
                String year = userDetailDTO.getJoinDate().substring(0, 4);
                String month = userDetailDTO.getJoinDate().substring(4, 6);
                String day = userDetailDTO.getJoinDate().substring(6);
                userDetailDTO.setJoinDate(year + "/" + month + "/" + day);
                float time = (float) (formatY.parse(dateYmd1).getTime() - formatY.parse(year + "/" + month).getTime()) / 1000 / 60 / 60 / 24 / 30 / 12;
                DecimalFormat formater = new DecimalFormat();
                formater.setMaximumFractionDigits(1);
                formater.setGroupingSize(0);
                formater.setRoundingMode(RoundingMode.FLOOR);
                String f = formater.format(time);
                userDetailDTO.setCountYear(f);
            }
            //下载文件
            // 勤務区分設定
            if (attendanceList.size() > 0) {
                //2022/06/21 追加＆変更　加賀田
                int workDays = 0; // 出勤日数
                String workDaysTime = "00:00"; // 出勤時間
                int lateWorkDays = 0; //遅刻日数
                String lateWorkDaysTime = "00:00"; //遅刻時間
                int lWorkDays = 0; //欠勤日数
                String lWorkDaysTime = "00:00"; //欠勤時間
                int retireDays = 0; //休職日数
                String retireDaysTime = "00:00"; //休職時間
                int newYearHolidayDays = 0; //年末年始日数
                String newYearHolidayDaysTime = "00:00"; //年末年始時間

                float paidHolidayHalfDays = 0; //有休半日日数
                String paidHolidayHalfDaysTime = "00:00"; //有休半日時間
                int paidHolidayDays = 0; //有休全日日数
                String paidHolidayDaysTime = "00:00"; //有休全日時間

                float insteadBreakHalfDays = 0; //代休半日日数
                String insteadBreakHalfDaysTime = "00:00"; //代休半日時間
                int insteadBreakDays = 0; //代休全日日数
                String insteadBreakDaysTime = "00:00"; //代休全日時間

                float satWorkHalfDays = 0; //休日出勤半日日数：土曜出勤
                String satWorkHalfDaysTime = "00:00"; //休日出勤半日：土曜出勤時間
                float sunWorkHalfDays = 0; //休日出勤半日日数：日・祝出勤
                String sunWorkHalfDaysTime = "00:00"; //休日出勤半日：日・祝出勤時間

                int satWorkDays = 0; //休日出勤全日日数：土曜出勤
                String satWorkDaysTime = "00:00"; //休日出勤全日：土曜出勤時間
                //2021/08/05 追加
                int sunWorkDays = 0; //休日出勤全日日数：日・祝出勤
                String sunWorkDaysTime = "00:00"; //休日出勤全日：日・祝出勤時間

                float sunFWorkHalfDays = 0; //振替出勤半日日数
                String sunFWorkHalfDaysTime = "00:00"; //振替出勤半日時間
                int sunFWorkDays = 0; //振替出勤全日日数
                String sunFWorkDaysTime = "00:00"; //振替出勤全日時間

                float breakFHalfDays = 0; //振替休半日日数
                String breakFHalfDaysTime = "00:00"; //振替休半日時間
                int breakFDays = 0; //振替休全日日数
                String breakFDaysTime = "00:00"; //振替休全日時間

                int summerHolidayDays = 0; //夏季休暇日数
                String summerHolidayDaysTime = "00:00"; //夏季休暇時間
                int condolenceDays = 0; //特別休暇日数
                String condolenceDaysTime = "00:00"; //特別休暇時間
                int elseWorkDays = 0; //その他日数
                String elseWorkDaysTime = "00:00"; //その他時間

                int workDate = 0; //所定労働日数
                int workTimeScheduled_kari = attendanceList.size();
                String workTimeScheduled = "00:00";//所定労働時間
                int workSumDate = 0;//実働勤務日数
                String workSumTime = "00:00";//実働勤務時間

                String workBreakTime1 = "00:00";//休憩時間1
                String workBreakTime2 = "00:00";//休憩時間2
                String workBreakTime3 = "00:00";//休憩時間3
                String workOverTime = "00:00";//普通残業時間
                String workMidnightTime = "00:00";//深夜残業時間
                String workHoliTime = "00:00";//休日出勤時間
                String workMidnightHoliTime = "00:00";//休日深夜時間

                for (int i = 0; i < attendanceList.size(); i++) {


                    //2021/08/04 追加
                    if (!StringUtils.isEmpty(attendanceList.get(i).getWorkKbn())) {
                        boolean elseWork = true;
                        // chen 2021/09/28 start
                        if (StringUtils.isEmpty(attendanceList.get(i).getWorkStart1())
                                || StringUtils.isEmpty(attendanceList.get(i).getWorkEnd1())) {
                            attendanceList.get(i).setWorkTime("");
                            attendanceList.get(i).setWorkSumTime("");
                            attendanceList.get(i).setWorkOverTime("");
                            attendanceList.get(i).setWorkMidnightTime("");
                            attendanceList.get(i).setWorkHoliTime("");
                            attendanceList.get(i).setWorkMidnightHoliTime("");
                        }
                        // chen 2021/09/28 end
                        // chen 2021/10/25 start
                        if (!StringUtils.isEmpty(attendanceList.get(i).getWorkEnd1())) {
                            String start = attendanceList.get(i).getWorkStart1().split(" ")[0];
                            String end = attendanceList.get(i).getWorkEnd1().split(" ")[0];
                            if (!attendanceList.get(i).getWorkEnd1().split(" ")[0].equals(attendanceList.get(i).getWorkStart1().split(" ")[0])) {
                                attendanceList.get(i).setWorkEnd1(minusToString(toMinus(attendanceList.get(i).getWorkEnd1().split(" ")[1]) + 24 * 60));
                            } else {
                                attendanceList.get(i).setWorkEnd1(attendanceList.get(i).getWorkEnd1().split(" ")[1]);
                            }
                        }
                        if (!StringUtils.isEmpty(attendanceList.get(i).getWorkStart1())) {
                            attendanceList.get(i).setWorkStart1(attendanceList.get(i).getWorkStart1().split(" ")[1]);
                        }
                        // chen 2021/10/25 end
                        // chen 2022/06/21　start　加賀田　変更＆追加
                        if ("001".equals(attendanceList.get(i).getWorkKbn())) {
                            workDays += 1;// 出勤日数
                            if (attendanceList.get(i).getWorkSumTime() != null) {
                                String fmtWorkSumTime = formatTime15(attendanceList.get(i).getWorkSumTime());
                                attendanceList.get(i).setWorkSumTime(fmtWorkSumTime);
                                workDaysTime = getSum(workDaysTime, attendanceList.get(i).getWorkSumTime());// 出勤時間
                            }
                            elseWork = false;
                        }
                        if ("004".equals(attendanceList.get(i).getWorkKbn())) {
                            lateWorkDays += 1;// 遅刻日数
                            if (attendanceList.get(i).getWorkStart1() != null) {
                                String LateTime = getMinus(attendanceList.get(i).getWorkStart1(), workStartTime);
                                lateWorkDaysTime = getSum(lateWorkDaysTime, LateTime);// 遅刻時間
                            }
                            elseWork = false;
                        }
                        if ("010".equals(attendanceList.get(i).getWorkKbn())) {
                            lWorkDays += 1;// 欠勤日数
                            if (attendanceList.get(i).getWorkTime() != null) {
                                lWorkDaysTime = getSum(lWorkDaysTime, attendanceList.get(i).getWorkTime());// 欠勤時間
                            }
                            elseWork = false;
                        }
                        if ("070".equals(attendanceList.get(i).getWorkKbn())) {
                            retireDays += 1;// 休職日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null) {
                                retireDaysTime = getSum(retireDaysTime, attendanceList.get(i).getWorkTime());// 休職時間
                            }
                            elseWork = false;
                        }
                        if ("080".equals(attendanceList.get(i).getWorkKbn())) {
                            newYearHolidayDays += 1;// 年末年始日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null) {
                                newYearHolidayDaysTime = getSum(newYearHolidayDaysTime, attendanceList.get(i).getWorkTime());// 年末年始時間
                            }
                            elseWork = false;
                        }
                        if ("021".equals(attendanceList.get(i).getWorkKbn())) {
                            paidHolidayHalfDays += 1;// 有休半日日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null && attendanceList.get(i).getWorkSumTime() != null) {
                                String BreakTime = getMinus(attendanceList.get(i).getWorkTime(), attendanceList.get(i).getWorkSumTime());
                                paidHolidayHalfDaysTime = getSum(paidHolidayHalfDaysTime, BreakTime);// 有休半日時間
                            }
                            elseWork = false;
                        }
                        if ("020".equals(attendanceList.get(i).getWorkKbn())) {
                            paidHolidayDays += 1;// 有休全日日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null) {
                                paidHolidayDaysTime = getSum(paidHolidayDaysTime, attendanceList.get(i).getWorkTime());// 有休全日時間
                            }
                            elseWork = false;
                        }
                        if ("026".equals(attendanceList.get(i).getWorkKbn())) {
                            insteadBreakHalfDays += 1;// 代休半日日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null && attendanceList.get(i).getWorkSumTime() != null) {
                                String BreakTime = getMinus(attendanceList.get(i).getWorkTime(), attendanceList.get(i).getWorkSumTime());
                                insteadBreakHalfDaysTime = getSum(insteadBreakHalfDaysTime, BreakTime);// 代休半日時間
                            }
                            elseWork = false;
                        }
                        if ("025".equals(attendanceList.get(i).getWorkKbn())) {
                            insteadBreakDays += 1;// 代休全日日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null) {
                                insteadBreakDaysTime = getSum(insteadBreakDaysTime, attendanceList.get(i).getWorkTime());// 代休全日時間
                            }
                            elseWork = false;
                        }
                        if ("005".equals(attendanceList.get(i).getWorkKbn())) {
                            if ("土".equals((attendanceList.get(i).getWorkDateWeek()))) {
                                satWorkHalfDays += 1;// 休日出勤半日日数：土出勤日数
                                if (attendanceList.get(i).getWorkSumTime() != null) {
                                    satWorkHalfDaysTime = getSum(satWorkHalfDaysTime, attendanceList.get(i).getWorkSumTime());// 休日出勤半日：土曜出勤時間
                                }
                                elseWork = false;
                            }
                            String dateYmd = attendanceList.get(i).getWorkDate();
                            int count = holidayMapper.selectByDate(companyCode, dateYmd);
                            if (count != 0 || "日".equals((attendanceList.get(i).getWorkDateWeek()))) {
                                sunWorkHalfDays += 1;//休日出勤半日日数：日・祝出勤
                                if (attendanceList.get(i).getWorkSumTime() != null) {
                                    sunWorkHalfDaysTime = getSum(sunWorkHalfDaysTime, attendanceList.get(i).getWorkSumTime());//休日出勤半日：日・祝出勤時間
                                }
                                elseWork = false;
                            }
                        }
                        if ("002".equals(attendanceList.get(i).getWorkKbn())) {
                            if ("土".equals((attendanceList.get(i).getWorkDateWeek()))) {
                                satWorkDays += 1;// 休日出勤全日日数：土出勤
                                if (attendanceList.get(i).getWorkSumTime() != null) {
                                    satWorkDaysTime = getSum(satWorkDaysTime, attendanceList.get(i).getWorkSumTime());// 休日出勤全日：土曜出勤時間
                                }
                                elseWork = false;
                            }
                            //2021/08/05 追加
                            String dateYmd = attendanceList.get(i).getWorkDate();
                            int count = holidayMapper.selectByDate(companyCode, dateYmd);
                            if (count != 0 || "日".equals((attendanceList.get(i).getWorkDateWeek()))) {
                                sunWorkDays += 1;//休日出勤全日日数：日・祝出勤
                                if (attendanceList.get(i).getWorkSumTime() != null) {
                                    sunWorkDaysTime = getSum(sunWorkDaysTime, attendanceList.get(i).getWorkSumTime());//休日出勤全日：日・祝出勤時間
                                }
                                elseWork = false;
                            }
                        }
                        if ("006".equals(attendanceList.get(i).getWorkKbn())) {
                            sunFWorkHalfDays += 1;// 振替出勤半日日数
                            if (attendanceList.get(i).getWorkSumTime() != null) {
                                sunFWorkHalfDaysTime = getSum(sunFWorkHalfDaysTime, attendanceList.get(i).getWorkSumTime());// 振替出勤半日時間
                            }
                            elseWork = false;
                        }
                        if ("003".equals(attendanceList.get(i).getWorkKbn())) {
                            sunFWorkDays += 1;// 振替出勤全日日数
                            if (attendanceList.get(i).getWorkSumTime() != null) {
                                sunFWorkDaysTime = getSum(sunFWorkDaysTime, attendanceList.get(i).getWorkSumTime());// 振替出勤全日時間
                            }
                            elseWork = false;
                        }
                        if ("024".equals(attendanceList.get(i).getWorkKbn())) {
                            breakFHalfDays += 1;// 振替休半日日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null && attendanceList.get(i).getWorkSumTime() != null) {
                                String BreakTime = getMinus(attendanceList.get(i).getWorkTime(), attendanceList.get(i).getWorkSumTime());
                                breakFHalfDaysTime = getSum(breakFHalfDaysTime, BreakTime);// 振替半日時間
                            }
                            elseWork = false;
                        }
                        if ("023".equals(attendanceList.get(i).getWorkKbn())) {
                            breakFDays += 1;// 振替休全日日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null) {
                                breakFDaysTime = getSum(breakFDaysTime, attendanceList.get(i).getWorkTime());// 振替休全日時間
                            }
                            elseWork = false;
                        }
                        if ("081".equals(attendanceList.get(i).getWorkKbn())) {
                            summerHolidayDays += 1;// 夏季休暇日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null) {
                                summerHolidayDaysTime = getSum(summerHolidayDaysTime, attendanceList.get(i).getWorkTime());// 夏季休暇時間
                            }
                            elseWork = false;
                        }

                        if ("022".equals(attendanceList.get(i).getWorkKbn())) {
                            condolenceDays += 1;// 特別休暇日数
                            attendanceList.get(i).setWorkTime(atndStting.getWorkPrescribedTime());
                            if (attendanceList.get(i).getWorkTime() != null) {
                                condolenceDaysTime = getSum(condolenceDaysTime, attendanceList.get(i).getWorkTime());// 特別休暇時間
                            }
                            elseWork = false;
                        }
                        if (elseWork) {
                            elseWorkDays += 1;// その他日数
                            if (attendanceList.get(i).getWorkTime() != null) {
                                elseWorkDaysTime = getSum(elseWorkDaysTime, attendanceList.get(i).getWorkTime());// その他時間
                            }
                        }
                    }
                    // chen 2022/06/21　end
                    if (!StringUtils.isEmpty(attendanceList.get(i).getWorkKbn())) {
                        Category category = categoryMapper.selectByPrimaryKey(companyCode, "010", attendanceList.get(i).getWorkKbn());

                        attendanceList.get(i).setWorkKbn(category.getCategoryKbnName());
                    }

                    //2021/08/05 追加
                    String dateYmd = attendanceList.get(i).getWorkDate();
                    int count = holidayMapper.selectByDate(companyCode, dateYmd);
                    if (count != 0 || "土".equals((attendanceList.get(i).getWorkDateWeek())) || "日".equals((attendanceList.get(i).getWorkDateWeek()))) {
                        workTimeScheduled_kari -= 1;//日・祝出勤
                    }
                }
                workTimeScheduled = workTimeScheduled_kari * Integer.parseInt(atndStting.getWorkPrescribedTime().split(":")[0]) + ":00";// 所定労働時間合計
                // 合計
//                int workDate = 0;
//                String workBreakTime1 = "00:00";
//                String workBreakTime2 = "00:00";
//                String workBreakTime3 = "00:00";
//                String workSumTime = "00:00";
//                String workOverTime = "00:00";
//                String workMidnightTime = "00:00";
//                String workHoliTime = "00:00";
//                String workMidnightHoliTime = "00:00";

                for (AttendanceRPnDTO attendanceRPnDTO : attendanceList) {
                    // 工作天数
                    int count = holidayMapper.selectByDate(companyCode, attendanceRPnDTO.getWorkDate());
                    if (count == 0 && !"土".equals(attendanceRPnDTO.getWorkDateWeek()) && !"日".equals(attendanceRPnDTO.getWorkDateWeek())) {
                        workDate += 1;
                    }
                    attendanceRPnDTO.setWorkDate(attendanceRPnDTO.getWorkDate().substring(6));
                    // 休憩時間1
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkBreakTime1()) && attendanceRPnDTO.getWorkBreakTime1() != null) {
                        workBreakTime1 = getSum(workBreakTime1, attendanceRPnDTO.getWorkBreakTime1());
                    }
                    // 休憩時間2
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkBreakTime2()) && attendanceRPnDTO.getWorkBreakTime2() != null) {
                        workBreakTime2 = getSum(workBreakTime2, attendanceRPnDTO.getWorkBreakTime2());
                    }
                    // 休憩時間3
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkBreakTime3()) && attendanceRPnDTO.getWorkBreakTime3() != null) {
                        workBreakTime3 = getSum(workBreakTime3, attendanceRPnDTO.getWorkBreakTime3());
                    }
                    // 実働時間合計
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkSumTime()) && attendanceRPnDTO.getWorkSumTime() != null) {
                        String fmtWorkSumTime = formatTime15(attendanceRPnDTO.getWorkSumTime());
                        attendanceRPnDTO.setWorkSumTime(fmtWorkSumTime);
                        workSumTime = getSum(workSumTime, attendanceRPnDTO.getWorkSumTime());
                    }
                    // 普通残業時間
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkOverTime()) && attendanceRPnDTO.getWorkOverTime() != null) {
                        String fmtWorkOverTime = formatTime15(attendanceRPnDTO.getWorkOverTime());
                        attendanceRPnDTO.setWorkOverTime(fmtWorkOverTime);
                        workOverTime = getSum(workOverTime, attendanceRPnDTO.getWorkOverTime());
                    }
                    // 深夜残業時間
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkMidnightTime()) && attendanceRPnDTO.getWorkMidnightTime() != null) {
                        String fmtWorkMidnightTime = formatTime15(attendanceRPnDTO.getWorkMidnightTime());
                        attendanceRPnDTO.setWorkMidnightTime(fmtWorkMidnightTime);
                        workMidnightTime = getSum(workMidnightTime, attendanceRPnDTO.getWorkMidnightTime());
                    }
                    // 休日出勤時間
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkHoliTime()) && attendanceRPnDTO.getWorkHoliTime() != null) {
                        String fmtWorkHoliTime = formatTime15(attendanceRPnDTO.getWorkHoliTime());
                        attendanceRPnDTO.setWorkHoliTime(fmtWorkHoliTime);
                        workHoliTime = getSum(workHoliTime, attendanceRPnDTO.getWorkHoliTime());
                    }
                    // 休日深夜時間
                    if (!StringUtils.isEmpty(attendanceRPnDTO.getWorkMidnightHoliTime()) && attendanceRPnDTO.getWorkMidnightHoliTime() != null) {
                        String fmtWorkMidnightHoliTime = formatTime15(attendanceRPnDTO.getWorkMidnightHoliTime());
                        attendanceRPnDTO.setWorkMidnightHoliTime(fmtWorkMidnightHoliTime);
                        workMidnightHoliTime = getSum(workMidnightHoliTime, attendanceRPnDTO.getWorkMidnightHoliTime());
                    }
                    //2022/08/31 備考に休日出勤日を追加（start）李
                    // 休日出勤判断
                    if ("代休半日".equals(attendanceRPnDTO.getWorkKbn()) || "代休全日".equals(attendanceRPnDTO.getWorkKbn())){
                        String subHoliday = attendanceMapper.selectSubHoliday(companyCode,attendanceRPnDTO.getUserId(),attendanceRPnDTO.getWorkDateYm() + attendanceRPnDTO.getWorkDate());

                        if (!StringUtils.isEmpty(subHoliday)){
                            SimpleDateFormat sdff =   new SimpleDateFormat( "yyyyMMdd" );
                            subHoliday = new SimpleDateFormat("yyyy/MM/dd").format(sdff.parse(subHoliday));
                            String remark = "";
                            if (StringUtils.isEmpty(attendanceRPnDTO.getRemark())) {
                                remark = "（休日出勤日：" + subHoliday + "）";
                            } else {
                                remark = attendanceRPnDTO.getRemark() + "（休日出勤日：" + subHoliday + "）";
                            }
                            attendanceRPnDTO.setRemark(remark);
                        }
                    }
                    //2022/08/31 備考に休日出勤日を追加（end）李
                }
                String applyStatus = "";
                //2022/06/22 追加＆変更　加賀田
                if(mode.equals("1")){
                    cMap.put("user", userDetailDTO);
                    cMap.put("vms", attendanceList);
                }else if(mode.equals("2") || mode.equals("3")){
                    cMap.put("joinDate", userDetailDTO.getJoinDate());//入社日
                    cMap.put("countYear", userDetailDTO.getCountYear());//勤続年数
                    cMap.put("employeeNo", userDetailDTO.getUserId());//社員番号
                    cMap.put("employeeName", userDetailDTO.getUserName());//社員番号
                    cMap.put("year", userDetailDTO.getYear());//申請年
                    cMap.put("month", userDetailDTO.getMonth());//申請月
                    cMap.put("datasourceAttendanceList", attendanceList);

                    //勤怠入力から入ってきた場合、申請状態を取得する
                    if(mode.equals("3")){
                        GetStatusAttendanceDTO getStatusAttendanceDTO = requestAttendanceMapper.getStatus(companyCode,userId,workDateYm);
                        if(getStatusAttendanceDTO != null){
                            requestStatusKbn = getStatusAttendanceDTO.getRequestStatusKbn();
                        }
                    }

                    if(StringUtils.isEmpty(requestStatusKbn) || requestStatusKbn.equals("001")){
                        applyStatus = "未申請";
                    }else if(requestStatusKbn.equals("003")){
                        applyStatus = "回覧中";
                    }else if(requestStatusKbn.equals("010")){
                        applyStatus = "差戻";
                    }else if(requestStatusKbn.equals("999")){
                        applyStatus = "完了";
                    }
                }
                cMap.put("workDate", workDate + "");//所定労働日数
                cMap.put("workTimeScheduled", workTimeScheduled);//所定労働時間
                cMap.put("workSumDate", workSumDate + workDays + lateWorkDays + satWorkDays + sunWorkDays + satWorkHalfDays / 2 + sunWorkHalfDays / 2 + sunFWorkDays + sunFWorkHalfDays / 2 + "");//実働勤務日数（出勤日数,遅刻日数,休日出勤日数,振替出勤日数 ）
                cMap.put("workSumTime", workSumTime);//実働勤務時間
                cMap.put("workBreakTime1", workBreakTime1);//休憩時間1
                cMap.put("workBreakTime2", workBreakTime2);//休憩時間2
                cMap.put("workBreakTime3", workBreakTime3);//休憩時間3
                cMap.put("workOverTime", workOverTime);//普通残業時間
                cMap.put("workMidnightTime", workMidnightTime);//深夜残業時間
                cMap.put("workHoliTime", workHoliTime);//休日出勤時間
                cMap.put("workMidnightHoliTime", workMidnightHoliTime);//休日深夜時間
                //2022/06/21 追加＆変更　加賀田
                cMap.put("workDays", workDays + "");// 出勤日数
                cMap.put("workDaysTime", workDaysTime);// 出勤時間
                cMap.put("lateWorkDays", lateWorkDays + "");//遅刻日数
                cMap.put("lateWorkDaysTime", lateWorkDaysTime);//遅刻時間
                cMap.put("lWorkDays", lWorkDays + "");//欠勤日数
                cMap.put("retireDays", retireDays + "");//休職日数
                cMap.put("newYearHolidayDays", newYearHolidayDays + "");//年末年始日数
                cMap.put("paidHolidayDays", paidHolidayDays + paidHolidayHalfDays / 2 + "");//有休全日,半日
                cMap.put("insteadBreakDays", insteadBreakDays + insteadBreakHalfDays / 2 + "");//代休全日,半日
                cMap.put("sunWorkDays", satWorkDays + sunWorkDays + satWorkHalfDays / 2 + sunWorkHalfDays / 2 + "");//休日全日日数,半日日数：日・祝出勤
                cMap.put("sunFWorkDays", sunFWorkDays + sunFWorkHalfDays / 2 + "");//振替出勤全日,半日
                cMap.put("breakFDays", breakFDays + breakFHalfDays / 2 + "");//振替休全日,半日
                cMap.put("summerHolidayDays", summerHolidayDays + "");//夏季休暇日数
                cMap.put("condolenceDays", condolenceDays + "");//特別休暇日数
                cMap.put("elseWorkDays", elseWorkDays + "");//その他日数
                cMap.put("elseWorkDaysTime", elseWorkDaysTime);//その他時間
                cMap.put("strApplyStatus", applyStatus);//勤怠申請状態
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {

        }
        return cMap;
    }

    public int toMinus(String str) throws Exception {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }

        int hour = Integer.parseInt(str.split(":")[0]) * 60;
        int minus = Integer.parseInt(str.split(":")[1]);

        return hour + minus;
    }

    public String getSum(String str1, String str2) throws Exception {
        if (":".equals(str1)) {
            str1 = "00:00";
        }
        String[] sum1 = str1.split(":");
        String[] sum2 = str2.split(":");

        if (sum2.length == 2) {
            int sum2_0 = Integer.parseInt("".equals(sum2[0]) ? "00" : sum2[0]);
            int sum2_1 = Integer.parseInt("".equals(sum2[1]) ? "00" : sum2[1]);
            int sum1_0 = Integer.parseInt("".equals(sum1[0]) ? "00" : sum1[0]);
            int sum1_1 = Integer.parseInt("".equals(sum1[1]) ? "00" : sum1[1]);
            int sumM = sum1_0 * 60 + sum2_0 * 60 + sum1_1 + sum2_1;
            String hour = "";
            String minu = "";
            if (sumM / 60 < 10) {
                hour = "0" + sumM / 60;
            } else {
                hour = "" + sumM / 60;
            }
            if (sumM % 60 < 10) {
                minu = "0" + sumM % 60;
            } else {
                minu = "" + sumM % 60;
            }
            str1 = hour + ":" + minu;
        }
        return str1;
    }

    // 2021/08/05 追加
    public String getMinus(String str1, String str2) throws Exception {
        if (":".equals(str1)) {
            str1 = "00:00";
        }
        String[] sum1 = str1.split(":");
        String[] sum2 = str2.split(":");

        if (sum1.length == 2 && sum2.length == 2) {
            int sum2_0 = Integer.parseInt("".equals(sum2[0]) ? "00" : sum2[0]);
            int sum2_1 = Integer.parseInt("".equals(sum2[1]) ? "00" : sum2[1]);
            int sum1_0 = Integer.parseInt("".equals(sum1[0]) ? "00" : sum1[0]);
            int sum1_1 = Integer.parseInt("".equals(sum1[1]) ? "00" : sum1[1]);
            int sumM = (sum1_0 * 60 + sum1_1) - (sum2_0 * 60 + sum2_1);
            String hour = "";
            String minu = "";
            if(sumM > 0) {
                if (sumM / 60 < 10) {
                    hour = "0" + sumM / 60;
                } else {
                    hour = "" + sumM / 60;
                }
                if (sumM % 60 < 10) {
                    minu = "0" + sumM % 60;
                } else {
                    minu = "" + sumM % 60;
                }
                str1 = hour + ":" + minu;
            }else {
                str1 = "00:00";
            }
        }
        return str1;
    }

    public String minusToString(int i) throws Exception {
        if (i == 0) {
            return "00:00";
        }
        String hour = i / 60 + "";
        String minus = i % 60 + "";

        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minus.length() == 1) {
            minus = "0" + minus;
        }

        return hour + ":" + minus;
    }
}
