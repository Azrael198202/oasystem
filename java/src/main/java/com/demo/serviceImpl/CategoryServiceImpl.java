package com.demo.serviceImpl;

import com.demo.common.ServerResponse;
import com.demo.dao.AttendanceMapper;
import com.demo.dao.CategoryMapper;
import com.demo.dao.HolidayMapper;
import com.demo.entity.Attendance;
import com.demo.entity.Category;
import com.demo.entity.Holiday;
import com.demo.pojo.attendance.AttendanceDTO;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.category.CategoryDTO;
import com.demo.pojo.category.CategoryRpnDTO;
import com.demo.pojo.holiday.HolidayDTO;
import com.demo.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 * Author  : libin
   Time    : 2021/07/12
   Function: 勤務表処理
*/
@Service
@Slf4j

public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    HolidayMapper holidayMapper;

    @Override
    public ServerResponse<List<CategoryRpnDTO>> getCategory(CategoryDTO categoryDTO, HttpServletRequest request, HttpServletResponse response) {

        List<CategoryRpnDTO> category = categoryMapper.selectKbn(categoryDTO.getCompanyCode(),categoryDTO.getCategoryClassNumber());

        return ServerResponse.createBySuccess(category);
    }

    @Override
    public ServerResponse<List<Holiday>> getHoliday(HolidayDTO holidayDTO, HttpServletRequest request, HttpServletResponse response) {
        List<Holiday> holiday = holidayMapper.selectByYear(holidayDTO.getCompanyCode(),holidayDTO.getHolidayYear());
        return ServerResponse.createBySuccess(holiday);
    }
}
