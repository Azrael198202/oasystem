package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.request.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IRequestService {

    ServerResponse<List<RequestDTO>> getRequestList(RequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> deleteRequestList(RequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse insertRequestList(InsertRequestDTO insertRequestDTO);

    ServerResponse insertLeaveRequestList(InsertLeaveRequestDTO insertLeaveRequestDTO);

    ServerResponse<List<RequestDTO>> searchRequestList(RequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse updateSkRequestList(UpdateSkRequestDTO updateSkRequestDTO);

    ServerResponse updateLaRequestList(UpdateLaRequestDTO updateLaRequestDTO);

    ServerResponse updateReturnRequestList(UpdateReturnRequestDTO updateReturnRequestDTO);
}

