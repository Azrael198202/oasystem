package com.demo.dao;

import com.demo.entity.AttendanceHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AttendanceHistoryMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("historyNo") String historyNo, @Param("workDate") String workDate);

    int insert(AttendanceHistory record);

    int insertSelective(AttendanceHistory record);

    AttendanceHistory selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("historyNo") String historyNo, @Param("workDate") String workDate);

    int updateByPrimaryKeySelective(AttendanceHistory record);

    int updateByPrimaryKey(AttendanceHistory record);

    String selectNo(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDate") String workDate);


}