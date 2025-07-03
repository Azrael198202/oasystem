package com.demo.dao;

import com.demo.entity.Holiday;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HolidayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Holiday record);

    int insertSelective(Holiday record);

    Holiday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Holiday record);

    int updateByPrimaryKey(Holiday record);

    int selectByDate(@Param("companyCode") String companyCode, @Param("holidayDate") String holidayDate);

    List<Holiday> selectByYear(@Param("companyCode") String companyCode, @Param("holidayYear") String holidayYear);
}