package com.demo.serviceImpl;

import com.demo.common.ServerResponse;
import com.demo.dao.DayCheckHistoryMapper;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;
import com.demo.service.IDayCheckHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Slf4j
public class DayCheckHistoryServiceImpl implements IDayCheckHistoryService {
    @Autowired
    DayCheckHistoryMapper dayCheckHistoryMapper;

    @Override
    public ServerResponse<List<DayCheckHistoryDTO>> getDayCheckHistory(DayCheckHistoryDTO dayCheckHistoryDTO, HttpServletRequest request, HttpServletResponse response) {

        List<DayCheckHistoryDTO> dayCheckHistoryList = dayCheckHistoryMapper.getDayCheckHistory(dayCheckHistoryDTO.companyCode);

        return ServerResponse.createBySuccess(dayCheckHistoryList);
    }

    @Override
    public ServerResponse<List<DayCheckHistoryDTO>> searchDayCheckHistory(DayCheckHistoryDTO dayCheckHistoryDTO, HttpServletRequest request, HttpServletResponse response) {

        List<DayCheckHistoryDTO>  searchDayCheckHistoryList = dayCheckHistoryMapper.searchDayCheckHistory(dayCheckHistoryDTO.companyCode, ('%' + dayCheckHistoryDTO.userName + '%'), ('%' + dayCheckHistoryDTO.checkDay + '%'));

        return ServerResponse.createBySuccess(searchDayCheckHistoryList);
    }
}
