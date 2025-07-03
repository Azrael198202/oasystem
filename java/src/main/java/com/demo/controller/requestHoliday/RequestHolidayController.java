package com.demo.controller.requestHoliday;

import com.demo.common.ServerResponse;
import com.demo.entity.RequestAttendance;
import com.demo.pojo.request.RequestDTO;
import com.demo.pojo.requestAttendance.RequestAttendanceByDTO;
import com.demo.pojo.requestHoliday.*;
import com.demo.service.IRequestHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/requestHoliday/")
public class RequestHolidayController {
    @Autowired
    IRequestHolidayService iRequestHolidayService;

    @RequestMapping(value = "getRequestHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<RequestHolidayDTO>> userRegister(@RequestBody RequestHolidayDTO requestHolidayDTO, HttpServletRequest request,
                                                         HttpServletResponse response) {

        return iRequestHolidayService.getRequestHolidayList(requestHolidayDTO,request ,response);
    }

    @RequestMapping(value = "insertRequestHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertRequestHoliday(@RequestBody InsertRequestHolidayDTO insertRequestHolidayDTO, HttpServletRequest request,
                                                       HttpServletResponse response) {

        return iRequestHolidayService.insertRequestHolidayList(insertRequestHolidayDTO,request ,response);
    }

    @RequestMapping(value = "updateRequestHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateRequestHoliday(@RequestBody List<UpdateRequestHolidayDTO> updateRequestHolidayDTO, HttpServletRequest request,
                                                       HttpServletResponse response) {

        return iRequestHolidayService.updateRequestHolidayList(updateRequestHolidayDTO,request ,response);
    }

    @RequestMapping(value = "/deleteRequestHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delete(@RequestBody RequestHolidayDTO requestHolidayDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iRequestHolidayService.deleteRequestHolidayList(requestHolidayDTO,request, response);
    }
    @RequestMapping(value = "/leaveDayConfirm.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> leaveDayConfirm(@RequestBody List<RequestHolidayDTO> requestHolidayDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iRequestHolidayService.countLeaveDay(requestHolidayDTO,request, response);
    }
    @RequestMapping(value = "/checkNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkNum(@RequestBody List<RequestHolidayDTO> requestHolidayDTO, HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {

        return iRequestHolidayService.checkNum(requestHolidayDTO,request, response);
    }
    @RequestMapping(value = "/paidHolidayHistory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<PaidHolidayHistoryDTO>> paidHolidayHistory(@RequestBody PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request,
                                                                          HttpServletResponse response) {

        return iRequestHolidayService.paidHolidayHistory(paidHolidayHistoryDTO,request, response);
    }
    @RequestMapping(value = "/paidHolidayNextMonth.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<PaidHolidayHistoryDTO>> paidHolidayNextMonth(@RequestBody PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request,
                                                                          HttpServletResponse response) {

        return iRequestHolidayService.paidHolidayNextMonth(paidHolidayHistoryDTO,request, response);
    }
    @RequestMapping(value = "/subHolidayHistory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<PaidHolidayHistoryDTO>> subHolidayHistory(@RequestBody PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request,
                                                                            HttpServletResponse response) {

        return iRequestHolidayService.subHolidayHistory(paidHolidayHistoryDTO,request, response);
    }
    @RequestMapping(value = "/subHolidayNextMonth.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<PaidHolidayHistoryDTO>> subHolidayNextMonth(@RequestBody PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request,
                                                                         HttpServletResponse response) {

        return iRequestHolidayService.subHolidayNextMonth(paidHolidayHistoryDTO,request, response);
    }

    @RequestMapping(value = "RequestCompleteHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<RequestCompleteHolidayDTO>>RequestCompleteHolidayDTO(@RequestBody RequestCompleteHolidayDTO requestCompleteHolidayDTO, HttpServletRequest request,
                                                                                    HttpServletResponse response) {
        return iRequestHolidayService.getRequestCompleteHoliday(requestCompleteHolidayDTO, request, response);
    }
    @RequestMapping(value = "/countSubHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> countSubHoliday(@RequestBody RequestHolidayDTO requestHolidayDTO, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        return iRequestHolidayService.countSubHoliday(requestHolidayDTO,request, response);
    }
}

