package com.demo.dao;

import com.demo.entity.WorkDayOff;
import com.demo.pojo.workDayOff.GetWorkDayOffInfoDTO;
import com.demo.pojo.workDayOff.WorkDayOffDTO;
import com.demo.pojo.workDayOff.WorkDayOffResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkDayOffMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    int insert(WorkDayOff record);

    int insertSelective(WorkDayOff record);

    WorkDayOff selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    int updateByPrimaryKeySelective(WorkDayOff record);

    int updateByPrimaryKey(WorkDayOff record);

    List<WorkDayOffDTO> selectWorkDayOff(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);
    //　　代休残入力波面：休日出勤テーブルの申請番号取得
    WorkDayOffResultDTO requestNumberSubHoliday(@Param("companyCode") String companyCode
                                               ,@Param("requestUserId") String requestUserId
                                               ,@Param("scheduledWorkDay") String scheduledWorkDay);
    //　　休日出勤データ取得
    List<GetWorkDayOffInfoDTO> getWorkDayOffInfo(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("scheduledWorkDay") String scheduledWorkDay);
}