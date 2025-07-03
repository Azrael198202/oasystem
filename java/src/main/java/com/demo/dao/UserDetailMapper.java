package com.demo.dao;

import com.demo.entity.UserDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId);

    int insert(UserDetail record);

    int insertSelective(UserDetail record);

    UserDetail selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId);

    int updateByPrimaryKeySelective(UserDetail record);

    int updateByPrimaryKey(UserDetail record);
}