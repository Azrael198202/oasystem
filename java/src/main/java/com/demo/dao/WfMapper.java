package com.demo.dao;

import com.demo.entity.Wf;
import com.demo.pojo.wf.RemandWfDTO;
import com.demo.pojo.wf.WfCount;
import com.demo.pojo.wf.WfDTO;
import com.demo.pojo.wf.SelectWfDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WfMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("reqAppOrder") Byte reqAppOrder);

    int insert(Wf record);

    int insertSelective(Wf record);

    Wf selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("reqAppOrder") Byte reqAppOrder);

    int updateByPrimaryKeySelective(Wf record);

    int updateByPrimaryKey(Wf record);

    int updateByPrimaryKeyWf(Wf record);

    int updateByPrimaryKeyadmitWf(Wf record);

    int updateByPrimaryKeylastadmitWf(Wf record);

    List<WfDTO> selectAll(@Param("companyCode") String companyCode);

    List<WfDTO> getCount(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);

    List<SelectWfDTO> selectWf(@Param("companyCode") String companyCode,
                               @Param("reqAppUserId") String reqAppUserId,
                               @Param("requestTypeKbn") String requestTypeKbn,
                               @Param("wfStatusKbn") String wfStatusKbn,
                               @Param("startDate") String startDate,
                               @Param("endDate") String endDate,
                               @Param("userId") String userId);

    int deleteAllWf(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);

    int deleteDataWf(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("reqAppOrder") Byte reqAppOrder);
}