package com.demo.controller.resume;

import com.demo.common.ServerResponse;
import com.demo.dao.ResumeMapper;
import com.demo.dao.UserMasterMapper;
import com.demo.pojo.resume.*;

import com.demo.pojo.user.UserDetailDTO;
import com.demo.service.IResumeService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/resume/")
public class ResumeController {
    @Autowired
    IResumeService iResumeService;

    @Autowired
    ResumeMapper resumeMapper;

    @Autowired
    UserMasterMapper userMasterMapper;

    @RequestMapping(value = "getResumeList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<SearchResumeDTO>> getResumeList(@RequestBody SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        return iResumeService.getResumeList(searchResumeDTO,request,response);
    }

    @RequestMapping(value = "insertResume.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertResume(@RequestBody InsertResumeDTO insertResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        return iResumeService.insertResume(insertResumeDTO,request,response);
    }

    @RequestMapping(value = "updateResume.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateResume(@RequestBody InsertResumeDTO insertResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        return iResumeService.updateResume(insertResumeDTO,request,response);
    }

    @RequestMapping(value = "getResume.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<InsertResumeDTO> getResume(@RequestBody SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        return iResumeService.getResume(searchResumeDTO,request,response);
    }

    @RequestMapping(value = "deleteResume.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteResume(@RequestBody SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        return iResumeService.deleteResume(searchResumeDTO,request,response);
    }

    @RequestMapping(value = "/exportPDF.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportPDF(@RequestBody SearchResumeDTO searchResumeDTO, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        Map<String, Object> parameters1 = iResumeService.exportResume(searchResumeDTO);

        try {
            // TODO:リリース用ファイルパス
            //                リリース用
//            File jrxmlFile = ResourceUtils.getFile("/home/demo/ResumePdf_01.jrxml");
            // 開発用ファイルパス
            File jrxmlFile = ResourceUtils.getFile("classpath:report/ResumePdf_01.jrxml");
            JasperReport report = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
            JasperPrint jspPrint = JasperFillManager.fillReport(report, parameters1, new JREmptyDataSource());
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @RequestMapping(value = "getResumeVerList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<GetResumeVerDTO>> getResumeVerList(@RequestBody SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        return iResumeService.getResumeVerList(searchResumeDTO,request,response);
    }

    @RequestMapping(value = "getUserList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<GetUserDTO>> getUserList(@RequestBody SearchUserDTO searchUserDTO, HttpServletRequest request, HttpServletResponse response) {
        return iResumeService.getUserList(searchUserDTO,request,response);
    }

    @RequestMapping(value = "/exportPDFs.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportPDFs(@RequestBody MultiResumeDTO multiResumeDTO, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        for (String userId:multiResumeDTO.userIdList){
            SearchResumeDTO dto = resumeMapper.searchResumeNew(multiResumeDTO.companyCode,userId);
            if (dto != null) {
                Map<String, Object> parameters1 = iResumeService.exportResume(dto);

                try {
                    // TODO:リリース用ファイルパス
                    //                リリース用
//                    File jrxmlFile = ResourceUtils.getFile("/home/demo/ResumePdf_01.jrxml");
                    // 開発用ファイルパス
                    File jrxmlFile = ResourceUtils.getFile("classpath:report/ResumePdf_01.jrxml");
                    JasperReport report = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
                    JasperPrint jspPrint = JasperFillManager.fillReport(report, parameters1, new JREmptyDataSource());
                    byte[] buffer = JasperExportManager.exportReportToPdf(jspPrint);
                    UserDetailDTO userDetailDTO = userMasterMapper.getUser(multiResumeDTO.companyCode,userId);
                    String fileName = userId + "_" + userDetailDTO.getUserName() + "_技術者経歴書.pdf";
                    try{
                        ZipEntry entry = new ZipEntry(fileName);

                        zos.putNextEntry(entry);
                        zos.write(buffer);
                        zos.closeEntry();
                    } catch(IOException ioe) {
                        ioe.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
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
