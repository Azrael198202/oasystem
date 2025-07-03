package com.demo.dao;

import com.demo.entity.CategoryDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDetailMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("categoryClassNumber") String categoryClassNumber);

    int insert(CategoryDetail record);

    int insertSelective(CategoryDetail record);

    CategoryDetail selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("categoryClassNumber") String categoryClassNumber);

    int updateByPrimaryKeySelective(CategoryDetail record);

    int updateByPrimaryKeyWithBLOBs(CategoryDetail record);

    int updateByPrimaryKey(CategoryDetail record);
}