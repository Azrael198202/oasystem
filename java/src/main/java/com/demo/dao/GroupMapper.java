package com.demo.dao;

import com.demo.entity.Group;
import com.demo.pojo.group.GroupDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("groupCode") String groupCode, @Param("groupName") String groupName);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("groupCode") String groupCode, @Param("groupName") String groupName);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    /*---------updateByPrimaryKey 截至为自动生成------------------*/
    List<GroupDTO> selectAll(@Param("companyCode") String companyCode, @Param("groupCode") String groupCode, @Param("groupName") String groupName);

    int deleteByCode(@Param("companyCode") String companyCode, @Param("groupCode") String groupCode);
    String selectCode(@Param("companyCode") String companyCode);
    int selectName(@Param("companyCode") String companyCode, @Param("groupName") String groupName);
    List<GroupDTO> selectGroup(@Param("companyCode") String companyCode);
}