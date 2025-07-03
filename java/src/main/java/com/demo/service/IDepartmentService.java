package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.department.DepartmentDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public interface IDepartmentService {
    ServerResponse<List<DepartmentDTO>> getDepartment(DepartmentDTO departmentDTO, HttpServletRequest request, HttpServletResponse response);
}
