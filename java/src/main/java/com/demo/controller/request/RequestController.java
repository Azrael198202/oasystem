package com.demo.controller.request;

import com.demo.common.ServerResponse;
import com.demo.pojo.request.*;
import com.demo.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/request/")
public class RequestController {
    @Autowired
    IRequestService iRequestService;

    @RequestMapping(value = "getRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<RequestDTO>> userRegister(@RequestBody RequestDTO requestDTO, HttpServletRequest request,
                                                           HttpServletResponse response) {

        return iRequestService.getRequestList(requestDTO,request,response);
    }

    @RequestMapping(value = "insertRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertRegister(@RequestBody InsertRequestDTO insertRequestDTO) {

        return iRequestService.insertRequestList(insertRequestDTO);
    }

    @RequestMapping(value = "insertLeaveRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertLeaveRegister(@RequestBody InsertLeaveRequestDTO insertLeaveRequestDTO) {

        return iRequestService.insertLeaveRequestList(insertLeaveRequestDTO);
    }

    @RequestMapping(value = "search.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<RequestDTO>> search(@RequestBody RequestDTO requestDTO, HttpServletRequest request,
                                         HttpServletResponse response) {

        return iRequestService.searchRequestList(requestDTO,request,response);
    }
    @RequestMapping(value = "updateSkRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(@RequestBody UpdateSkRequestDTO updateSkRequestDTO) {

        // TODO
        return iRequestService.updateSkRequestList(updateSkRequestDTO);
    }

    @RequestMapping(value = "updateReturnRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(@RequestBody UpdateReturnRequestDTO updateReturnRequestDTO) {

        // TODO
        return iRequestService.updateReturnRequestList(updateReturnRequestDTO);
    }

    @RequestMapping(value = "updateLaRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(@RequestBody UpdateLaRequestDTO updateLaRequestDTO) {

        // TODO
        return iRequestService.updateLaRequestList(updateLaRequestDTO);
    }

    @RequestMapping(value = "/deleteRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delete(@RequestBody RequestDTO requestDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iRequestService.deleteRequestList(requestDTO,request, response);
    }

}




