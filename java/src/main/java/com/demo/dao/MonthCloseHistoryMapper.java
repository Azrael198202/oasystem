package com.demo.dao;

import com.demo.entity.MonthCloseHistory;
import com.demo.pojo.monthCloseHistory.PaidHolidayHistoryMonthDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MonthCloseHistoryMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("targetMonth") String targetMonth);

    int insert(MonthCloseHistory record);

    int insertSelective(MonthCloseHistory record);

    MonthCloseHistory selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("targetMonth") String targetMonth);

    int updateByPrimaryKeySelective(MonthCloseHistory record);

    int updateByPrimaryKey(MonthCloseHistory record);

    String selectTgtMonth(@Param("userId") String userId, @Param("processResult") String processResult);

    List<PaidHolidayHistoryMonthDTO> paidHolidayHistoryMonth(@Param("userId") String userId, @Param("resultKbn") String processResult);
}