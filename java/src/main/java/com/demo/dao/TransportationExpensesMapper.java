package com.demo.dao;

import com.demo.entity.TransportationExpenses;
import com.demo.entity.report.ExpensePdf;
import com.demo.entity.report.TransportationExpensesOtherTransportPdf;
import com.demo.entity.report.TransportationExpensesTransportPdf;
import com.demo.pojo.transportationExpenses.TransportationExpensesDTO;
import com.demo.pojo.transportationExpenses.SelectApplyMonthTpDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransportationExpensesMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("applyYm") String applyYm, @Param("requestNumber") String requestNumber, @Param("requestSubNumber") Byte requestSubNumber);

    int insert(TransportationExpenses record);

    int insertSelective(TransportationExpenses record);

    TransportationExpenses selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("applyYm") String applyYm, @Param("requestNumber") String requestNumber, @Param("requestSubNumber") Byte requestSubNumber);

    int updateByPrimaryKeySelective(TransportationExpenses record);

    int updateByPrimaryKey(TransportationExpenses record);

    List<TransportationExpensesDTO> selectAll(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("requestNumber") String requestNumber);

    int deleteTransportationExpenses(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    List<TransportationExpensesDTO> searchRefMonthData(@Param("companyCode") String companyCode, @Param("applyYm") String applyYm, @Param("requestUserId") String requestUserId);

    List<TransportationExpensesDTO> searchImage(@Param("requestNumber") String requestNumber);

    List<TransportationExpensesDTO> searchImageOne(@Param("requestNumber") String requestNumber,@Param("requestSubNumber") Byte requestSubNumber,@Param("companyCode") String companyCode);

    SelectApplyMonthTpDTO selectMonth(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId);

    List<TransportationExpensesTransportPdf> selectTransportPdfData(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("requestNumber") String requestNumber);

    List<TransportationExpensesOtherTransportPdf> selectOtherTransportPdfData(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("requestNumber") String requestNumber);

    List<ExpensePdf> selectExpensePdfData(@Param("companyCode") String companyCode, @Param("requestUserId") String requestUserId, @Param("requestNumber") String requestNumber);

}