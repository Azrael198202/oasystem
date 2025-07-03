package com.demo.dao;

import com.demo.entity.OverTime;
import com.demo.pojo.overTime.OverTimeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OverTimeMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    int insert(OverTime record);

    int insertSelective(OverTime record);

    OverTime selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    int updateByPrimaryKeySelective(OverTime record);

    int updateByPrimaryKey(OverTime record);

    List<OverTimeDTO> selectOvertime(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);

    int countOvertimeDay(@Param("companyCode") String companyCode, @Param("scheduledOvertimeDay") String scheduledOvertimeDay, @Param("requestUserId") String requestUserId);
}