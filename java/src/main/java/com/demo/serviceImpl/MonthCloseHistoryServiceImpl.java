package com.demo.serviceImpl;

import com.demo.common.ServerResponse;
import com.demo.dao.MonthCloseHistoryMapper;
import com.demo.pojo.monthCloseHistory.MonthCloseHistoryDTO;
import com.demo.pojo.monthCloseHistory.PaidHolidayHistoryMonthDTO;
import com.demo.service.IMonthCloseHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Slf4j
public class MonthCloseHistoryServiceImpl implements IMonthCloseHistoryService {

    @Autowired
    MonthCloseHistoryMapper monthCloseHistoryMapper;

    @Override
    public ServerResponse<String> selectTgtMonth(MonthCloseHistoryDTO monthCloseHistoryDTO, HttpServletRequest request, HttpServletResponse response) {

        String TgtMonth = monthCloseHistoryMapper.selectTgtMonth(monthCloseHistoryDTO.userId, monthCloseHistoryDTO.processResult);

        if(TgtMonth == null) {
            return ServerResponse.createBySuccess("月締め処理データが存在しません。", "0");
        }else {
            return ServerResponse.createBySuccess(TgtMonth);
        }

    }

    @Override
    public ServerResponse<List<PaidHolidayHistoryMonthDTO>> paidHolidayHistoryMonth(MonthCloseHistoryDTO monthCloseHistoryDTO, HttpServletRequest request, HttpServletResponse response) {
        List<PaidHolidayHistoryMonthDTO> paidHolidayMonth = monthCloseHistoryMapper.paidHolidayHistoryMonth(monthCloseHistoryDTO.userId, monthCloseHistoryDTO.processResult);
        return ServerResponse.createBySuccess(paidHolidayMonth);
    }
}
