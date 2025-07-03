package com.demo.controller.requestAttendance;

import com.demo.common.ServerResponse;
import com.demo.entity.RequestAttendance;
import com.demo.pojo.common.LoginUserInfo;
import com.demo.pojo.requestAttendance.*;
import com.demo.pojo.user.UserDetailInfo;
import com.demo.pojo.wf.WfDTO;
import com.demo.service.IRequestAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/requestAttendance/")
public class RequestAttendanceController {
    @Autowired
    IRequestAttendanceService iRequestAttendanceService;

    @RequestMapping(value = "getRequestAttendance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<RequestAttendanceDTO>> userRegister(@RequestBody RequestAttendanceDTO requestAttendanceDTO, HttpServletRequest request,
                                                         HttpServletResponse response) {

        return iRequestAttendanceService.getRequestAttendanceList(requestAttendanceDTO,request,response);
    }

    @RequestMapping(value = "getRequestAttendanceBy.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<RequestAttendance> userRegister(@RequestBody RequestAttendanceByDTO requestAttendanceByDTO) {

        return iRequestAttendanceService.getRequestAttendanceByList(requestAttendanceByDTO);
    }

    @RequestMapping(value = "insertRequestAttendance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<RequestAttendanceReqDTO> insertRegister(@RequestBody InsertRequestAttendanceDTO insertRequestAttendanceDTO) {

        return iRequestAttendanceService.insertRequestAttendanceList(insertRequestAttendanceDTO);
    }

    @RequestMapping(value = "updateRequestAttendance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(@RequestBody UpdateRequestAttendanceDTO updateRequestAttendanceDTO) {

        // TODO
        return iRequestAttendanceService.updateRequestAttendanceList(updateRequestAttendanceDTO);
    }

    @RequestMapping(value = "updateNumRequestAttendance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(@RequestBody UpdateNumRequestAttendanceDTO updateNumRequestAttendanceDTO) {

        // TODO
        return iRequestAttendanceService.updateNumRequestAttendanceList(updateNumRequestAttendanceDTO);
    }
    @RequestMapping(value = "/deleteRequestAttendance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delete(@RequestBody RequestAttendanceDTO requestAttendanceDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iRequestAttendanceService.deleteRequestAttendanceList(requestAttendanceDTO,request, response);
    }

    @RequestMapping(value = "getStatusAttendance.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<GetStatusAttendanceDTO> getStatusAttendance(@RequestBody GetStatusAttendanceDTO getStatusAttendanceDTO) {

        return iRequestAttendanceService.getStatusAttendance(getStatusAttendanceDTO);
    }
}
