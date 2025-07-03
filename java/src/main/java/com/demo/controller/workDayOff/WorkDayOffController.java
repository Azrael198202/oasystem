package com.demo.controller.workDayOff;

import com.demo.common.ServerResponse;
import com.demo.pojo.workDayOff.GetWorkDayOffInfoDTO;
import com.demo.pojo.workDayOff.WorkDayOffDTO;
import com.demo.pojo.workDayOff.WorkDayOffResultDTO;
import com.demo.pojo.workDayOff.WorkDayOffSearchDTO;
import com.demo.service.IWorkDayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/workDayOff/")
public class WorkDayOffController {

    @Autowired
    IWorkDayOffService iWorkDayOffService;

    @RequestMapping(value = "/insertWorkDayOff.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertWorkDayOff(@RequestBody WorkDayOffDTO workDayOffDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        return iWorkDayOffService.insertWorkDayOff(workDayOffDTO, request, response);
    }
    @RequestMapping(value = "/selectWorkDayOff.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<WorkDayOffDTO>> selectWorkDayOff(@RequestBody WorkDayOffDTO workDayOffDTO,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws Exception {
        return iWorkDayOffService.selectWorkDayOff(workDayOffDTO, request, response);
    }
    @RequestMapping(value = "/deleteWorkDayOff.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteWorkDayOff(@RequestBody WorkDayOffDTO workDayOffDTO,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception
    {

        return iWorkDayOffService.deleteWorkDayOff(workDayOffDTO, request, response);
    }
    //　　代休残入力画面：休日出勤テーブルの申請番号取得
    @RequestMapping(value = "/requestNumberSubHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<WorkDayOffResultDTO> requestNumberSubHoliday(@RequestBody WorkDayOffSearchDTO workDayOffSearchDTO,
                                                                       HttpServletRequest request,
                                                                       HttpServletResponse response) throws Exception {

        return iWorkDayOffService.selectRequestNumberWorkDayOff(workDayOffSearchDTO, request, response);
    }
    //　　休日出勤データ取得
    @RequestMapping(value = "/getWorkDayOffInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<GetWorkDayOffInfoDTO>> getWorkDayOffInfo(@RequestBody GetWorkDayOffInfoDTO getWorkDayOffInfoDTO,
                                                                        HttpServletRequest request,
                                                                        HttpServletResponse response) throws Exception {
        return iWorkDayOffService.getWorkDayOffInfo(getWorkDayOffInfoDTO, request, response);
    }
}
