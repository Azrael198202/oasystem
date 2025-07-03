package com.demo.controller.wf;

import com.demo.common.ServerResponse;
import com.demo.pojo.group.GroupDTO;
import com.demo.pojo.requestAttendance.UpdateRequestAttendanceDTO;
import com.demo.pojo.wf.*;
import com.demo.service.IWfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/requestWf/")
public class WfController {
    @Autowired
    IWfService iWfService;

    @RequestMapping(value = "getWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<WfDTO>> userRegister(@RequestBody WfDTO wfDTO, HttpServletRequest request,
                                                HttpServletResponse response) {


        return iWfService.getWfList(wfDTO,request,response);
    }

    @RequestMapping(value = "deleteWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delete(@RequestBody WfDTO wfDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iWfService.deleteWfList(wfDTO,request, response);
    }

    @RequestMapping(value = "insertNewWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertNewWf(@RequestBody InsertNewWfDTO insertNewWfDTO) {

        return iWfService.insertNewWf(insertNewWfDTO);
    }

    @RequestMapping(value = "insertWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertRegister(@RequestBody InsertWfDTO insertWfDTO) {

        return iWfService.insertWfList(insertWfDTO);
    }

    @RequestMapping(value = "updateWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateWf(@RequestBody UpdateWfDTO updateWfDTO) {

        return iWfService.updateWfList(updateWfDTO);
    }
    @RequestMapping(value = "updateadmitWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(@RequestBody UpdateadmitWfDTO updateadmitWfDTO) {

        return iWfService.updateadmitWfList(updateadmitWfDTO);
    }
    @RequestMapping(value = "updatelastadmitWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(@RequestBody UpdatelastadmitWfDTO updatelastadmitWfDTO) {

        return iWfService.updatelastadmitWfList(updatelastadmitWfDTO);
    }

    @RequestMapping(value = "selectWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<SelectWfDTO>> selectWf(@RequestBody SelectWfDTO selectWfDTO, HttpServletRequest request,
                                                HttpServletResponse response) {

        return iWfService.selectWf(selectWfDTO,request, response);
    }
    @RequestMapping(value = "deleteAllWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteAllWf(@RequestBody WfDTO wfDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iWfService.deleteAllWf(wfDTO,request, response);
    }
    @RequestMapping(value = "deleteDataWf.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteDataWf(@RequestBody List<RemandWfDTO> remandWfDTO, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {

        return iWfService.deleteDataWf(remandWfDTO,request, response);
    }
}
