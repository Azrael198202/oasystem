package com.demo.dao;

import com.demo.entity.RequestHoliday;
import com.demo.pojo.requestHoliday.PaidHolidayHistoryDTO;
import com.demo.pojo.requestHoliday.RequestCompleteHolidayDTO;
import com.demo.pojo.requestHoliday.RequestHolidayDTO;
import com.demo.pojo.requestHoliday.checkNumDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RequestHolidayMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestSubNumber") Byte requestSubNumber, @Param("requestUserId") String requestUserId);

    int insert(RequestHoliday record);

    int insertSelective(RequestHoliday record);

    RequestHoliday selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestSubNumber") Byte requestSubNumber, @Param("requestUserId") String requestUserId);

    int updateByPrimaryKeySelective(RequestHoliday record);

    int updateByPrimaryKey(RequestHoliday record);

    List<RequestHolidayDTO> selectAll(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("requestNumber") String requestNumber);

    int deleteRequestHoliday(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    int countLeaveDay(@Param("companyCode") String companyCode,@Param("holidayPlanDay") String holidayPlanDay, @Param("requestUserId") String requestUserId, @Param("requestNumber") String requestNumber);

    List<checkNumDTO> checkNum(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("holidayPlanDay") String holidayPlanDay);

    List<PaidHolidayHistoryDTO> paidHolidayHistory(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId);
    List<PaidHolidayHistoryDTO> paidHolidayNextMonth(@Param("companyCode") String companyCode,@Param("requestUserId") String requestUserId);
    List<PaidHolidayHistoryDTO> subHolidayHistory(@Param("companyCode") String companyCode,@Param("requestUserId") String requestUserId,@Param("setDate") String setDate);
    List<PaidHolidayHistoryDTO> subHolidayNextMonth(@Param("companyCode") String companyCode,@Param("requestUserId") String requestUserId,@Param("setDate") String setDate);
    int countSubHoliday(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("workDayoffRequestNumber") String workDayoffRequestNumber);

    List<RequestCompleteHolidayDTO> getRequestCompleteHoliday(@Param("holidayPlanDay")String holidayPlanDay,@Param("companyCode")String companyCode);

}