package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.exchangedWorkDay.ExchangedWorkDayDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IExchangedWorkDayService {
    ServerResponse<String> insertExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<ExchangedWorkDayDTO>> selectExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> deleteExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> countExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response);
}
