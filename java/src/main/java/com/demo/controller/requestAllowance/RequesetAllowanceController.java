package com.demo.controller.requestAllowance;

import com.demo.common.ServerResponse;
import com.demo.dao.RequestAllowanceMapper;
import com.demo.dao.RequestMapper;
import com.demo.dao.UserMasterMapper;
import com.demo.entity.Request;
import com.demo.pojo.requestAllowance.GetApplyYmDTO;
import com.demo.pojo.requestAllowance.QualificationAllowanceDTO;
import com.demo.pojo.requestAllowance.RequestAllowanceDTO;
import com.demo.pojo.user.UserDetailDTO;
import com.demo.pojo.user.UserDetailInfo;
import com.demo.service.IRequestAllowanceService;
import net.sf.jasperreports.engine.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requestAllowance/")
public class RequesetAllowanceController {
    @Autowired
    IRequestAllowanceService iRequestAllowanceService;

    @Autowired
    RequestAllowanceMapper requestAllowanceMapper;

    @Autowired
    UserMasterMapper userMasterMapper;

    @Autowired
    RequestMapper requestMapper;

    // 手当申請：詳細処理
    @RequestMapping(value = "/getRequestAllowance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<RequestAllowanceDTO>> getRequestAllowance(@RequestBody RequestAllowanceDTO  requestAllowanceDTO,
                                                                                     HttpServletRequest request,
                                                                                     HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.getRequestAllowance(requestAllowanceDTO, request, response);
    }
    // 手当申請：レコード削除処理
    @RequestMapping(value = "/deleteRequestAllowance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteRequestAllowance(@RequestBody RequestAllowanceDTO requestAllowanceDTO,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.deleteRequestAllowance(requestAllowanceDTO, request, response);
    }

    // 手当申請：出張,リーダー手当申請の保存処理
    @RequestMapping(value = "/insertAllowance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertAllowance(@RequestBody RequestAllowanceDTO requestAllowanceDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.insertAllowance(requestAllowanceDTO, request, response);
    }
    // 手当申請：参照処理
    @RequestMapping(value = "/searchReAllowanceMonthData.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<RequestAllowanceDTO>> searchReAllowanceMonthData(@RequestBody RequestAllowanceDTO  requestAllowanceDTO,
                                                                              HttpServletRequest request,
                                                                              HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.searchReAllowanceMonthData(requestAllowanceDTO, request, response);
    }
    // 手当申請：年月取得
    @RequestMapping(value = "/searchMonth.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<GetApplyYmDTO>> searchMonth(@RequestBody GetApplyYmDTO getApplyYmDTO,
                                                                                HttpServletRequest request,
                                                                                HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.searchMonth(getApplyYmDTO, request, response);
    }

    // 手当申請：資格手当申請の保存処理
    @RequestMapping(value = "/insertQualificationAllowance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertQualificationAllowance(@RequestBody QualificationAllowanceDTO qualificationAllowanceDTO,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.insertQualificationAllowance(qualificationAllowanceDTO, request, response);
    }

    // 手当申請：資格手当申請のチェック処理
    @RequestMapping(value = "/qualificationAllowanceCheck.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> qualificationAllowanceCheck(@RequestBody QualificationAllowanceDTO qualificationAllowanceDTO,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.qualificationAllowanceCheck(qualificationAllowanceDTO, request, response);
    }
    // 手当申請：慶弔見舞金申請の保存処理
    @RequestMapping(value = "/insertCongratulatoryOrCondolencePayments.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertCongratulatoryOrCondolencePayments(@RequestBody RequestAllowanceDTO requestAllowanceDTO,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) throws Exception {
        return iRequestAllowanceService.insertCongratulatoryOrCondolencePayments(requestAllowanceDTO, request, response);
    }

    /**
     * /requestAllowance/exportToPDF.do PDF出力処理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportToPDF.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportToPDF(@RequestBody RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {

        SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy年MM月" );
        SimpleDateFormat sdf2 =   new SimpleDateFormat( "yyyyMM" );
        UserDetailInfo userDetailInfo = userMasterMapper.getUserDetailInfo(requestAllowanceDTO.companyCode,requestAllowanceDTO.requestUserId);
        UserDetailDTO userDetailDTO = userMasterMapper.getUser(requestAllowanceDTO.companyCode,requestAllowanceDTO.requestUserId);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        Map<String, Object> parameters1 = new HashMap();

        parameters1.put("department_name",userDetailInfo.getDepartmentName());
        parameters1.put("user_id",userDetailInfo.getUserId());
        parameters1.put("user_name",userDetailDTO.getUserName());
        parameters1.put("apply_ym",sdf1.format(sdf2.parse(requestAllowanceDTO.applyYm)));
        parameters1.put("allowance",decimalFormat.format(requestAllowanceDTO.allowance));
        parameters1.put("quantity",decimalFormat.format(requestAllowanceDTO.quantity));
        parameters1.put("total_amount",decimalFormat.format(requestAllowanceDTO.total));
        parameters1.put("remark",requestAllowanceDTO.remark1);

        Request request_details = requestMapper.selectByPrimaryKey(requestAllowanceDTO.companyCode,requestAllowanceDTO.requestNumber);
        String applyStatus = "";
        if(StringUtils.isEmpty(request_details.getRequestStatusKbn()) || request_details.getRequestStatusKbn().equals("001")){
            applyStatus = "未申請";
        }else if(request_details.getRequestStatusKbn().equals("003")){
            applyStatus = "回覧中";
        }else if(request_details.getRequestStatusKbn().equals("010")){
            applyStatus = "差戻";
        }else if(request_details.getRequestStatusKbn().equals("999")){
            applyStatus = "完了";
        }
        String applyDate = request_details.getRequestDate();
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy年MM月dd日" );
        SimpleDateFormat sdfFormat =   new SimpleDateFormat( "yyyyMMdd" );
        parameters1.put("strApplyStatus", applyStatus);
        parameters1.put("apply_date",sdf.format(sdfFormat.parse(applyDate)));

        try {
            // TODO:リリース用ファイルパス
            //                リリース用
//            File jrxmlFile = ResourceUtils.getFile("/home/demo/AllowanceBusinessTripPdf.jrxml");
            // 開発用ファイルパス
            File jrxmlFile = ResourceUtils.getFile("classpath:report/AllowanceBusinessTripPdf.jrxml");
            JasperReport report = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
            JasperPrint jspPrint = JasperFillManager.fillReport(report, parameters1, new JREmptyDataSource());
            byte[] output = JasperExportManager.exportReportToPdf(jspPrint);
            response.resetBuffer();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "inline; filename=" + "testPdf.pdf");
            response.setContentLength(output.length);

            OutputStream os = null;
            os = response.getOutputStream();
            os.write(output);
            os.flush();
            os.close();
            response.flushBuffer();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
