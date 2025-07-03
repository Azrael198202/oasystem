package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.overTime.OverTimeDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public interface IOverTimeService {
    ServerResponse<String> insertData(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<OverTimeDTO>> getRequestOvertime(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> deleteOvertime(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> countOvertimeDay(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response);
}
