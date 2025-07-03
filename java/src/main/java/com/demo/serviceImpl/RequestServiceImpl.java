package com.demo.serviceImpl;

import com.demo.common.Const;
import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.RequestMapper;
import com.demo.entity.Request;
import com.demo.pojo.request.*;
import com.demo.pojo.requestAttendance.UpdateRequestAttendanceDTO;
import com.demo.pojo.wf.WfDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import com.demo.service.IRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RequestServiceImpl implements IRequestService {

    @Autowired
    RequestMapper requestMapper;

    @Override
    public ServerResponse<List<RequestDTO>> getRequestList(RequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response) {

        List<RequestDTO> requestRpnDTOList = requestMapper.selectAll(requestDTO.requestUserId, requestDTO.companyCode);

        return ServerResponse.createBySuccess(requestRpnDTOList);
    }

    @Override
    public ServerResponse updateSkRequestList(UpdateSkRequestDTO updateSkRequestDTO) {

        Request requestList = new Request();

        BeanCopier beanCopier = BeanCopier.create(UpdateSkRequestDTO.class,Request.class,false);
        beanCopier.copy(updateSkRequestDTO,requestList,null);


        requestList.setRequestStatusKbn(Const.CATEGORY_030_010);

        Date date = new Date();
        requestList.setUpdateUser(updateSkRequestDTO.updateUser);
        requestList.setUpdateDatetime(date);

        //int result = requestMapper.updateByPrimaryKeySk(Const.CATEGORY_030_010,updateSkRequestDTO.companyCode,updateSkRequestDTO.requestNumber);
        int result = requestMapper.updateByPrimaryKeySk(requestList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("(差戻後)申請テーブルにデータ更新しました。");
        }

        return ServerResponse.createByErrorMessage("(差戻後)申請テーブルにデータ更新失敗しました。");
    }

    @Override
    public ServerResponse updateReturnRequestList(UpdateReturnRequestDTO updateReturnRequestDTO) {

        Request requestList = new Request();

        BeanCopier beanCopier = BeanCopier.create(UpdateReturnRequestDTO.class,Request.class,false);
        beanCopier.copy(updateReturnRequestDTO,requestList,null);


        requestList.setRequestStatusKbn(Const.CATEGORY_030_001);

        Date date = new Date();
        requestList.setUpdateUser(updateReturnRequestDTO.updateUser);
        requestList.setUpdateDatetime(date);

        int result = requestMapper.updateByPrimaryKeyRt(requestList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("(取戻後)申請テーブルのデータを更新しました。");
        }

        return ServerResponse.createByErrorMessage("(取戻後)申請テーブルのデータ更新に失敗しました。");
    }

    @Override
    public ServerResponse updateLaRequestList(UpdateLaRequestDTO updateLaRequestDTO) {

        Request requestList = new Request();

        BeanCopier beanCopier = BeanCopier.create(UpdateLaRequestDTO.class,Request.class,false);
        beanCopier.copy(updateLaRequestDTO,requestList,null);


        requestList.setRequestStatusKbn(Const.CATEGORY_030_999);
        Date date = new Date();
        requestList.setUpdateUser(updateLaRequestDTO.updateUser);
        requestList.setUpdateDatetime(date);

        //int result = requestMapper.updateByPrimaryKeyLa(Const.CATEGORY_030_999,updateLaRequestDTO.companyCode,updateLaRequestDTO.requestNumber);
        int result = requestMapper.updateByPrimaryKeyLa(requestList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("(最終承認後)申請テーブルにデータ更新しました。");
        }

        return ServerResponse.createByErrorMessage("(最終承認後)申請テーブルにデータ更新失敗しました。");
    }

    @Override
    public ServerResponse insertRequestList(InsertRequestDTO insertRequestDTO) {
        //20211014被退回之后重新申请时，番号为3，
        Request requestreq = requestMapper.selectByPrimaryKey (insertRequestDTO.companyCode,insertRequestDTO.requestNumber);
        if(requestreq != null){
            requestreq.setRequestStatusKbn(Const.CATEGORY_030_003);
            requestreq.setRequestOverview(insertRequestDTO.requestOverview);

            Date date = new Date();
            requestreq.setUpdateUser(insertRequestDTO.requestUserId);
            requestreq.setUpdateDatetime(date);

            int result = requestMapper.updateByPrimaryKeySelective(requestreq);
            if(result == ConstCode.NUM_1) {
                return ServerResponse.createBySuccessMessage("(再申請の場合)申請テーブルにデータ登録しました。");
            }
            return ServerResponse.createByErrorMessage("(再申請の場合)に申請テーブルにデータ登録失敗しました。");
        }

        Request requestList = new Request();

        BeanCopier beanCopier = BeanCopier.create(InsertRequestDTO.class,Request.class,false);
        beanCopier.copy(insertRequestDTO,requestList,null);

        Date date = new Date();
        requestList.setRequestStatusKbn(Const.CATEGORY_030_003);
        requestList.setRequestTypeKbn(Const.CATEGORY_031_010);
        requestList.setRegistrationUser(insertRequestDTO.requestUserId);
        requestList.setRegistrationDatetime(date);
        requestList.setUpdateUser(insertRequestDTO.requestUserId);
        requestList.setUpdateDatetime(date);

        int result = requestMapper.insert(requestList);

        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("申請テーブルにデータ登録しました。");
        }

        return ServerResponse.createByErrorMessage("申請テーブルにデータ登録失敗しました。");
    }
    @Override
    public ServerResponse insertLeaveRequestList(InsertLeaveRequestDTO insertLeaveRequestDTO) {
        //20211014被退回之后重新申请时，番号为3，
        Request requestreq = requestMapper.selectByPrimaryKey (insertLeaveRequestDTO.companyCode,insertLeaveRequestDTO.requestNumber);
        if(requestreq != null){
            requestreq.setRequestStatusKbn(insertLeaveRequestDTO.requestStatusKbn);
            requestreq.setRequestOverview(insertLeaveRequestDTO.requestOverview);

            Date date = new Date();
            requestreq.setUpdateUser(insertLeaveRequestDTO.requestUserId);
            requestreq.setUpdateDatetime(date);

            int result = requestMapper.updateByPrimaryKeySelective(requestreq);
            if(result == ConstCode.NUM_1) {
                return ServerResponse.createBySuccessMessage("(再申請の場合)申請テーブルにデータ登録しました。");
            }
            return ServerResponse.createByErrorMessage("(再申請の場合)に申請テーブルにデータ登録失敗しました。");
        }

        Request requestList = new Request();

        BeanCopier beanCopier = BeanCopier.create(InsertLeaveRequestDTO.class,Request.class,false);
        beanCopier.copy(insertLeaveRequestDTO,requestList,null);

        Date date = new Date();
        requestList.setRegistrationUser(insertLeaveRequestDTO.requestUserId);
        requestList.setRegistrationDatetime(date);
        requestList.setUpdateUser(insertLeaveRequestDTO.requestUserId);
        requestList.setUpdateDatetime(date);

        int result = requestMapper.insert(requestList);

        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("申請テーブルにデータ登録しました。");
        }

        return ServerResponse.createByErrorMessage("申請テーブルにデータ登録失敗しました。");
    }


    @Override
    public ServerResponse<List<RequestDTO>> searchRequestList(RequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response) {

        List<RequestDTO> requestRpnDTOList = requestMapper.search(requestDTO.companyCode, requestDTO.requestUserId,  ('%' + requestDTO.requestStatusKbn + '%'), ('%' + requestDTO.requestTypeKbn + '%'));

        return ServerResponse.createBySuccess(requestRpnDTOList);
    }

    @Override
    public ServerResponse<String> deleteRequestList(RequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response) {

        String companyCode = requestDTO.getCompanyCode();

        String requestNumber = requestDTO.getRequestNumber();


        int deleteWf = requestMapper.deleteByPrimaryKey(companyCode,requestNumber);
        if(deleteWf == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("申請テーブルにデータ削除しました。");
        }

        return ServerResponse.createByErrorMessage("申請テーブルにデータ削除失敗しました。");
    }
}
