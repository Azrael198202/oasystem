package com.demo.dao;

import com.demo.entity.Attendance;
import com.demo.pojo.attendance.*;
import com.demo.pojo.attendance.ApplyAttendanceDTO;
import com.demo.pojo.attendance.AttendanceExportDTO;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.attendance.AttendanceTimeDTO;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDate") String workDate);

    int insert(Attendance record);

    int insertSelective(Attendance record);

    Attendance selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDate") String workDate);

    int updateByPrimaryKeySelective(Attendance record);

    int updateByPrimaryKey(Attendance record);

    List<AttendanceRPnDTO> selectById(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDateYm") String workDateYm);
    List<ApplyAttendanceDTO> selectByApply(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDateYm") String workDateYm);
    List<ApplyAttendanceDTO> selectByApplyActivity(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDateYm") String workDateYm);
    AttendanceTimeDTO selectTime(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDate") String workDate);

    //  月末締明細情報検索処理(検索ボタン押下時の処理）
    List<BulkExportDTO> searchBulkExport(@Param("companyCode") String companyCode,
                                         @Param("departmentCode") String departmentCode,
                                         @Param("workDateYm") String workDateYm,
                                         @Param("requestStatusKbn") String categoryKbnName,
                                         @Param("transportationCode") String transportationCode,
                                         @Param("userId") String userId,
                                         @Param("userName") String userName);

    String selectSubHoliday(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDate") String workDate);
}