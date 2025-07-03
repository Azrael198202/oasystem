package com.demo.dao;

import com.demo.entity.AtndApply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AtndApplyMapper {
    int deleteByPrimaryKey(String atndApplyNo);

    int insert(AtndApply record);

    int insertSelective(AtndApply record);

    AtndApply selectByPrimaryKey(String atndApplyNo);

    int updateByPrimaryKeySelective(AtndApply record);

    int updateByPrimaryKey(AtndApply record);
}