package com.demo.dao;

import com.demo.entity.PaidHolidayContentsUpdate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PaidHolidayContentsUpdateMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("changeDate") String changeDate, @Param("subNumber") String subNumber, @Param("dateYm") String dateYm, @Param("assignmentYear") String assignmentYear);

    int insert(PaidHolidayContentsUpdate record);

    int insertSelective(PaidHolidayContentsUpdate record);

    PaidHolidayContentsUpdate selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("changeDate") String changeDate, @Param("subNumber") String subNumber, @Param("dateYm") String dateYm, @Param("assignmentYear") String assignmentYear);

    int updateByPrimaryKeySelective(PaidHolidayContentsUpdate record);

    int updateByPrimaryKey(PaidHolidayContentsUpdate record);

    List<PaidHolidayContentsUpdate>getPaidHolidayContentsUpdate(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("changeDate") String changeDate, @Param("subNumber") String subNumber);

}