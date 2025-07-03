package com.demo.dao;

import com.demo.entity.DayCheckHistory;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DayCheckHistoryMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("checkDay") String checkDay, @Param("branchNumber") Integer branchNumber);

    int insert(DayCheckHistory record);

    int insertSelective(DayCheckHistory record);

    DayCheckHistory selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("checkDay") String checkDay, @Param("branchNumber") Integer branchNumber);

    int updateByPrimaryKeySelective(DayCheckHistory record);

    int updateByPrimaryKey(DayCheckHistory record);

    List<DayCheckHistoryDTO> getDayCheckHistory(@Param("companyCode") String companyCode);

    List<DayCheckHistoryDTO> searchDayCheckHistory(@Param("companyCode") String companyCode, @Param("userName") String userName, @Param("checkDay") String checkDay);
}