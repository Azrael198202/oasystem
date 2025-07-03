package com.demo.dao;

import com.demo.entity.RequestAllowance;
import com.demo.pojo.requestAllowance.GetApplyYmDTO;
import com.demo.pojo.requestAllowance.RequestAllowanceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RequestAllowanceMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("applyYm") String applyYm, @Param("requestNumber") String requestNumber);

    int insert(RequestAllowance record);

    int insertSelective(RequestAllowance record);

    RequestAllowance selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("applyYm") String applyYm, @Param("requestNumber") String requestNumber);

    int updateByPrimaryKeySelective(RequestAllowance record);

    int updateByPrimaryKey(RequestAllowance record);

    List<RequestAllowanceDTO> selectRequestAllowance(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);
    List<RequestAllowanceDTO> searchReAllowanceMonthData(@Param("companyCode") String companyCode, @Param("applyYm") String applyYm, @Param("requestUserId") String requestUserId, @Param("requestTypeKbn") String requestTypeKbn);
    List<GetApplyYmDTO> searchMonth(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("requestTypeKbn") String requestTypeKbn);
    int QualificationCheck (@Param("companyCode") String companyCode, @Param("qualificationId") String qualificationId, @Param("requestUserId") String requestUserId, @Param("requestNumber") String requestNumber);
}