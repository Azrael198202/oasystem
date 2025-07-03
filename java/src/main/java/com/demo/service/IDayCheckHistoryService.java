package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IDayCheckHistoryService {
    ServerResponse<List<DayCheckHistoryDTO>> getDayCheckHistory(DayCheckHistoryDTO dayCheckHistoryDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<List<DayCheckHistoryDTO>> searchDayCheckHistory(DayCheckHistoryDTO dayCheckHistoryDTO, HttpServletRequest request, HttpServletResponse response);
}
