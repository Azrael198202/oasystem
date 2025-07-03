package com.demo.controller.dayCheckHistory;

import com.demo.common.ServerResponse;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;
import com.demo.service.IDayCheckHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/dayCheckHistory")
public class dayCheckHistoryController {
    @Autowired
    IDayCheckHistoryService iDayCheckHistoryService;
    @RequestMapping(value = "/getDayCheckHistory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<DayCheckHistoryDTO>> getDayCheckHistory(@RequestBody DayCheckHistoryDTO dayCheckHistoryDTO,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response) throws Exception
    {
        return iDayCheckHistoryService.getDayCheckHistory(dayCheckHistoryDTO, request, response);
    }

    @RequestMapping(value = "/searchDayCheckHistory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<DayCheckHistoryDTO>> searchDayCheckHistory(@RequestBody DayCheckHistoryDTO dayCheckHistoryDTO,
                                                                              HttpServletRequest request,
                                                                              HttpServletResponse response) throws Exception
    {
        return iDayCheckHistoryService.searchDayCheckHistory(dayCheckHistoryDTO, request, response);
    }
}
