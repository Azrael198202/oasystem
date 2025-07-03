package com.demo.controller.exchangedWorkDay;

import com.demo.common.ServerResponse;
import com.demo.pojo.exchangedWorkDay.ExchangedWorkDayDTO;
import com.demo.service.IExchangedWorkDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/exchangedWorkDay/")
public class ExchangedWorkDayController {

    @Autowired
    IExchangedWorkDayService iExchangedWorkDayService;

    @RequestMapping(value = "/insertExchangedWorkDay.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertExchangedWorkDay(@RequestBody ExchangedWorkDayDTO exchangedWorkDayDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        return iExchangedWorkDayService.insertExchangedWorkDay(exchangedWorkDayDTO, request, response);
    }
    @RequestMapping(value = "/selectExchangedWorkDay.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<ExchangedWorkDayDTO>> selectExchangedWorkDay(@RequestBody ExchangedWorkDayDTO exchangedWorkDayDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        return iExchangedWorkDayService.selectExchangedWorkDay(exchangedWorkDayDTO, request, response);
    }
    @RequestMapping(value = "/deleteExchangedWorkDay.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteExchangedWorkDay(@RequestBody ExchangedWorkDayDTO exchangedWorkDayDTO,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception
    {
        return iExchangedWorkDayService.deleteExchangedWorkDay(exchangedWorkDayDTO, request, response);
    }
    @RequestMapping(value = "/exchangedWorkDayConfirm.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> exchangedWorkDayConfirm(@RequestBody ExchangedWorkDayDTO exchangedWorkDayDTO,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws Exception
    {
        return iExchangedWorkDayService.countExchangedWorkDay(exchangedWorkDayDTO, request, response);
    }
}
