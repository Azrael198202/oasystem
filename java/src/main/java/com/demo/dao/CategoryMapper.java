package com.demo.dao;

import com.demo.entity.Category;
import com.demo.pojo.category.CategoryRpnDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("categoryClassNumber") String categoryClassNumber, @Param("categoryKbn") String categoryKbn);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("categoryClassNumber") String categoryClassNumber, @Param("categoryKbn") String categoryKbn);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<CategoryRpnDTO> selectKbn(@Param("companyCode") String companyCode, @Param("categoryClassNumber") String categoryClassNumber);
}