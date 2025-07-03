package com.demo.dao;

import com.demo.entity.RequestAttendance;
import com.demo.pojo.requestAttendance.GetStatusAttendanceDTO;
import com.demo.pojo.requestAttendance.RequestAttendanceDTO;
import com.demo.pojo.requestAttendance.RequestAttendanceReqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RequestAttendanceMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDateYm") String workDateYm);

    int insert(RequestAttendance record);

    int insertSelective(RequestAttendance record);

    RequestAttendance selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDateYm") String workDateYm);

    int updateByPrimaryKeySelective(RequestAttendance record);

    int updateByPrimaryKey(RequestAttendance record);

    int updateByPrimaryKeyReq(RequestAttendance record);

    int updateByPrimaryKeyReqNum(RequestAttendance record);

    RequestAttendanceReqDTO selectreq(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDateYm") String workDateYm);

    List<RequestAttendanceDTO> selectAll(@Param("companyCode") String companyCode);

    GetStatusAttendanceDTO getStatus(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("workDateYm") String workDateYm);

}