package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.request.UpdateSkRequestDTO;
import com.demo.pojo.wf.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IWfService {
    ServerResponse<List<WfDTO>> getWfList(WfDTO wfDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> deleteWfList(WfDTO wfDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse insertWfList(InsertWfDTO insertWfDTO);

    ServerResponse updateWfList(UpdateWfDTO updateWfDTO);

    ServerResponse updateadmitWfList(UpdateadmitWfDTO updateadmitWfDTO);

    ServerResponse updatelastadmitWfList(UpdatelastadmitWfDTO updatelastadmitWfDTO);

    ServerResponse<List<SelectWfDTO>> selectWf(SelectWfDTO selectWfDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> deleteAllWf(WfDTO wfDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse insertNewWf(InsertNewWfDTO insertNewWfDTO);

    ServerResponse<String> deleteDataWf(List<RemandWfDTO> remandWfDTO, HttpServletRequest request, HttpServletResponse response);
}

