package com.demo.controller.department;

import com.demo.common.ServerResponse;

import com.demo.pojo.department.DepartmentDTO;
import com.demo.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/department/")
public class DepartmentController {

    @Autowired
    IDepartmentService iDepartmentService;

    @RequestMapping(value = "/getDepartment.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<DepartmentDTO>> getDepartment(@RequestBody DepartmentDTO departmentDTO,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response) throws Exception
    {
        return iDepartmentService.getDepartment(departmentDTO, request, response);
    }

}
