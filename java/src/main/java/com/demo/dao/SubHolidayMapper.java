package com.demo.dao;

import com.demo.entity.SubHoliday;
import com.demo.pojo.subHoliday.GetSubHolidayCntDTO;
import com.demo.pojo.subHoliday.SubHolidayDTO;
import com.demo.pojo.subHoliday.SubHolidayInputDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubHolidayMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("holidayWorkDay") String holidayWorkDay);

    int insert(SubHoliday record);

    int insertSelective(SubHoliday record);

    SubHoliday selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("holidayWorkDay") String holidayWorkDay);

    int updateByPrimaryKeySelective(SubHoliday record);

    int updateByPrimaryKey(SubHoliday record);

    float getRequestSubHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("setDate") String setDate);
    float endOfThisMonthSubHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("setDate") String setDate);
    float theEndOfTheFollowingMonthSubHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("setDate") String setDate);

    float checkNum(@Param("companyCode") String companyCode, @Param("userId") String userId);

    List<SubHolidayDTO> getScheduledWorkDays(@Param("companyCode") String companyCode, @Param("userId") String userId);
    //    代休残入力画面：検索押下時の処理
    List<SubHolidayInputDTO> usersearch(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("userName") String userName);
    //    代休残入力画面：重複データの確認
    GetSubHolidayCntDTO checkSubHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("holidayWorkDay") String holidayWorkDay);
}