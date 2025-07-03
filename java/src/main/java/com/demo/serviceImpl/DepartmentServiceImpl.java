package com.demo.serviceImpl;

import com.demo.common.ServerResponse;
import com.demo.dao.DepartmentMapper;
import com.demo.pojo.department.DepartmentDTO;
import com.demo.service.IDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Slf4j
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public ServerResponse<List<DepartmentDTO>> getDepartment(DepartmentDTO departmentDTO, HttpServletRequest request, HttpServletResponse response){
        List<DepartmentDTO> reqDepartmentList = departmentMapper.getDepartment(departmentDTO.companyCode);
        return ServerResponse.createBySuccess(reqDepartmentList);
    }

}
