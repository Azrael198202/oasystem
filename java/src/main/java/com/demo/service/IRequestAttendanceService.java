package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.requestAttendance.*;
import com.demo.pojo.wf.WfDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IRequestAttendanceService {

    ServerResponse<List<RequestAttendanceDTO>> getRequestAttendanceList(RequestAttendanceDTO requestAttendanceDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse getRequestAttendanceByList(RequestAttendanceByDTO requestAttendanceByDTO);

    ServerResponse<RequestAttendanceReqDTO> selectreqList(RequestAttendanceReqDTO requestAttendanceReqDTO);

    ServerResponse insertRequestAttendanceList(InsertRequestAttendanceDTO insertRequestAttendanceDTO);

    ServerResponse updateRequestAttendanceList(UpdateRequestAttendanceDTO updateRequestAttendanceDTO);

    ServerResponse updateNumRequestAttendanceList(UpdateNumRequestAttendanceDTO updateNumRequestAttendanceDTO);

    ServerResponse<String> deleteRequestAttendanceList(RequestAttendanceDTO requestAttendanceDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse getStatusAttendance(GetStatusAttendanceDTO getStatusAttendanceDTO);
}
