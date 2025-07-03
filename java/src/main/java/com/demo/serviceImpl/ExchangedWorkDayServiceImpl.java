package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.ExchangedWorkDayMapper;
import com.demo.entity.ExchangedWorkDay;
import com.demo.pojo.exchangedWorkDay.ExchangedWorkDayDTO;
import com.demo.service.IExchangedWorkDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExchangedWorkDayServiceImpl implements IExchangedWorkDayService {
    @Autowired
    ExchangedWorkDayMapper exchangedWorkDayMapper;

    @Override
    public ServerResponse<String>insertExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response){

        ExchangedWorkDay exchangedWorkDay = new ExchangedWorkDay();

        BeanCopier beanCopier = BeanCopier.create(ExchangedWorkDayDTO.class,ExchangedWorkDay.class,false);
        beanCopier.copy(exchangedWorkDayDTO,exchangedWorkDay,null);

        Date date = new Date();

        exchangedWorkDay.setRegistrationUser(exchangedWorkDayDTO.requestUserId);
        exchangedWorkDay.setUpdateUser(exchangedWorkDayDTO.requestUserId);
        exchangedWorkDay.setRegistrationDatetime(date);
        exchangedWorkDay.setUpdateDatetime(date);
        int result = exchangedWorkDayMapper.insert(exchangedWorkDay);
        if (result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("exchangedWorkDayテーブルにデータ登録しました。");
        }
        return ServerResponse.createByErrorMessage("exchangedWorkDayテーブルにデータ登録失敗しました。");
    }
    @Override
    public ServerResponse<List<ExchangedWorkDayDTO>> selectExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response){
        List<ExchangedWorkDayDTO> selectExchangedWorkDayList = exchangedWorkDayMapper.selectExchangedWorkDay(exchangedWorkDayDTO.companyCode, exchangedWorkDayDTO.requestNumber);
        return ServerResponse.createBySuccess(selectExchangedWorkDayList);
    }
    @Override
    public ServerResponse<String> deleteExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response){
        String companyCode = exchangedWorkDayDTO.getCompanyCode();
        String requestNumber = exchangedWorkDayDTO.getRequestNumber();
        String requestUserId = exchangedWorkDayDTO.getRequestUserId();

        int deleteOt = exchangedWorkDayMapper.deleteByPrimaryKey(companyCode,requestNumber,requestUserId);
        if( deleteOt == ConstCode.NUM_1){
            return ServerResponse.createBySuccessMessage("休日出勤申請内容テーブルのレコードを削除しました。");
        }
        return  ServerResponse.createByErrorMessage("休日出勤申請内容テーブルのレコードの削除が失敗しました。");
    }
    @Override
    public ServerResponse<String> countExchangedWorkDay(ExchangedWorkDayDTO exchangedWorkDayDTO, HttpServletRequest request, HttpServletResponse response){
        int checkForDuplicate = exchangedWorkDayMapper.countExchangedWorkDay(exchangedWorkDayDTO.companyCode,exchangedWorkDayDTO.scheduledWorkDay,exchangedWorkDayDTO.requestUserId);
        if(checkForDuplicate >= ConstCode.NUM_1) {
            return ServerResponse.createBySuccess("重複する日付のデータが存在します。","1");
        }
        return ServerResponse.createBySuccess("重複する日付のデータが存在しません。","0");
    }
}
