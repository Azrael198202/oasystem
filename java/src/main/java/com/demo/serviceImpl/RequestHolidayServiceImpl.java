package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.RequestHolidayMapper;
import com.demo.entity.Request;
import com.demo.entity.RequestHoliday;
import com.demo.entity.Wf;
import com.demo.pojo.attendance.AttendanceTimeDTO;
import com.demo.pojo.overTime.OverTimeDTO;
import com.demo.pojo.request.InsertLeaveRequestDTO;
import com.demo.pojo.request.RequestDTO;
import com.demo.pojo.requestHoliday.*;
import com.demo.pojo.wf.InsertWfDTO;
import com.demo.pojo.requestHoliday.RequestCompleteHolidayDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import com.demo.service.IRequestHolidayService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RequestHolidayServiceImpl implements IRequestHolidayService {

    @Autowired
    RequestHolidayMapper requestHolidayMapper;

    @Override
    public ServerResponse<List<RequestHolidayDTO>> getRequestHolidayList(RequestHolidayDTO requestHolidayDTO, HttpServletRequest request, HttpServletResponse response) {

        List<RequestHolidayDTO> requestHolidayRpnDTOList = requestHolidayMapper.selectAll(requestHolidayDTO.companyCode, requestHolidayDTO.requestUserId, requestHolidayDTO.requestNumber);

        return ServerResponse.createBySuccess(requestHolidayRpnDTOList);
    }

    @Override
    public ServerResponse insertRequestHolidayList(InsertRequestHolidayDTO insertRequestHolidayDTO, HttpServletRequest request, HttpServletResponse response) {

        RequestHoliday requestHolidayList = new RequestHoliday();

        BeanCopier beanCopier = BeanCopier.create(InsertRequestHolidayDTO.class,RequestHoliday.class,false);
        beanCopier.copy(insertRequestHolidayDTO,requestHolidayList,null);

        Date date = new Date();
        requestHolidayList.setRegistrationUser(insertRequestHolidayDTO.requestUserId);
        requestHolidayList.setRegistrationDatetime(date);
        requestHolidayList.setUpdateUser(insertRequestHolidayDTO.requestUserId);
        requestHolidayList.setUpdateDatetime(date);

        int result = requestHolidayMapper.insert(requestHolidayList);

        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("休暇申請内容テーブルにデータ登録しました。");
        }

        return ServerResponse.createByErrorMessage("休暇申請内容テーブルへのデータ登録が失敗しました。");
    }

    @Override
    public ServerResponse<String> deleteRequestHolidayList(RequestHolidayDTO requestHolidayDTO, HttpServletRequest request, HttpServletResponse response) {

        String companyCode = requestHolidayDTO.getCompanyCode();
        String requestNumber = requestHolidayDTO.getRequestNumber();
        String requestUserId = requestHolidayDTO.getRequestUserId();


        int deleteRh = requestHolidayMapper.deleteRequestHoliday(companyCode, requestNumber, requestUserId);
        if(deleteRh == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("休暇申請内容テーブルのレコードを削除しました。");
        }

        return ServerResponse.createByErrorMessage("休暇申請内容テーブルのレコードの削除が失敗しました。");
    }

    @Override
    public ServerResponse<String> updateRequestHolidayList(List<UpdateRequestHolidayDTO> updateRequestHolidayDTO, HttpServletRequest request, HttpServletResponse response) {

        String companyCode = updateRequestHolidayDTO.get(0).companyCode;
        String requestNumber = updateRequestHolidayDTO.get(0).requestNumber;
        String requestUserId = updateRequestHolidayDTO.get(0).requestUserId;

        int deleteRh = requestHolidayMapper.deleteRequestHoliday(companyCode, requestNumber, requestUserId);

        if(deleteRh != 0) {
            //  ループで囲む
            for(int i = 1; i < updateRequestHolidayDTO.size(); i++) {
                RequestHoliday requestHolidayList = new RequestHoliday();

                BeanCopier beanCopier = BeanCopier.create(UpdateRequestHolidayDTO.class, RequestHoliday.class, false);
                beanCopier.copy(updateRequestHolidayDTO.get(i), requestHolidayList, null);

                Date date = new Date();
                requestHolidayList.setRegistrationUser(updateRequestHolidayDTO.get(i).requestUserId);
                requestHolidayList.setRegistrationDatetime(date);
                requestHolidayList.setUpdateUser(updateRequestHolidayDTO.get(i).requestUserId);
                requestHolidayList.setUpdateDatetime(date);

                int result = requestHolidayMapper.insert(requestHolidayList);

                if(result != ConstCode.NUM_1) {

                    return ServerResponse.createByErrorMessage("休暇申請内容テーブルへのデータ更新が失敗しました。");

                }
            }
            //  ループで囲む
            return ServerResponse.createBySuccessMessage("休暇申請内容テーブルのデータを更新しました。");
        }
        return ServerResponse.createByErrorMessage("休暇申請内容テーブルの削除に失敗しました。");
    }

    @Override
    public ServerResponse<String> countLeaveDay(List<RequestHolidayDTO> requestHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        for(int i = 0; i < requestHolidayDTO.size(); i++){
            int checkForDuplicate = requestHolidayMapper.countLeaveDay(requestHolidayDTO.get(i).companyCode,requestHolidayDTO.get(i).holidayPlanDay,requestHolidayDTO.get(i).requestUserId, requestHolidayDTO.get(i).requestNumber);
            if(checkForDuplicate >= ConstCode.NUM_1) {
                return ServerResponse.createBySuccess("休暇申請内容テーブルに同じ日付のデータが存在します。","1");
            }

        }
        return ServerResponse.createBySuccess("休暇申請内容テーブルに同じ日付のデータが存在しません。","0");
    }

    @Override
    public ServerResponse<String> checkNum(List<RequestHolidayDTO> requestHolidayDTO, HttpServletRequest request, HttpServletResponse response){

//            有給取得可(0)・不可(+1)
            int flg=0;

//            有給の使用数
            int flg2=0;

            for(int i = 0; i < requestHolidayDTO.size(); i++){
                List<checkNumDTO> checkRemainingNum = requestHolidayMapper.checkNum(requestHolidayDTO.get(i).companyCode, requestHolidayDTO.get(i).requestUserId, requestHolidayDTO.get(i).holidayPlanDay);
                
                if(checkRemainingNum.get(0).count - flg2  < ConstCode.NUM_TEN_5){
                    flg++;
                }else {
                    flg2++;
                }
                
                
            }
            if(flg == 0) {
                return ServerResponse.createBySuccess("有給休暇取得可能です。", "0");
            }else {
            	return ServerResponse.createBySuccess("有給休暇の日数が足りません。", "1");
            }
    }

    @Override
    public ServerResponse<List<PaidHolidayHistoryDTO>> paidHolidayHistory(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response){
        List<PaidHolidayHistoryDTO> requestPaidHolidayHistoryDTOList = requestHolidayMapper.paidHolidayHistory(paidHolidayHistoryDTO.companyCode, paidHolidayHistoryDTO.requestUserId);
        return  ServerResponse.createBySuccess(requestPaidHolidayHistoryDTOList);
    }
    @Override
    public ServerResponse<List<PaidHolidayHistoryDTO>> paidHolidayNextMonth(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response){
        List<PaidHolidayHistoryDTO> requestPaidHolidayHistoryDTOList = requestHolidayMapper.paidHolidayNextMonth(paidHolidayHistoryDTO.companyCode, paidHolidayHistoryDTO.requestUserId);
        return  ServerResponse.createBySuccess(requestPaidHolidayHistoryDTOList);
    }
    @Override
    public ServerResponse<List<PaidHolidayHistoryDTO>> subHolidayHistory(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response){
        List<PaidHolidayHistoryDTO> requestPaidHolidayHistoryDTOList = requestHolidayMapper.subHolidayHistory(paidHolidayHistoryDTO.companyCode, paidHolidayHistoryDTO.requestUserId,paidHolidayHistoryDTO.setDate);
        return  ServerResponse.createBySuccess(requestPaidHolidayHistoryDTOList);
    }
    @Override
    public ServerResponse<List<PaidHolidayHistoryDTO>> subHolidayNextMonth(PaidHolidayHistoryDTO paidHolidayHistoryDTO, HttpServletRequest request, HttpServletResponse response){
        List<PaidHolidayHistoryDTO> requestPaidHolidayHistoryDTOList = requestHolidayMapper.subHolidayNextMonth(paidHolidayHistoryDTO.companyCode, paidHolidayHistoryDTO.requestUserId,paidHolidayHistoryDTO.setDate);
        return  ServerResponse.createBySuccess(requestPaidHolidayHistoryDTOList);
    }
    @Override
    public ServerResponse<List<RequestCompleteHolidayDTO>>getRequestCompleteHoliday(@RequestBody RequestCompleteHolidayDTO requestCompleteHolidayDTO, HttpServletRequest request, HttpServletResponse response) {
        String holidayPlanDay=requestCompleteHolidayDTO.getPlanYear();
        List<RequestCompleteHolidayDTO> RequestCompleteHolidayDTOList = requestHolidayMapper.getRequestCompleteHoliday(holidayPlanDay,requestCompleteHolidayDTO.getCompanyCode());

        return ServerResponse.createBySuccess(RequestCompleteHolidayDTOList);
    }
    @Override
    public ServerResponse<String> countSubHoliday(RequestHolidayDTO requestHolidayDTO, HttpServletRequest request, HttpServletResponse response){
        int checkForDuplicate = requestHolidayMapper.countSubHoliday(requestHolidayDTO.companyCode,requestHolidayDTO.requestUserId,requestHolidayDTO.workDayoffRequestNumber);
        if(checkForDuplicate >= ConstCode.NUM_1) {
            return ServerResponse.createBySuccess("休暇申請内容テーブルに同じ日付のデータが存在します。","1");
        }
        return ServerResponse.createBySuccess("休暇申請内容テーブルに同じ日付のデータが存在しません。","0");
    }
}

