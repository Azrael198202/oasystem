package com.demo.dao;

import com.demo.entity.Department;
import com.demo.pojo.department.DepartmentDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("departmentCode") String departmentCode);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("departmentCode") String departmentCode);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<DepartmentDTO>getDepartment(@Param("companyCode") String companyCode);
}