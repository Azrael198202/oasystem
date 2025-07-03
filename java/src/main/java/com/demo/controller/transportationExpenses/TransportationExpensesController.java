package com.demo.controller.transportationExpenses;

import com.demo.common.ServerResponse;
import com.demo.dao.RequestMapper;
import com.demo.dao.TransportationExpensesMapper;
import com.demo.dao.UserMasterMapper;
import com.demo.entity.Request;
import com.demo.entity.report.ExpensePdf;
import com.demo.entity.TransportationExpenses;
import com.demo.entity.report.TransportationExpensesOtherTransportPdf;
import com.demo.entity.report.TransportationExpensesTransportPdf;
import com.demo.pojo.attendance.BulkExportDTO;
import com.demo.pojo.attendance.MultiAttendanceDTO;
import com.demo.pojo.transportationExpenses.MultiTransportDTO;
import com.demo.pojo.transportationExpenses.TransportationExpensesDTO;
import com.demo.pojo.transportationExpenses.TransportationExpensesPdfFiledDTO;
import com.demo.pojo.user.UserDetailDTO;
import com.demo.pojo.user.UserDetailInfo;
import com.demo.pojo.transportationExpenses.SelectApplyMonthTpDTO;
import com.demo.pojo.transportationExpenses.TransportationExpensesDTO;
import com.demo.service.ITransportationExpensesService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/transportationExpenses/")
public class TransportationExpensesController {
    @Autowired
    ITransportationExpensesService iTransportationExpensesService;

    @Autowired
    TransportationExpensesMapper transportationExpensesMapper;

    @Autowired
    UserMasterMapper userMasterMapper;

    @Autowired
    RequestMapper requestMapper;

    @RequestMapping(value = "/insertTransportationExpenses.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertTransportationExpenses(@RequestBody List<TransportationExpensesDTO>  transportationExpensesDTO,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws Exception {

        return iTransportationExpensesService.insertTransportationExpenses(transportationExpensesDTO, request, response);
    }

    @RequestMapping(value = "/getTransportationExpenses.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<TransportationExpensesDTO>> getTransportationExpenses(@RequestBody TransportationExpensesDTO  transportationExpensesDTO,
                                                                             HttpServletRequest request,
                                                                             HttpServletResponse response) throws Exception {

        return iTransportationExpensesService.getTransportationExpenses(transportationExpensesDTO, request, response);
    }

    @RequestMapping(value = "/deleteTransportationExpenses.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delete(@RequestBody TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iTransportationExpensesService.deleteTransportationExpenses(transportationExpensesDTO,request, response);
    }

    @RequestMapping(value = "updateTransportationExpenses.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateTransportationExpenses(@RequestBody List<TransportationExpensesDTO> transportationExpensesDTO, HttpServletRequest request,
                                                       HttpServletResponse response) {

        return iTransportationExpensesService.updateTransportationExpenses(transportationExpensesDTO,request ,response);
    }

    @RequestMapping(value = "/searchRefMonthData.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<TransportationExpensesDTO>> searchRefMonthData(@RequestBody TransportationExpensesDTO  transportationExpensesDTO,
                                                                                     HttpServletRequest request,
                                                                                     HttpServletResponse response) throws Exception {

        return iTransportationExpensesService.searchRefMonthData(transportationExpensesDTO, request, response);
    }

    @RequestMapping(value = "/selectMonth.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<SelectApplyMonthTpDTO> selectMonth(@RequestBody SelectApplyMonthTpDTO  selectApplyMonthTpDTO,
                                                                              HttpServletRequest request,
                                                                              HttpServletResponse response) throws Exception {

        return iTransportationExpensesService.selectMonth(selectApplyMonthTpDTO, request, response);
    }

    @RequestMapping(value = "/saveImgTemporary.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> saveImgTemporary(@RequestBody TransportationExpensesDTO transportationExpensesDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {

        return iTransportationExpensesService.saveImgTemporary(transportationExpensesDTO, request, response);
    }

    @RequestMapping(value = "/deleteImageTemporary.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteImageTemporary(@RequestBody String  userId,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception {

        return iTransportationExpensesService.deleteImageTemporary(userId, request, response);
    }

    /**
     * /transportationExpenses/exportToPDF.do 交通費PDF出力処理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportToPDF.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportToPDF(@RequestBody TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {

        /* 表示データを取得する */

        // １）定期券、旅費、その他経費の明細情報を取得する
        List<TransportationExpensesTransportPdf> compassList = transportationExpensesMapper.selectTransportPdfData(
                transportationExpensesDTO.companyCode, transportationExpensesDTO.requestUserId, transportationExpensesDTO.requestNumber);
        List<TransportationExpensesOtherTransportPdf> otherList = transportationExpensesMapper.selectOtherTransportPdfData(
                transportationExpensesDTO.companyCode, transportationExpensesDTO.requestUserId, transportationExpensesDTO.requestNumber);
        List<ExpensePdf> expenseList = transportationExpensesMapper.selectExpensePdfData(
                transportationExpensesDTO.companyCode, transportationExpensesDTO.requestUserId, transportationExpensesDTO.requestNumber);

        // ２）画像情報を取得する
        List<TransportationExpensesTransportPdf> compassImageList = compassList.size() > 0
                ? compassList.stream().filter(row -> !StringUtils.isEmpty(row.getStrImg())).collect(Collectors.toList())
                : new ArrayList<>();
        List<TransportationExpensesOtherTransportPdf> otherImageList = otherList.size() > 0
                ? otherList.stream().filter(row -> !StringUtils.isEmpty(row.getStrImg())).collect(Collectors.toList())
                : new ArrayList<>();
        List<ExpensePdf> expenseImageList = expenseList.size() > 0
                ? expenseList.stream().filter(row -> !StringUtils.isEmpty(row.getStrImg())).collect(Collectors.toList())
                : new ArrayList<>();

        // ３）明細ページにあるフィールドの値（ヘッダー部など）を設置する
        List<TransportationExpensesPdfFiledDTO> fields = new ArrayList<>();
        TransportationExpensesPdfFiledDTO rows = new TransportationExpensesPdfFiledDTO();
        UserDetailInfo userInfo = userMasterMapper.getUserDetailInfo(transportationExpensesDTO.companyCode, transportationExpensesDTO.requestUserId);
        rows.setAddress(userInfo.getAddressPrefecture()
                + userInfo.getAddressMunicipality() + userInfo.getAddressTown() + userInfo.getAddressDetail());     // 住所
        Request request_info = requestMapper.selectByPrimaryKey(transportationExpensesDTO.companyCode,transportationExpensesDTO.requestNumber);
        rows.setApply_month(transportationExpensesDTO.applyYm + "月分");  // 申請月
        rows.setApply_date(request_info.getRequestDate().substring(0,4) + '/' + request_info.getRequestDate().substring(4,6) + '/' + request_info.getRequestDate().substring(6,8));  // 申請日
        rows.setDepartment_name(userInfo.getDepartmentName());  // 所属
        rows.setEmployee_no(userInfo.getUserId());  // 社員番号
        rows.setName(userInfo.getFirstName() + userInfo.getLastName()); // 氏名

        // 定期券の小計を算出し、フィールドへセットする
        int sumCompass = compassList.stream()
                .mapToInt(value-> Integer.parseInt(value.getAmount().toString()))
                .sum();
        //rows.setCompass_subtotal_amount( String.format("%,d", sumCompass));

        // 旅費の小計を算出し、フィールドへセットする
        int sumOther = otherList.stream()
                .mapToInt(value-> Integer.parseInt(value.getAmount().toString()))
                .sum();
        //rows.setOther_subtotal_amount( String.format("%,d", sumOther));

        // その他の小計を算出し、フィールドへセットする
        int sumExpense = expenseList.stream()
                .mapToInt(value-> Integer.parseInt(value.getAmount().toString()))
                .sum();
        //rows.setExpense_subtotal_amount( String.format("%,d", sumExpense));

        // 定期・旅費・その他経費の合計
        rows.setTotal_amount(BigDecimal.valueOf(sumCompass + sumOther + sumExpense));

        // JRBeanCollectionDataSourceを利用して格納された値をJasperテンプレートのフィールド項目に埋め込む
        fields.add(rows);
        JRBeanCollectionDataSource jrbeanDtSource = new JRBeanCollectionDataSource(fields);
        Request request_details = requestMapper.selectByPrimaryKey(transportationExpensesDTO.companyCode,transportationExpensesDTO.requestNumber);
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
        // 取得してきた定期券明細情報をJasperレポート側のデータソースとバインドする
        Map<String, Object> parameters1 = new HashMap<>();
        parameters1.put("datasourceTransport", compassList);
        parameters1.put("datasourceOther", otherList);
        parameters1.put("datasourceExpense", expenseList);
        parameters1.put("strApplyStatus", applyStatus);

        // 定期券画像をJasperレポート側のデータソースとバインドする
        Map<String, Object> parameters2 = new HashMap<>();
//        parameters2.put("datasourceCompassImg", compassImageList);
//        parameters2.put("datasourceOtherImg", otherImageList);
        try {
            // テンプレートファイル（明細ページ）を読み込み、データをレポートへ引き渡す
            // TODO:リリース用ファイルパス
            //                リリース用
//            File jrxmlFile1 = ResourceUtils.getFile("/home/demo/TransportationExpensesDetailPdf.jrxml");
            // 開発用ファイルパス
            File jrxmlFile1 = ResourceUtils.getFile("classpath:report/TransportationExpensesDetailPdf.jrxml");
            JasperReport report1 = JasperCompileManager.compileReport(jrxmlFile1.getAbsolutePath());
            JasperPrint jasperPrint1 = JasperFillManager.fillReport(report1, parameters1, jrbeanDtSource);

            /*if(compassImageList.size() > 0 || otherImageList.size() > 0 || expenseImageList.size() > 0){
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                //                リリース用
//                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
                //File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }
            */
            if(compassImageList.size() > 0){
                parameters2 = new HashMap<>();
                parameters2.put("datasourceImg", compassImageList);
                parameters2.put("imgTitleName", "　定　期　券　");
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                //                リリース用
//                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
                File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }

            if(otherImageList.size() > 0){
                parameters2 = new HashMap<>();
                parameters2.put("datasourceImg", otherImageList);
                parameters2.put("imgTitleName", "　経　費　（旅　費）　");
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                //                リリース用
//                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
                File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }

            if(expenseImageList.size() > 0){
                parameters2 = new HashMap<>();
                parameters2.put("datasourceImg", expenseImageList);
                parameters2.put("imgTitleName", "　経　費　（その他）　");
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                //                リリース用
//                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
                File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }
            //PDF出力処理(ファイル名のみ指定したら、src\main\resources直下へ出力)
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "receipt.pdf");

            //PDFダウンロード処理
            byte[] output = JasperExportManager.exportReportToPdf(jasperPrint1);
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

        //System.out.println("終了しました");
    }

    /**
     * /transportationExpenses/exportToProveBulkPdf.do 月末精算エクスポート(月末精算詳細情報取得・一括Pdfエクスポート)
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportToProveBulkPdf.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportToProveBulkPdf(@RequestBody MultiTransportDTO multiTransportDTO, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        String applyYm = (multiTransportDTO.applyYmList.size() > 0) ? multiTransportDTO.applyYmList.get(0) : "";
        for (int index=0; index<multiTransportDTO.userIdList.size(); index++){

            String companyCode = multiTransportDTO.companyCodeList.get(index);
            String userId = multiTransportDTO.userIdList.get(index);

            String transRequestNumber = multiTransportDTO.requestNumberIdList.get(index);

            // １）定期券、旅費、その他経費の明細情報を取得する
            List<TransportationExpensesTransportPdf> compassList = transportationExpensesMapper.selectTransportPdfData(
                    companyCode, userId, transRequestNumber);
            List<TransportationExpensesOtherTransportPdf> otherList = transportationExpensesMapper.selectOtherTransportPdfData(
                    companyCode, userId, transRequestNumber);
            List<ExpensePdf> expenseList = transportationExpensesMapper.selectExpensePdfData(
                    companyCode, userId, transRequestNumber);

            // ２）画像情報を取得する
            List<TransportationExpensesTransportPdf> compassImageList = compassList.size() > 0
                    ? compassList.stream().filter(row -> !StringUtils.isEmpty(row.getStrImg())).collect(Collectors.toList())
                    : new ArrayList<>();
            List<TransportationExpensesOtherTransportPdf> otherImageList = otherList.size() > 0
                    ? otherList.stream().filter(row -> !StringUtils.isEmpty(row.getStrImg())).collect(Collectors.toList())
                    : new ArrayList<>();
            List<ExpensePdf> expenseImageList = expenseList.size() > 0
                    ? expenseList.stream().filter(row -> !StringUtils.isEmpty(row.getStrImg())).collect(Collectors.toList())
                    : new ArrayList<>();

            // ３）明細ページにあるフィールドの値（ヘッダー部など）を設置する
            List<TransportationExpensesPdfFiledDTO> fields = new ArrayList<>();
            TransportationExpensesPdfFiledDTO rows = new TransportationExpensesPdfFiledDTO();
            UserDetailInfo userInfo = userMasterMapper.getUserDetailInfo(companyCode, userId);
            rows.setAddress(userInfo.getAddressPrefecture()
                    + userInfo.getAddressMunicipality() + userInfo.getAddressTown() + userInfo.getAddressDetail());     // 住所
            String outPutYm = multiTransportDTO.applyYmList.get(index);
            String outPutMonth = StringUtils.isEmpty(outPutYm) ? "" : outPutYm.substring(4,6);
            rows.setApply_month(outPutMonth + "月分");  // 申請月
            rows.setApply_date(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));  // 申請日
            rows.setDepartment_name(userInfo.getDepartmentName());  // 所属
            rows.setEmployee_no(userInfo.getUserId());  // 社員番号
            rows.setName(userInfo.getFirstName() + userInfo.getLastName()); // 氏名

            // 定期券の小計を算出し、フィールドへセットするF
            int sumCompass = compassList.stream()
                    .mapToInt(value-> Integer.parseInt(value.getAmount().toString()))
                    .sum();

            // 旅費の小計を算出し、フィールドへセットする
            int sumOther = otherList.stream()
                    .mapToInt(value-> Integer.parseInt(value.getAmount().toString()))
                    .sum();

//            rows.setTotal_amount(BigDecimal.valueOf(sumCompass + sumOther));

            // その他の小計を算出し、フィールドへセットする
            int sumExpense = expenseList.stream()
                    .mapToInt(value-> Integer.parseInt(value.getAmount().toString()))
                    .sum();
            //rows.setExpense_subtotal_amount( String.format("%,d", sumExpense));

            // 定期・旅費・その他経費の合計
            rows.setTotal_amount(BigDecimal.valueOf(sumCompass + sumOther + sumExpense));


            // JRBeanCollectionDataSourceを利用して格納された値をJasperテンプレートのフィールド項目に埋め込む
            fields.add(rows);
            JRBeanCollectionDataSource jrbeanDtSource = new JRBeanCollectionDataSource(fields);

            Request request_details = requestMapper.selectByPrimaryKey(companyCode,transRequestNumber);
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

            // 取得してきた定期券明細情報をJasperレポート側のデータソースとバインドする
            Map<String, Object> parameters1 = new HashMap<>();
            parameters1.put("datasourceTransport", compassList);
            parameters1.put("datasourceOther", otherList);
            parameters1.put("datasourceExpense", expenseList);
            parameters1.put("strApplyStatus", applyStatus);

            // 定期券画像をJasperレポート側のデータソースとバインドする
            Map<String, Object> parameters2 = new HashMap<>();
            //parameters2.put("datasourceCompassImg", compassImageList);
            //parameters2.put("datasourceOtherImg", otherImageList);

            // テンプレートファイル（明細ページ）を読み込み、データをレポートへ引き渡す
            // TODO:リリース用ファイルパス
//            File jrxmlFile1 = ResourceUtils.getFile("/home/demo/TransportationExpensesDetailPdf.jrxml");
            // 開発用ファイルパス
            File jrxmlFile1 = ResourceUtils.getFile("classpath:report/TransportationExpensesDetailPdf.jrxml");
            JasperReport report1 = JasperCompileManager.compileReport(jrxmlFile1.getAbsolutePath());
            JasperPrint jasperPrint1 = JasperFillManager.fillReport(report1, parameters1, jrbeanDtSource);
/*
            if(compassImageList.size() > 0 || otherImageList.size() > 0){
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
//                File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }
*/
            if(compassImageList.size() > 0){
                parameters2 = new HashMap<>();
                parameters2.put("datasourceImg", compassImageList);
                parameters2.put("imgTitleName", "　定　期　券　");
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                //                リリース用
//                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
                File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }

            if(otherImageList.size() > 0){
                parameters2 = new HashMap<>();
                parameters2.put("datasourceImg", otherImageList);
                parameters2.put("imgTitleName", "　経　費　（旅　費）　");
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                //                リリース用
//                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
                File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }

//            その他経費
            if(expenseImageList.size() > 0){
                parameters2 = new HashMap<>();
                parameters2.put("datasourceImg", expenseImageList);
                parameters2.put("imgTitleName", "　経　費　（その他）　");
                // 画像データがあれば、テンプレートファイル（画像ページ）を読み込み、データをレポートへ引き渡す
                // TODO:リリース用ファイルパス
                //                リリース用
//                File jrxmlFile2 = ResourceUtils.getFile("/home/demo/TransportationExpensesImgPdf.jrxml");
                // 開発用ファイルパス
                File jrxmlFile2 = ResourceUtils.getFile("classpath:report/TransportationExpensesImgPdf.jrxml");
                JasperReport report2 = JasperCompileManager.compileReport(jrxmlFile2.getAbsolutePath());
                JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

                // 明細ページ＋画像ページを結合して一つPDFファイルになる
                List<JRPrintPage> pages = jasperPrint2.getPages();
                for(JRPrintPage page : pages) {
                    jasperPrint1.addPage(page);
                }
            }

            //PDF出力処理(ファイル名のみ指定したら、src\main\resources直下へ出力)
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "receipt.pdf");

            //PDFダウンロード処理
            byte[] buffer = JasperExportManager.exportReportToPdf(jasperPrint1);
            // 開発用テンプファイルパス（ファイル名は申請番号に付けるよう変更
            // 理由：同じ月に複数申請ができるので、「ID+年月」の形式だと複数の場合、最後のデータのみ出力されてしまう（前のデータがどんどん上書きされてしまう））
            UserDetailDTO userDetailDTO = userMasterMapper.getUser(companyCode, userId);
            String categoryTitle = "";
            if(compassList.size() >= 1 && otherList.size() == 0 && expenseList.size() == 0){
                categoryTitle = "定";
            }else if(compassList.size() == 0 && otherList.size() >= 1 && expenseList.size() == 0){
                categoryTitle = "旅";
            }else if(compassList.size() == 0 && otherList.size() == 0 && expenseList.size() >= 1){
                categoryTitle = "他";
            }else if(compassList.size() >= 1 && otherList.size() >= 1 && expenseList.size() == 0){
                categoryTitle = "定_旅";
            }else if(compassList.size() >= 1 && otherList.size() == 0 && expenseList.size() >= 1){
                categoryTitle = "定_他";
            }else if(compassList.size() == 0 && otherList.size() >= 1 && expenseList.size() >= 1){
                categoryTitle = "旅_他";
            }else if(compassList.size() >= 1 && otherList.size() >= 1 && expenseList.size() >= 1){
                categoryTitle = "定_旅_他";
            }
//            String fileName = userId + "_" + categoryTitle + "_" + transRequestNumber.replace(companyCode + userId,"").replace(applyYm,"") + "_月末精算" + "_" + userDetailDTO.getUserName() + ".pdf";
            String fileName = userId + "_" + categoryTitle + "_" + transRequestNumber.substring(6,17) + "_月末精算" + "_" + userDetailDTO.getUserName() + ".pdf";

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
