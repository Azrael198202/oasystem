package com.demo.controller.monthCloseHistory;

import com.demo.common.ServerResponse;
import com.demo.pojo.monthCloseHistory.MonthCloseHistoryDTO;
import com.demo.pojo.monthCloseHistory.PaidHolidayHistoryMonthDTO;
import com.demo.service.IMonthCloseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/monthCloseHistory/")
public class monthCloseHistoryController {

    @Autowired
    IMonthCloseHistoryService iMonthCloseHistoryService;

    @RequestMapping(value = "selectTgtMonth.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> selectTgtMonth(@RequestBody MonthCloseHistoryDTO monthCloseHistoryDTO, HttpServletRequest request,
                                                 HttpServletResponse response) {

        return iMonthCloseHistoryService.selectTgtMonth(monthCloseHistoryDTO,request ,response);
    }
    @RequestMapping(value = "paidHolidayHistoryMonth.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<PaidHolidayHistoryMonthDTO>> paidHolidayHistoryMonth(@RequestBody MonthCloseHistoryDTO monthCloseHistoryDTO, HttpServletRequest request,
                                                                                    HttpServletResponse response) {

        return iMonthCloseHistoryService.paidHolidayHistoryMonth(monthCloseHistoryDTO,request ,response);
    }
}
