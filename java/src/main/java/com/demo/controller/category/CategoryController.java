package com.demo.controller.category;

import com.demo.common.ServerResponse;
import com.demo.entity.Holiday;
import com.demo.pojo.attendance.AttendanceDTO;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.category.CategoryDTO;
import com.demo.pojo.category.CategoryRpnDTO;
import com.demo.pojo.holiday.HolidayDTO;
import com.demo.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 * Author  : libin
   Time    : 2021/07/15
   Function:
*/
@RestController
@RequestMapping("/category/")
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;

    /**
     * /attendance/getList.do 勤務区分情報取得
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<CategoryRpnDTO>> getCategory(@RequestBody CategoryDTO categoryDTO, HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {

        return iCategoryService.getCategory(categoryDTO, request, response);
    }

    /**
     * /attendance/getList.do 休日情報取得
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getHoliday.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Holiday>> getHoliday(@RequestBody HolidayDTO holidayDTO,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {

        return iCategoryService.getHoliday(holidayDTO, request, response);
    }
}

