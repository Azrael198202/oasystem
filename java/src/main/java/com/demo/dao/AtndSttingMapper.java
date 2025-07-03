package com.demo.dao;

import com.demo.entity.AtndStting;
import com.demo.pojo.atndStting.AtndSttingRpnDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AtndSttingMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("applicationDate") String applicationDate);

    int insert(AtndStting record);

    int insertSelective(AtndStting record);

    AtndStting selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("applicationDate") String applicationDate);

    int updateByPrimaryKeySelective(AtndStting record);

    int updateByPrimaryKey(AtndStting record);

    AtndSttingRpnDTO searchData(@Param("companyCode") String companyCode, @Param("userId") String userId);

    List<AtndSttingRpnDTO> searchByDate(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("applicationDate") String applicationDate);
}