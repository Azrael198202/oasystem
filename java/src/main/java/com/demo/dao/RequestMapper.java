package com.demo.dao;

import com.demo.entity.Request;
import com.demo.pojo.request.InsertByRequestDTO;
import com.demo.pojo.request.RequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RequestMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);

    int insert(Request record);

    int insertSelective(Request record);

    Request selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);

    int updateByPrimaryKeySelective(Request record);

    int updateByPrimaryKey(Request record);

    List<RequestDTO> selectAll(@Param("requestUserId") String requestUserId, @Param("companyCode") String companyCode);

    List<RequestDTO> search(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("requestStatusKbn") String requestStatusKbn, @Param("requestTypeKbn") String requestTypeKbn);

    InsertByRequestDTO selectByResNum(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);

    //int updateByPrimaryKeySk(@Param("requestStatusKbn") String requestStatusKbn, @Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);
    int updateByPrimaryKeySk(Request record);

    int updateByPrimaryKeyRt(Request record);

    //int updateByPrimaryKeyLa(@Param("requestStatusKbn") String requestStatusKbn, @Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);
    int updateByPrimaryKeyLa(Request record);
    List<RequestDTO> selectAll(@Param("companyCode") String companyCode);
}