package com.demo.dao;

import com.demo.entity.Menu;
import com.demo.pojo.common.MenuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MenuMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("menuNumber") String menuNumber, @Param("permissionKbn") String permissionKbn);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("menuNumber") String menuNumber, @Param("permissionKbn") String permissionKbn);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuInfo> selectByKbn(@Param("companyCode") String companyCode, @Param("permissionKbn") String permissionKbn);
}