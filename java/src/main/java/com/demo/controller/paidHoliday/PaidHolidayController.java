package com.demo.controller.paidHoliday;

import com.demo.common.ServerResponse;
import com.demo.pojo.paidHoliday.PaidHolidayDTO;
import com.demo.pojo.paidHoliday.PaidHolidayMaintenanceDTO;
import com.demo.service.IPaidHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/paidHoliday")
public class PaidHolidayController {
    @Autowired
    IPaidHolidayService iPaidHolidayService;
    @RequestMapping(value = "/getRequestPaidHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> getRequestPaidHoliday(@RequestBody PaidHolidayDTO paidHolidayDTO,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception
    {
        return iPaidHolidayService.getRequestPaidHoliday(paidHolidayDTO, request, response);
    }

    @RequestMapping(value = "/EndMonthPaidHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> EndMonthPaidHoliday(@RequestBody PaidHolidayDTO paidHolidayDTO,
                                                                      HttpServletRequest request,
                                                                      HttpServletResponse response) throws Exception
    {
        return iPaidHolidayService.EndMonthPaidHoliday(paidHolidayDTO, request, response);
    }

    @RequestMapping(value = "/EndMonthAfterNextPaidHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> EndMonthAfterNextPaidHoliday(@RequestBody PaidHolidayDTO paidHolidayDTO,
                                                                      HttpServletRequest request,
                                                                      HttpServletResponse response) throws Exception
    {
        return iPaidHolidayService.EndMonthAfterNextPaidHoliday(paidHolidayDTO, request, response);
    }
    @RequestMapping(value = "/getPaidHolidayMaintenance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<PaidHolidayMaintenanceDTO>> getPaidHolidayMaintenance(@RequestBody PaidHolidayMaintenanceDTO paidHolidayMaintenanceDTO, HttpServletRequest request,
                                                                                     HttpServletResponse response) {
        return iPaidHolidayService.getPaidHolidayMaintenance(paidHolidayMaintenanceDTO, request, response);
    }

    @RequestMapping(value = "/getPaidHolidayRemaining.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> getPaidHolidayRemaining(@RequestBody PaidHolidayDTO paidHolidayDTO,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response)throws Exception
    {
        return iPaidHolidayService.getPaidHolidayRemaining(paidHolidayDTO, request, response);
    }
}
