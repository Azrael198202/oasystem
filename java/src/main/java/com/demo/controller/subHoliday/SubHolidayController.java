package com.demo.controller.subHoliday;

import com.demo.common.ServerResponse;
import com.demo.pojo.category.CategoryRpnDTO;
import com.demo.pojo.requestHoliday.RequestHolidayDTO;
import com.demo.pojo.subHoliday.SubHolidayDTO;
import com.demo.pojo.subHoliday.SubHolidayInputDTO;
import com.demo.service.ISubHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/subHoliday")
public class SubHolidayController {
    @Autowired
    ISubHolidayService iSubHolidayService;
    @RequestMapping(value = "/getRequestSubHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> getRequestSubHoliday(@RequestBody SubHolidayDTO subHolidayDTO,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception
    {
        return iSubHolidayService.getRequestSubHoliday(subHolidayDTO, request, response);
    }
    @RequestMapping(value = "/endOfThisMonthSubHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> endOfThisMonthSubHoliday(@RequestBody SubHolidayDTO subHolidayDTO,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) throws Exception
    {
        return iSubHolidayService.endOfThisMonthSubHoliday(subHolidayDTO, request, response);
    }
    @RequestMapping(value = "/theEndOfTheFollowingMonthSubHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> theEndOfTheFollowingMonthSubHoliday(@RequestBody SubHolidayDTO subHolidayDTO,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) throws Exception
    {
        return iSubHolidayService.theEndOfTheFollowingMonthSubHoliday(subHolidayDTO, request, response);
    }

    @RequestMapping(value = "/checkNum.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Float> checkNum(@RequestBody SubHolidayDTO subHolidayDTO,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception
    {
        return iSubHolidayService.checkNum(subHolidayDTO,request, response);
    }

    @RequestMapping(value = "/getScheduledWorkDays.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<SubHolidayDTO>> getScheduledWorkDays(@RequestBody SubHolidayDTO subHolidayDTO,
                                                                     HttpServletRequest request,
                                                                     HttpServletResponse response) throws Exception
    {
        return iSubHolidayService.getScheduledWorkDays(subHolidayDTO,request, response);
    }
    //    代休残入力画面：検索押下時の処理
    @RequestMapping(value = "/usersearch.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<SubHolidayInputDTO>> usersearch(@RequestBody SubHolidayInputDTO subHolidayInputDTO,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws Exception {
        return iSubHolidayService.usersearch(subHolidayInputDTO,request, response);
    }
    //    代休残入力画面：重複データの確認
    @RequestMapping(value = "/checkSubHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkSubHoliday(@RequestBody SubHolidayDTO subHolidayDTO,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        return iSubHolidayService.checkSubHoliday(subHolidayDTO,request, response);
    }
    //    代休残入力画面：作成押下時の処理(代休残にデータを登録)
    @RequestMapping(value = "/subHolidayCreate.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> subHolidayCreate(@RequestBody SubHolidayDTO subHolidayDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        return iSubHolidayService.subHolidayCreate(subHolidayDTO,request, response);
    }
}
