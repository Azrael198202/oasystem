package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.transportationExpenses.TransportationExpensesDTO;
import com.demo.pojo.transportationExpenses.SelectApplyMonthTpDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ITransportationExpensesService {
    ServerResponse<String> insertTransportationExpenses(List<TransportationExpensesDTO>  transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<TransportationExpensesDTO>> getTransportationExpenses(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> deleteTransportationExpenses(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> updateTransportationExpenses(List<TransportationExpensesDTO> transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<TransportationExpensesDTO>> searchRefMonthData(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<SelectApplyMonthTpDTO> selectMonth(SelectApplyMonthTpDTO selectApplyMonthTpDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> saveImgTemporary(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> deleteImageTemporary(String userId, HttpServletRequest request, HttpServletResponse response);
}
