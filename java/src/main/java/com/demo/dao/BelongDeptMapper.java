package com.demo.dao;

import com.demo.entity.BelongDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BelongDeptMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("departmentCode") String departmentCode);

    int insert(BelongDept record);

    int insertSelective(BelongDept record);

    BelongDept selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("departmentCode") String departmentCode);

    int updateByPrimaryKeySelective(BelongDept record);

    int updateByPrimaryKey(BelongDept record);
}