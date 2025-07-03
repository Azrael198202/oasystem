package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.PaidHolidayMapper;
import com.demo.entity.PaidHoliday;
import com.demo.pojo.paidHoliday.*;
import com.demo.service.IPaidHolidayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class PaidHolidayServiceImpl implements IPaidHolidayService {
    @Autowired
    PaidHolidayMapper paidHolidayMapper;

    @Override
    public ServerResponse<Float> getRequestPaidHoliday(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response) {
        float requestPaidHolidayData = paidHolidayMapper.getRequestPaidHoliday(paidHolidayDTO.companyCode, paidHolidayDTO.userId);
        return ServerResponse.createBySuccess(requestPaidHolidayData);
    }

    @Override
    public ServerResponse<Float> EndMonthPaidHoliday(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response) {
        float endMonthData = paidHolidayMapper.EndMonthPaidHoliday(paidHolidayDTO.companyCode, paidHolidayDTO.userId);
        return ServerResponse.createBySuccess(endMonthData);
    }

    @Override
    public ServerResponse<Float> EndMonthAfterNextPaidHoliday(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response) {
        float endMonthAfterNextData = paidHolidayMapper.EndMonthAfterNextPaidHoliday(paidHolidayDTO.companyCode, paidHolidayDTO.userId);
        return ServerResponse.createBySuccess(endMonthAfterNextData);
    }

    @Override
    public ServerResponse<List<PaidHolidayMaintenanceDTO>> getPaidHolidayMaintenance(@RequestBody PaidHolidayMaintenanceDTO paidHolidayMaintenanceDTO, HttpServletRequest request, HttpServletResponse response) {

        List<PaidHolidayMaintenanceDTO> PaidHolidayMaintenanceDTOList = paidHolidayMapper.getPaidHolidayMaintenance(paidHolidayMaintenanceDTO.getCompanyCode(),paidHolidayMaintenanceDTO.getDateY());

        return ServerResponse.createBySuccess(PaidHolidayMaintenanceDTOList);
    }

    @Override
    public ServerResponse<Float> getPaidHolidayRemaining(PaidHolidayDTO paidHolidayDTO, HttpServletRequest request, HttpServletResponse response) {

        float getPaidHolidayRemainingData = paidHolidayMapper.getPaidHolidayRemaining(paidHolidayDTO.companyCode, paidHolidayDTO.userId,paidHolidayDTO.dateYm,paidHolidayDTO.assignmentYear);
        return ServerResponse.createBySuccess(getPaidHolidayRemainingData);
    }

}
