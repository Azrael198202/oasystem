package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.requestHoliday.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IRequestHolidayService {
    ServerResponse<List<RequestHolidayDTO>> getRequestHolidayList(RequestHolidayDTO requestHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> insertRequestHolidayList(InsertRequestHolidayDTO insertRequestHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> deleteRequestHolidayList(RequestHolidayDTO requestHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> updateRequestHolidayList(List<UpdateRequestHolidayDTO> updateRequestHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> countLeaveDay(List<RequestHolidayDTO> requestHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> checkNum(List<RequestHolidayDTO> requestHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<PaidHolidayHistoryDTO>> paidHolidayHistory(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<PaidHolidayHistoryDTO>> paidHolidayNextMonth(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<PaidHolidayHistoryDTO>> subHolidayHistory(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<PaidHolidayHistoryDTO>> subHolidayNextMonth(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<RequestCompleteHolidayDTO>> getRequestCompleteHoliday(RequestCompleteHolidayDTO requestCompleteHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> countSubHoliday(RequestHolidayDTO requestHolidayDTO, HttpServletRequest request, HttpServletResponse response);

}