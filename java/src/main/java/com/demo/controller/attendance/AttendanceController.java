package com.demo.controller.attendance;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.common.Const;
import com.demo.dao.*;
import com.demo.entity.AtndStting;
import com.demo.entity.Attendance;
import com.demo.entity.Category;
import com.demo.pojo.atndApply.AtndApplyRpnDTO;
import com.demo.pojo.atndApply.AtndApplySearchDTO;
import com.demo.pojo.atndStting.AtndSttingDTO;
import com.demo.pojo.atndStting.AtndSttingRpnDTO;
import com.demo.pojo.attendance.ApplyAttendanceDTO;
import com.demo.pojo.attendance.AttendanceDTO;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.attendance.*;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;
import com.demo.pojo.notice.NoticeListDTO;
import com.demo.pojo.notice.NoticeRpnDTO;
import com.demo.pojo.user.UserDetailDTO;
import com.demo.pojo.user.UserDetailInfo;
import com.demo.service.INoticeService;
import net.sf.jasperreports.engine.*;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.ServerResponse;
import com.demo.service.IAttendanceService;

import java.io.*;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Author  : libin
   Time    : 2021/07/12
   Function:
*/
@RestController
@RequestMapping("/attendance/")
public class AttendanceController {

    @Autowired
    IAttendanceService iAttendanceService;

    @Autowired
    AttendanceMapper attendanceMapper;

    @Autowired
    UserMasterMapper userMasterMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    INoticeService iNoticeService;

    @Autowired
    HolidayMapper holidayMapper;

    @Autowired
    AtndSttingMapper atndSttingMapper;


    /**
     * /attendance/getList.do 勤務表詳細情報取得
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDetail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<AttendanceRPnDTO>> getDetail(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {

        return iAttendanceService.getDetail(attendanceDTO, request, response);
    }

    @RequestMapping(value = "/selectByApply.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<ApplyAttendanceDTO>> selectByApply(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                                                  HttpServletResponse response) throws Exception {

        return iAttendanceService.selectByApply(attendanceDTO, request, response);
    }

    @RequestMapping(value = "/selectByApplyActivity.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<ApplyAttendanceDTO>> selectByApplyActivity(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                                                          HttpServletResponse response) throws Exception {

        return iAttendanceService.selectByApplyActivity(attendanceDTO, request, response);
    }

    /**
     * /attendance/search.do 勤務表検索
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<AttendanceRPnDTO>> search(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                                         HttpServletResponse response) throws Exception {

        return iAttendanceService.search(attendanceDTO, request, response);
    }

    /**
     * /attendance/create.do 勤務表登録　保存・申請
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> create(@RequestBody List<AttendanceRPnDTO> attendanceRPnDTOList, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iAttendanceService.create(attendanceRPnDTOList, request, response);
    }

    /**
     * /attendance/create.do 勤務表登録　更新
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/attendanceCreateUpdate.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> attendanceCreateUpdate(@RequestBody List<AttendanceRPnDTO> attendanceRPnDTOList, HttpServletRequest request,
                                                         HttpServletResponse response) throws Exception {

        return iAttendanceService.attendanceCreateUpdate(attendanceRPnDTOList, request, response);
    }

    /**
     * /attendance/searchTime.do 勤務表登録
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchTime.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Attendance> searchTime(@RequestBody Map<String, String> params, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        String companyCode = params.get("companyCode");
        String userId = params.get("userId");
        String workDate = params.get("workDate");

        return ServerResponse.createBySuccess(attendanceMapper.selectByPrimaryKey(companyCode,userId,workDate));
    }

    /**
     * /attendance/aprove.do 勤務表　上司承認
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {Const.ADMIN_USER})
    @RequestMapping(value = "/aprove.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> aprove(@RequestBody AttendanceRPnDTO attendanceRPnDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iAttendanceService.aprove(attendanceRPnDTO, request, response);
    }

    /**
     * /attendance/workStart.do 出勤登録
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/workStart.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> workStart(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {

        return iAttendanceService.workStart(attendanceDTO, request, response);
    }

    /**
     * /attendance/workEnd.do 退勤登録
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/workEnd.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> workEnd(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {

        return iAttendanceService.workEnd(attendanceDTO, request, response);
    }

    /**
     * /attendance/workStartSetting.do 出勤登録
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/workStartSetting.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> workStartSetting(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {

        return iAttendanceService.workStartSetting(attendanceDTO, request, response);
    }

    /**
     * /attendance/workEndSetting.do 退勤登録
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/workEndSetting.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> workEndSetting(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        return iAttendanceService.workEndSetting(attendanceDTO, request, response);
    }

    /**
     * /attendance/getAtndStting.do 勤務表詳細情報取得
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAtndStting.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<AtndSttingRpnDTO>> getAtndStting(@RequestBody AtndSttingDTO atndSttingDTO, HttpServletRequest request,
                                                                HttpServletResponse response) throws Exception {

        return iAttendanceService.getAtndStting(atndSttingDTO, request, response);
    }

    /**
     * /attendance/getAtndAStting.do 勤務表詳細情報取得
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportToProveExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportToProveExcel(@RequestBody AttendanceDTO attendanceDTO, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        AttendanceExcelFileDTO excelFile  = iAttendanceService.createAttendanceFileStream(attendanceDTO);

        response.resetBuffer();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + excelFile.fileName);

        ServletOutputStream outputStream = response.getOutputStream();

        byte[] buffer = excelFile.fileContent.toByteArray();
        outputStream.write(buffer);
        outputStream.flush();
        response.flushBuffer();
    }

    /**
     * /attendance/exportToAttendancePdf.do 勤務表PDF出力処理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportToAttendancePdf.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportToAttendancePdf(@RequestBody BulkExportDTO bulkExportDTO, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        try {
            Map<String, Object> cMap = iAttendanceService.outPutAttendancePdfStream(bulkExportDTO);
            // TODO:リリース用テンプファイルパス
//            File jrxmlFile = ResourceUtils.getFile("/home/demo/AttendancePdf.jrxml");
            // 開発用テンプファイルパス
            File jrxmlFile = ResourceUtils.getFile("classpath:report/AttendancePdf.jrxml");
            JasperReport report = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
            JasperPrint jspPrint = JasperFillManager.fillReport(report, cMap, new JREmptyDataSource());
            byte[] output = JasperExportManager.exportReportToPdf(jspPrint);
            response.resetBuffer();
            response.setContentType("application/pdf;charset=UTF-8");
            response.setHeader("Content-Disposition","inline;");
            response.setContentLength(output.length);

            OutputStream os = response.getOutputStream();
            os.write(output);
            os.flush();
            os.close();
            response.flushBuffer();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * /attendance/getNoticeList.do お知らせ一覧情報取得
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getNoticeList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<NoticeRpnDTO>> getNotice(@RequestBody NoticeListDTO noticeListDTO, HttpServletRequest request,
                                                        HttpServletResponse response) throws Exception {

        return iNoticeService.getNoticeList(noticeListDTO, request, response);
    }

    /**
     * /attendance/getNoticeDetil.do お知らせ詳細情報取得
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getNoticeDetil.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<NoticeRpnDTO> getNoticeDetil(@RequestBody NoticeListDTO noticeListDTO, HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception {

        return iNoticeService.getNoticeById(noticeListDTO, request, response);
    }

    //  月末締一括出力(検索押下時の処理）
    /**
     * /attendance/searchBulkExport.do 月末締一括出力(検索押下時の処理）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchBulkExport.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<BulkExportDTO>> searchBulkExport(@RequestBody BulkExportDTO bulkExportDTO,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) throws Exception {
        return iAttendanceService.searchBulkExport(bulkExportDTO, request, response);
    }

    /**
     * /attendance/getAtndAStting.do 勤怠表エクスポート(勤務表詳細情報取得・一括Excelエクスポート)
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportToMultiProveExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportToMultiProveExcel(@RequestBody MultiAttendanceDTO multiAttendanceDTO, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        for (int index=0;index<multiAttendanceDTO.userIdList.size();index++){
            AttendanceDTO attendanceDTO = new AttendanceDTO();
            attendanceDTO.companyCode = multiAttendanceDTO.companyCodeList.get(index);
            attendanceDTO.userId = multiAttendanceDTO.userIdList.get(index);
            attendanceDTO.workDateYm = multiAttendanceDTO.workDateYmList.get(index);

            AttendanceExcelFileDTO excelFile  = iAttendanceService.createAttendanceFileStream(attendanceDTO);
            byte[] buffer = excelFile.fileContent.toByteArray();

            try{
                ZipEntry entry = new ZipEntry(excelFile.fileName);


                zos.putNextEntry(entry);
                zos.write(buffer);
                zos.closeEntry();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
        zos.close();

        response.resetBuffer();
        response.setContentType("application/zip");

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(baos.toByteArray());
        outputStream.flush();
        response.flushBuffer();
    }

    /**
     * /attendance/exportToProveBulkPdf.do 勤怠表エクスポート(勤務表詳細情報取得・一括Pdfエクスポート)
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportToProveBulkPdf.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportToProveBulkPdf(@RequestBody MultiAttendanceDTO multiAttendanceDTO, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        for (int index=0;index<multiAttendanceDTO.userIdList.size();index++){
            BulkExportDTO bulkExportDTO = new BulkExportDTO();
            bulkExportDTO.companyCode = multiAttendanceDTO.companyCodeList.get(index);
            bulkExportDTO.userId = multiAttendanceDTO.userIdList.get(index);
            bulkExportDTO.workDateYm = multiAttendanceDTO.workDateYmList.get(index);
            bulkExportDTO.requestStatusKbn = multiAttendanceDTO.requestStatusKbnList.get(index);
            bulkExportDTO.accessMode = "2";

            Map<String, Object> cMap   = iAttendanceService.outPutAttendancePdfStream(bulkExportDTO);
            // TODO:リリース用ファイルパス
            //                リリース用
//            File jrxmlFile = ResourceUtils.getFile("/home/demo/AttendancePdf.jrxml");

            // 開発用テンプファイルパス
            File jrxmlFile = ResourceUtils.getFile("classpath:report/AttendancePdf.jrxml");
            JasperReport report = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
            JasperPrint jspPrint = JasperFillManager.fillReport(report, cMap, new JREmptyDataSource());
            byte[] buffer = JasperExportManager.exportReportToPdf(jspPrint);
            UserDetailDTO userDetailDTO = userMasterMapper.getUser(bulkExportDTO.companyCode, bulkExportDTO.userId);
            String fileName = bulkExportDTO.userId + "_" + bulkExportDTO.workDateYm + "_勤務表" + "_" + userDetailDTO.getUserName() + ".pdf";
            try{
                ZipEntry entry = new ZipEntry(fileName);

                zos.putNextEntry(entry);
                zos.write(buffer);
                zos.closeEntry();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
        zos.close();

        response.resetBuffer();
        response.setContentType("application/zip");

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(baos.toByteArray());
        outputStream.flush();
        response.flushBuffer();
    }
}
