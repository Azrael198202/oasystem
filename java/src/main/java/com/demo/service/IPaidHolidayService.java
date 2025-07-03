package com.demo.service;

import com.demo.common.ServerResponse;

import com.demo.pojo.paidHoliday.PaidHolidayDTO;
import com.demo.pojo.paidHoliday.PaidHolidayMaintenanceDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IPaidHolidayService {
    ServerResponse<Float> getRequestPaidHoliday(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<Float> EndMonthPaidHoliday(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<Float> EndMonthAfterNextPaidHoliday(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<PaidHolidayMaintenanceDTO>>getPaidHolidayMaintenance(PaidHolidayMaintenanceDTO paidHolidayMaintenanceDTO, HttpServletRequest request,
                                                                             HttpServletResponse response);
    ServerResponse<Float> getPaidHolidayRemaining(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response);
}
