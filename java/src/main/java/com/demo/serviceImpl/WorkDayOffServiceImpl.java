package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.WorkDayOffMapper;
import com.demo.entity.WorkDayOff;
import com.demo.pojo.workDayOff.GetWorkDayOffInfoDTO;
import com.demo.pojo.workDayOff.WorkDayOffDTO;
import com.demo.pojo.workDayOff.WorkDayOffResultDTO;
import com.demo.pojo.workDayOff.WorkDayOffSearchDTO;
import com.demo.service.IWorkDayOffService;
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
public class WorkDayOffServiceImpl implements IWorkDayOffService {
    @Autowired
    WorkDayOffMapper workDayOffMapper;

    @Override
    public ServerResponse<String>insertWorkDayOff(WorkDayOffDTO workDayOffDTO, HttpServletRequest request, HttpServletResponse response){

        WorkDayOff workDayOff = new WorkDayOff();

        BeanCopier beanCopier = BeanCopier.create(WorkDayOffDTO.class,WorkDayOff.class,false);
        beanCopier.copy(workDayOffDTO,workDayOff,null);

        Date date = new Date();

        workDayOff.setRegistrationUser(workDayOffDTO.requestUserId);
        workDayOff.setUpdateUser(workDayOffDTO.requestUserId);
        workDayOff.setRegistrationDatetime(date);
        workDayOff.setUpdateDatetime(date);
        int result = workDayOffMapper.insert(workDayOff);
        if (result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("workDayOffテーブルにデータ登録しました。");
        }else if(result > ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("workDayOffテーブルにデータ更新しました。");
        }
        return ServerResponse.createByErrorMessage("workDayOffテーブルにデータ登録失敗しました。");
    }
    @Override
    public ServerResponse<List<WorkDayOffDTO>> selectWorkDayOff(WorkDayOffDTO workDayOffDTO, HttpServletRequest request, HttpServletResponse response){
        List<WorkDayOffDTO> selectWorkDayOffList = workDayOffMapper.selectWorkDayOff(workDayOffDTO.companyCode, workDayOffDTO.requestNumber);
        return ServerResponse.createBySuccess(selectWorkDayOffList);
    }
    @Override
    public ServerResponse<String> deleteWorkDayOff(WorkDayOffDTO workDayOffDTO, HttpServletRequest request, HttpServletResponse response){
        String companyCode = workDayOffDTO.getCompanyCode();
        String requestNumber = workDayOffDTO.getRequestNumber();
        String requestUserId = workDayOffDTO.getRequestUserId();

        int deleteOt = workDayOffMapper.deleteByPrimaryKey(companyCode,requestNumber,requestUserId);
        if( deleteOt == ConstCode.NUM_1){
            return ServerResponse.createBySuccessMessage("休日出勤申請内容テーブルのレコードを削除しました。");
        }
        return  ServerResponse.createByErrorMessage("休日出勤申請内容テーブルのレコードの削除が失敗しました。");
    }
    //　　代休残入力画面：休日出勤テーブルの申請番号取得
    @Override
    public ServerResponse<WorkDayOffResultDTO> selectRequestNumberWorkDayOff(WorkDayOffSearchDTO workDayOffSearchDTO, HttpServletRequest request, HttpServletResponse response){
        String companyCode = workDayOffSearchDTO.getCompanyCode();
        String requestUserId = workDayOffSearchDTO.getUserId();
        String scheduledWorkDay = workDayOffSearchDTO.getScheduledWorkDay();

        WorkDayOffResultDTO res = workDayOffMapper.requestNumberSubHoliday(companyCode,requestUserId,scheduledWorkDay);
        return  ServerResponse.createBySuccess(res);
    }
    //　　休日出勤データ取得
    @Override
    public ServerResponse<List<GetWorkDayOffInfoDTO>> getWorkDayOffInfo(GetWorkDayOffInfoDTO getWorkDayOffInfoDTO, HttpServletRequest request, HttpServletResponse response){
        List<GetWorkDayOffInfoDTO> getWorkDayOffList = workDayOffMapper.getWorkDayOffInfo(getWorkDayOffInfoDTO.companyCode, getWorkDayOffInfoDTO.requestUserId, getWorkDayOffInfoDTO.scheduledWorkDay);
        return ServerResponse.createBySuccess(getWorkDayOffList);
    }
}
