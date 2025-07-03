package com.demo.dao;

import com.demo.entity.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {
    int deleteByPrimaryKey(String companyCode);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(String companyCode);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
}