package com.demo.dao;

import com.demo.entity.GroupMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMemberMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("groupCode") String groupCode, @Param("groupUserId") String groupUserId);

    int insert(GroupMember record);

    int insertSelective(GroupMember record);

    GroupMember selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("groupCode") String groupCode, @Param("groupUserId") String groupUserId);

    int updateByPrimaryKeySelective(GroupMember record);

    int updateByPrimaryKey(GroupMember record);

    /*---------updateByPrimaryKey 截至为自动生成------------------*/
    int deleteByCode(@Param("companyCode") String companyCode, @Param("groupCode") String groupCode);
}