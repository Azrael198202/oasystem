package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.entity.Holiday;
import com.demo.pojo.attendance.AttendanceDTO;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.category.CategoryDTO;
import com.demo.pojo.category.CategoryRpnDTO;
import com.demo.pojo.holiday.HolidayDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ICategoryService {
    /**
     * 勤務表詳細情報取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<CategoryRpnDTO>> getCategory(CategoryDTO categoryDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<List<Holiday>> getHoliday(HolidayDTO holidayDTO, HttpServletRequest request, HttpServletResponse response);
}
