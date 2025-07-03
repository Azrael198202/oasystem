package com.demo.controller.paidHolodayCorrect;

import com.demo.common.ServerResponse;
import com.demo.entity.PaidHolidayCorrect;
import com.demo.pojo.paidHolidayCorrect.DefaultPaidHolidayCorrectDTO;
import com.demo.service.IPaidHolidayCorrectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RestController
@RequestMapping("/paidHolidayCorrect")
public class PaidHolidayCorrectController {
    @Autowired
    IPaidHolidayCorrectService iPaidHolidayCorrectService;
    @RequestMapping(value = "paidHolidayCorrect.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> PaidHolidayCorrect(@RequestBody DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO , HttpServletRequest request,
                                                           HttpServletResponse response) {
        return iPaidHolidayCorrectService.PaidHolidayCorrect(defaultPaidHolidayCorrectDTO,request,response);
    }
    @RequestMapping(value ="getPaidHolidayCorrect.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<DefaultPaidHolidayCorrectDTO>>getPaidHolidayCorrect(@RequestBody DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO , HttpServletRequest request,
    HttpServletResponse response) {
        return iPaidHolidayCorrectService.getPaidHolidayCorrect(defaultPaidHolidayCorrectDTO,request,response);
    }
}
