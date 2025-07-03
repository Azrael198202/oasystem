package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.monthCloseHistory.MonthCloseHistoryDTO;
import com.demo.pojo.monthCloseHistory.PaidHolidayHistoryMonthDTO;
import com.demo.pojo.requestHoliday.RequestHolidayDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IMonthCloseHistoryService {
    ServerResponse<String> selectTgtMonth(MonthCloseHistoryDTO monthCloseHistoryDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<List<PaidHolidayHistoryMonthDTO>> paidHolidayHistoryMonth(MonthCloseHistoryDTO monthCloseHistoryDTO, HttpServletRequest request, HttpServletResponse response);
}
