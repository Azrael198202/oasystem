package com.demo.controller.overTime;

import com.demo.common.ServerResponse;

import com.demo.pojo.overTime.OverTimeDTO;
import com.demo.service.IOverTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/overTime/")
public class OverTimeController {

    @Autowired
    IOverTimeService iOverTimeService;

    /**
     * /overTime/insertData.do 用户登录
     * @param overTimeDTO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertData.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertData(@RequestBody OverTimeDTO overTimeDTO,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        return iOverTimeService.insertData(overTimeDTO, request, response);
    }

    @RequestMapping(value = "/getRequestOvertime.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<OverTimeDTO>> getRequestOvertime(@RequestBody OverTimeDTO overTimeDTO,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) throws Exception
    {

        return iOverTimeService.getRequestOvertime(overTimeDTO, request, response);
    }

    @RequestMapping(value = "/deleteOvertime.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteOvertime(@RequestBody OverTimeDTO overTimeDTO,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) throws Exception
    {

        return iOverTimeService.deleteOvertime(overTimeDTO, request, response);
    }

    @RequestMapping(value = "/overtimeDayConfirm.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> overtimeDayConfirm(@RequestBody OverTimeDTO overTimeDTO,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception
    {
        return iOverTimeService.countOvertimeDay(overTimeDTO, request, response);
    }

}

