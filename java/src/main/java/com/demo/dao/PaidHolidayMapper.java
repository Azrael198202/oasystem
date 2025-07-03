package com.demo.dao;

import com.demo.entity.PaidHoliday;
import com.demo.pojo.paidHoliday.PaidHolidayMaintenanceDTO;
import com.demo.pojo.paidHoliday.PaidHolidayDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaidHolidayMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("dateYm") String dateYm, @Param("assignmentYear") String assignmentYear);

    int insert(PaidHoliday record);

    int insertSelective(PaidHoliday record);

    PaidHoliday selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("dateYm") String dateYm, @Param("assignmentYear") String assignmentYear);

    int updateByPrimaryKeySelective(PaidHoliday record);

    int updateByPrimaryKey(PaidHoliday record);

    float getRequestPaidHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId);

    float EndMonthPaidHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId);

    float EndMonthAfterNextPaidHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId);
    List<PaidHolidayMaintenanceDTO> getPaidHolidayMaintenance(@Param("companyCode")String companyCode,@Param("dateY")String dateY);
    int updatePaidHolidayRemaining (PaidHoliday record);
    PaidHoliday getPaidHoliday(@Param("companyCode")String companyCode,@Param("userId") String userId,@Param("dateYm") String dateYm,@Param("assignmentYear") String assignmentYear);
    float getPaidHolidayRemaining(@Param("companyCode")String companyCode,@Param("userId") String userId,@Param("dateYm") String dateYm,@Param("assignmentYear") String assignmentYear);
   // List <PaidHolidayDTO> getPaidHolidayAssignmentYear(@Param("companyCode") String companyCode, @Param("userId") String userId);
   List <PaidHoliday> getPaidHolidayAssignmentYear(@Param("companyCode") String companyCode, @Param("userId") String userId);

}