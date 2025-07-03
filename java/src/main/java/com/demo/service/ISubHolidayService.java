package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.category.CategoryRpnDTO;
import com.demo.pojo.requestHoliday.RequestHolidayDTO;
import com.demo.pojo.subHoliday.SubHolidayDTO;
import com.demo.pojo.subHoliday.SubHolidayInputDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ISubHolidayService {
    ServerResponse<Float> getRequestSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<Float> endOfThisMonthSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<Float> theEndOfTheFollowingMonthSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<Float> checkNum(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<List<SubHolidayDTO>> getScheduledWorkDays(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    //    代休残入力画面：検索押下時の処理
    ServerResponse<List<SubHolidayInputDTO>> usersearch(SubHolidayInputDTO subHolidayInputDTO, HttpServletRequest request, HttpServletResponse response);
    //    代休残入力画面：重複データの確認
    ServerResponse<String> checkSubHoliday(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response);
    //    代休残入力画面：作成押下時の処理(代休残にデータを登録)
    ServerResponse<String> subHolidayCreate(SubHolidayDTO subHolidayDTO, HttpServletRequest request, HttpServletResponse response);
}
