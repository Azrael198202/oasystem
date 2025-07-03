package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.SubHolidayMapper;
import com.demo.dao.WorkDayOffMapper;
import com.demo.entity.Attendance;
import com.demo.entity.SubHoliday;
import com.demo.entity.WorkDayOff;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.subHoliday.GetSubHolidayCntDTO;
import com.demo.pojo.subHoliday.SubHolidayDTO;
import com.demo.pojo.subHoliday.SubHolidayInputDTO;
import com.demo.service.ISubHolidayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SubHolidayServiceImpl implements ISubHolidayService {
    @Autowired
    SubHolidayMapper subHolidayMapper;

    @Autowired
    WorkDayOffMapper workDayOffMapper;

    @Override
    public ServerResponse<Float> getRequestSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        float requestSubHolidayData = subHolidayMapper.getRequestSubHoliday(subHolidayDTO.companyCode, subHolidayDTO.userId, subHolidayDTO.setDate);
        return  ServerResponse.createBySuccess(requestSubHolidayData);
    }
    @Override
    public ServerResponse<Float> endOfThisMonthSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        float requestSubHolidayData = subHolidayMapper.endOfThisMonthSubHoliday(subHolidayDTO.companyCode, subHolidayDTO.userId, subHolidayDTO.setDate);
        return  ServerResponse.createBySuccess(requestSubHolidayData);
    }
    @Override
    public ServerResponse<Float> theEndOfTheFollowingMonthSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        float requestSubHolidayData = subHolidayMapper.theEndOfTheFollowingMonthSubHoliday(subHolidayDTO.companyCode, subHolidayDTO.userId, subHolidayDTO.setDate);
        return  ServerResponse.createBySuccess(requestSubHolidayData);
    }
    @Override
    public ServerResponse<Float> checkNum(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        float requestSubHolidayData = subHolidayMapper.checkNum(subHolidayDTO.companyCode, subHolidayDTO.userId);
        return  ServerResponse.createBySuccess(requestSubHolidayData);
    }
    @Override
    public ServerResponse<List<SubHolidayDTO>> getScheduledWorkDays(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        List<SubHolidayDTO> ScheduledWorkDayList = subHolidayMapper.getScheduledWorkDays(subHolidayDTO.companyCode, subHolidayDTO.userId);
        return  ServerResponse.createBySuccess(ScheduledWorkDayList);
    }
    //    代休残入力画面：検索押下時の処理
    @Override
    public ServerResponse<List<SubHolidayInputDTO>> usersearch(SubHolidayInputDTO subHolidayInputDTO, HttpServletRequest request, HttpServletResponse response) {

        List<SubHolidayInputDTO> ScheduledWorkDayList = subHolidayMapper.usersearch(subHolidayInputDTO.companyCode
                                                                                 , ('%'+ subHolidayInputDTO.userId +'%')
                                                                                 , ('%'+ subHolidayInputDTO.userName +'%'));
        return ServerResponse.createBySuccess(ScheduledWorkDayList);
    }
    //    代休残入力画面：重複データの確認
    @Override
    public ServerResponse<String> checkSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        GetSubHolidayCntDTO getSubHolidayCntDTO = subHolidayMapper.checkSubHoliday(subHolidayDTO.getCompanyCode(),subHolidayDTO.getUserId(),subHolidayDTO.getHolidayWorkDay());
        int checkSubHolidayCNT = Integer.parseInt(getSubHolidayCntDTO.getCNT());
        if(checkSubHolidayCNT >= ConstCode.NUM_1) {
            return ServerResponse.createBySuccess("代休残テーブルに同じ日付のデータが存在しています。","1");
        }
        return ServerResponse.createBySuccess("代休残テーブルに同じ日付のデータが存在しません。","0");
    }
    //    代休残入力画面：作成押下時の処理（代休残に登録）
    @Override
    public ServerResponse<String> subHolidayCreate(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response){

        // 代休残の作成
        SubHoliday subHoliday = new SubHoliday();
        Date date = new Date();
        subHoliday.setCompanyCode(subHolidayDTO.companyCode);// 会社コード
        subHoliday.setUserId(subHolidayDTO.userId);// ユーザーID
        subHoliday.setHolidayWorkDay(subHolidayDTO.holidayWorkDay);// 休日出勤日
        subHoliday.setSubHolidayDays(subHolidayDTO.subHolidayDays);// 日数
        subHoliday.setRequestNumber(subHolidayDTO.requestNumber);// 申請番号
        subHoliday.setRegistrationUser(subHolidayDTO.registrationUser);// 登録者
        subHoliday.setRegistrationDatetime(date);// 登録日時
        subHoliday.setUpdateUser(subHolidayDTO.registrationUser);// 更新者
        subHoliday.setUpdateDatetime(date);// 更新日時

        int result = subHolidayMapper.insert(subHoliday);

        if (result == 0) {
            ServerResponse.createByError();
        }

        // 休日出勤申請内容の作成,更新
        WorkDayOff workDayOff = new WorkDayOff();
        if(subHolidayDTO.createOrUpdateKbn.equals("1")){
            // 休日出勤申請内容の作成(新規)
            workDayOff.setCompanyCode(subHolidayDTO.companyCode);// 会社コード
            workDayOff.setRequestNumber(subHolidayDTO.requestNumber);// 申請番号
            workDayOff.setRequestUserId(subHolidayDTO.userId);// ユーザーID
            workDayOff.setWorkTypeKbn("021");// 出勤種類区分
            workDayOff.setScheduledWorkDay(subHolidayDTO.holidayWorkDay);// 休日出勤予定日
            workDayOff.setWorkTimeKbn(subHolidayDTO.workTimeKbn);// 休日出勤時刻区分
            workDayOff.setWorkReasonKbn("099");// 休日出勤理由区分
            workDayOff.setRemark("");// 備考
            workDayOff.setRegistrationUser(subHolidayDTO.registrationUser);// 登録者
            workDayOff.setRegistrationDatetime(date);// 登録日時
            workDayOff.setUpdateUser(subHolidayDTO.registrationUser);// 更新者
            workDayOff.setUpdateDatetime(date);// 更新日時

            result = workDayOffMapper.insert(workDayOff);

        }else if(subHolidayDTO.createOrUpdateKbn.equals("2")){
            // 休日出勤申請内容の更新
            workDayOff.setCompanyCode(subHolidayDTO.companyCode);// 会社コード
            workDayOff.setRequestNumber(subHolidayDTO.requestNumber);// 申請番号
            workDayOff.setRequestUserId(subHolidayDTO.userId);// ユーザーID
            workDayOff.setWorkTimeKbn(subHolidayDTO.workTimeKbn);// 休日出勤時刻区分
            workDayOff.setUpdateUser(subHolidayDTO.registrationUser);// 更新者
            workDayOff.setUpdateDatetime(date);// 更新日時

            result = workDayOffMapper.updateByPrimaryKeySelective(workDayOff);
        }

        if (result == 0) {
            ServerResponse.createByError();
        }

        return ServerResponse.createBySuccess();
    }
}
