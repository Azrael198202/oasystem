package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.OverTimeMapper;
import com.demo.entity.OverTime;
import com.demo.pojo.overTime.OverTimeDTO;
import com.demo.service.IOverTimeService;
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
public class OverTimeServiceImpl implements IOverTimeService {
    @Autowired
    OverTimeMapper overTimeMapper;

    @Override
    public ServerResponse<String> insertData(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response) {

        OverTime overTime = new OverTime();

        BeanCopier beanCopier = BeanCopier.create(OverTimeDTO.class,OverTime.class,false);
        beanCopier.copy(overTimeDTO,overTime,null);

        Date date = new Date();

        overTime.setRegistrationUser(overTimeDTO.requestUserId);
        overTime.setUpdateUser(overTimeDTO.requestUserId);
        overTime.setRegistrationDatetime(date);
        overTime.setUpdateDatetime(date);
        int result = overTimeMapper.insert(overTime);
        if (result == ConstCode.NUM_1) {
                return ServerResponse.createBySuccessMessage("overTimeテーブルにデータ登録しました。");
        }
        return ServerResponse.createByErrorMessage("overTimeテーブルにデータ登録失敗しました。");
    }
    @Override
    public ServerResponse<List<OverTimeDTO>> getRequestOvertime(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response){
        List<OverTimeDTO> requestOvertimeDTOList = overTimeMapper.selectOvertime(overTimeDTO.companyCode, overTimeDTO.requestNumber);
        return  ServerResponse.createBySuccess(requestOvertimeDTOList);
    }
    @Override
    public ServerResponse<String> deleteOvertime(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response){
        String companyCode = overTimeDTO.getCompanyCode();
        String requestNumber = overTimeDTO.getRequestNumber();
        String requestUserId = overTimeDTO.getRequestUserId();

        int deleteOt = overTimeMapper.deleteByPrimaryKey(companyCode,requestNumber,requestUserId);
        if( deleteOt == ConstCode.NUM_1){
            return ServerResponse.createBySuccessMessage("深夜残業申請内容テーブルのレコードを削除しました。");
        }
        return  ServerResponse.createByErrorMessage("深夜残業申請内容テーブルのレコードの削除が失敗しました。");
    }
    @Override
    public ServerResponse<String> countOvertimeDay(OverTimeDTO overTimeDTO, HttpServletRequest request, HttpServletResponse response){
        int checkForDuplicate = overTimeMapper.countOvertimeDay(overTimeDTO.companyCode,overTimeDTO.scheduledOvertimeDay,overTimeDTO.requestUserId);
        if(checkForDuplicate >= ConstCode.NUM_1) {
            return ServerResponse.createBySuccess("overTimeテーブルに同じ日付のデータが存在します。","1");
        }
        return ServerResponse.createBySuccess("overTimeテーブルに同じ日付のデータが存在しません。","0");
    }
}
