package com.demo.dao;

import com.demo.entity.MenuStyle;
import org.apache.ibatis.annotations.Param;

public interface MenuStyleMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("menuNumber") String menuNumber);

    int insert(MenuStyle record);

    int insertSelective(MenuStyle record);

    MenuStyle selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("menuNumber") String menuNumber);

    int updateByPrimaryKeySelective(MenuStyle record);

    int updateByPrimaryKey(MenuStyle record);
}