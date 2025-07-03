package com.demo.serviceImpl;

import com.demo.common.Const;
import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.SubHolidayMapper;
import com.demo.dao.WfMapper;
import com.demo.dao.WorkDayOffMapper;
import com.demo.entity.SubHoliday;
import com.demo.entity.Wf;
import com.demo.entity.WorkDayOff;
import com.demo.pojo.group.GroupDTO;
import com.demo.pojo.requestHoliday.RequestHolidayDTO;
import com.demo.pojo.wf.*;
import com.demo.pojo.workDayOff.WorkDayOffDTO;
import com.demo.service.IWfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WfServiceImpl implements IWfService {

    @Autowired
    WfMapper wfMapper;

    @Autowired
    WorkDayOffMapper workDayOffMapper;

    @Autowired
    SubHolidayMapper subHolidayMapper;
    

    @Override
    public ServerResponse<List<WfDTO>> getWfList(WfDTO wfDTO, HttpServletRequest request, HttpServletResponse response) {

        List<WfDTO> wc = wfMapper.getCount(wfDTO.companyCode, wfDTO.requestNumber);
//      List<WfDTO> wfRpnDTOList = wfMapper.selectAll(wfDTO.getCompanyCode());

        return ServerResponse.createBySuccess(wc);
    }

    @Override
    public ServerResponse<String> deleteWfList(WfDTO wfDTO, HttpServletRequest request, HttpServletResponse response) {
        // 会社コード
        String companyCode = wfDTO.getCompanyCode();

        String requestNumber = wfDTO.getRequestNumber();

        Byte reqAppOrder = wfDTO.getReqAppOrder();

        int deleteWf = wfMapper.deleteByPrimaryKey(companyCode,requestNumber,reqAppOrder);
        if(deleteWf == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("ワークフローテーブルにデータ削除しました。");
        }

        return ServerResponse.createByErrorMessage("ワークフローテーブルにデータ削除失敗しました。");
    }

    //    最初に作るデータ（申請,承認）
    @Override
    public ServerResponse insertNewWf(InsertNewWfDTO insertNewWfDTO) {

        /*1件目データ（申請側）*/
        Wf wfRequest = new Wf();
        Date date = new Date();

        int reqOrder = Integer.parseInt(insertNewWfDTO.total);
        reqOrder++;
        wfRequest.setCompanyCode(insertNewWfDTO.companyCode);// 会社コード
        wfRequest.setRequestNumber(insertNewWfDTO.requestNumber);// 申請番号
        wfRequest.setReqAppOrder(reqOrder);// 申請・承認順
        wfRequest.setReqAppUserId(insertNewWfDTO.reqRequestUserId);// 申請者ID
        wfRequest.setReqAppKbn(ConstCode.REQUEST_APPROVE_KBN.APPLY);// 申請承認区分
        wfRequest.setWfStatusKbn(ConstCode.WKFL_STATUS_KBN.APPLIED);// ワークフロー状態区分
        wfRequest.setReqAppComment(insertNewWfDTO.reqAppComment);// コメント欄
        wfRequest.setReqAppDatetime(date);// 申請日時
        wfRequest.setRegistrationUser(insertNewWfDTO.reqRequestUserId);// 登録者
        wfRequest.setRegistrationDatetime(date);// 登録日時
        wfRequest.setUpdateUser(insertNewWfDTO.reqRequestUserId);//　更新者
        wfRequest.setUpdateDatetime(date);// 更新日時

        int result = wfMapper.insert(wfRequest);
        if(result == ConstCode.NUM_1) {
            /*２件目データ（承認側）*/
            Wf wfApprove = new Wf();
            reqOrder++;
            wfApprove.setCompanyCode(insertNewWfDTO.companyCode);// 会社コード
            wfApprove.setRequestNumber(insertNewWfDTO.requestNumber);// 申請番号
            wfApprove.setReqAppOrder(reqOrder);// 申請・承認順
            wfApprove.setReqAppUserId(insertNewWfDTO.reqApproveUserId);// 承認者ID
            wfApprove.setReqAppKbn(ConstCode.REQUEST_APPROVE_KBN.APPROVE);// 申請承認区分
            wfApprove.setWfStatusKbn(ConstCode.WKFL_STATUS_KBN.UNAPPROVED);// ワークフロー状態区分
            wfApprove.setReqAppComment(insertNewWfDTO.reqAppComment);// コメント欄
            wfApprove.setReqAppDatetime(date);// 承認日時
            wfApprove.setRegistrationUser(insertNewWfDTO.reqRequestUserId);// 登録者
            wfApprove.setRegistrationDatetime(date);// 登録日時
            wfApprove.setUpdateUser(insertNewWfDTO.reqRequestUserId);//　更新者
            wfApprove.setUpdateDatetime(date);// 更新日時

            result = wfMapper.insert(wfApprove);
            if (result == ConstCode.NUM_1) {
                return ServerResponse.createBySuccessMessage("ワークフローテーブルにデータ登録しました。");
            }
        }

        return ServerResponse.createByErrorMessage("ワークフローテーブルにデータ登録失敗しました。");
    }
    //    承認
    @Override
    public ServerResponse insertWfList(InsertWfDTO insertWfDTO) {

        Wf wfList = new Wf();

        BeanCopier beanCopier = BeanCopier.create(InsertWfDTO.class,Wf.class,false);
        beanCopier.copy(insertWfDTO,wfList,null);

        Date date = new Date();

        wfList.setRequestNumber(insertWfDTO.requestNumber);// 申請番号
        wfList.setReqAppOrder(insertWfDTO.reqAppOrder);// 申請・承認順
//        wfList.setReqAppKbn(ConstCode.REQUEST_APPROVE_KBN.APPLY);// 申請承認区分
        wfList.setReqAppKbn(ConstCode.REQUEST_APPROVE_KBN.APPROVE);// 申請承認区分
        wfList.setWfStatusKbn(ConstCode.WKFL_STATUS_KBN.UNAPPROVED);// ワークフロー状態区分
        wfList.setReqAppComment(insertWfDTO.reqAppComment);// コメント欄
        wfList.setReqAppDatetime(date);// 申請日時
        wfList.setRegistrationUser(insertWfDTO.reqAppUserId);// 登録者
        wfList.setRegistrationDatetime(date);// 登録日時
        wfList.setUpdateUser(insertWfDTO.reqAppUpUserId);//　更新者
        wfList.setUpdateDatetime(date);// 更新日時

        int result = wfMapper.insert(wfList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("ワークフローテーブルにデータ登録しました。");
        }

        return ServerResponse.createByErrorMessage("ワークフローテーブルにデータ登録失敗しました。");
    }
    //    差戻
    @Override
    public ServerResponse updateWfList(UpdateWfDTO updateWfDTO) {

        Wf wfList = new Wf();
        Date date = new Date();

        BeanCopier beanCopier = BeanCopier.create(UpdateWfDTO.class,Wf.class,false);
        beanCopier.copy(updateWfDTO,wfList,null);

        wfList.setRequestNumber(updateWfDTO.requestNumber);// 申請番号
        wfList.setReqAppOrder(updateWfDTO.reqAppOrder);// 申請・承認順
        wfList.setWfStatusKbn(ConstCode.WKFL_STATUS_KBN.SENDBACK);// ワークフロー状態区分
        wfList.setReqAppComment(updateWfDTO.reqAppComment);// コメント欄
        wfList.setReqAppDatetime(date);// 申請・承認日時
        wfList.setUpdateUser(updateWfDTO.updateUser);// 更新者
        wfList.setUpdateDatetime(date);// 更新日時

        int result = wfMapper.updateByPrimaryKeyWf(wfList);

        // 休日出勤データを検索する。
        List<WorkDayOffDTO> selectWorkDayOffList = workDayOffMapper.selectWorkDayOff(updateWfDTO.companyCode, updateWfDTO.requestNumber);

        for (WorkDayOffDTO workDayOffDTO : selectWorkDayOffList) {
            if (workDayOffDTO.workTypeKbn.equals("021")){
                subHolidayMapper.deleteByPrimaryKey(updateWfDTO.companyCode,workDayOffDTO.requestUserId,workDayOffDTO.scheduledWorkDay);
            }
        }

        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("ワークフローテーブルにデータ更新しました。");
        }

        return ServerResponse.createByErrorMessage("ワークフローテーブルにデータ更新失敗しました。");
    }
    //    承認済
    @Override
    public ServerResponse updateadmitWfList(UpdateadmitWfDTO updateadmitWfDTO) {

        Wf wfList = new Wf();
        Date date = new Date();

        BeanCopier beanCopier = BeanCopier.create(UpdateadmitWfDTO.class,Wf.class,false);
        beanCopier.copy(updateadmitWfDTO,wfList,null);

        wfList.setRequestNumber(updateadmitWfDTO.requestNumber);// 申請番号
        wfList.setReqAppOrder(updateadmitWfDTO.reqAppOrder);// 申請・承認順
        wfList.setReqAppKbn(ConstCode.REQUEST_APPROVE_KBN.APPROVE);// 申請承認区分
        wfList.setWfStatusKbn(ConstCode.WKFL_STATUS_KBN.APPROVED);// ワークフロー状態区分
        wfList.setReqAppDatetime(date);// 承認日時
        wfList.setUpdateUser(updateadmitWfDTO.updateUser);//　更新者
        wfList.setUpdateDatetime(date);// 更新日時

        int result = wfMapper.updateByPrimaryKeyadmitWf(wfList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("ワークフローテーブルにデータ更新しました。");
        }

        return ServerResponse.createByErrorMessage("ワークフローテーブルにデータ更新失敗しました。");
    }

    // 最終承認
    @Override
    public ServerResponse updatelastadmitWfList(UpdatelastadmitWfDTO updatelastadmitWfDTO) {

        Wf wfList = new Wf();
        Date date = new Date();

        BeanCopier beanCopier = BeanCopier.create(UpdatelastadmitWfDTO.class,Wf.class,false);
        beanCopier.copy(updatelastadmitWfDTO,wfList,null);

        wfList.setRequestNumber(updatelastadmitWfDTO.requestNumber);// 申請番号
        wfList.setReqAppOrder(updatelastadmitWfDTO.reqAppOrder);// 申請・承認順
        wfList.setWfStatusKbn(ConstCode.WKFL_STATUS_KBN.APPROVEDCOMPLETE);// ワークフロー状態区分
        wfList.setReqAppDatetime(date);// 承認日時
        wfList.setUpdateUser(updatelastadmitWfDTO.updateUser);// 更新者
        wfList.setUpdateDatetime(date);// 更新日時

        int result = wfMapper.updateByPrimaryKeylastadmitWf(wfList);

        // 休日出勤データを検索する。
        List<WorkDayOffDTO> selectWorkDayOffList = workDayOffMapper.selectWorkDayOff(updatelastadmitWfDTO.companyCode, updatelastadmitWfDTO.requestNumber);

        // 最終承認後（休日出勤）の場合、代休残にテーブルを更新する。
        for (WorkDayOffDTO workDayOffDTO : selectWorkDayOffList) {
            if (workDayOffDTO.workTypeKbn.equals("021")){
                SubHoliday subHoliday = new SubHoliday();
                subHoliday.setCompanyCode(workDayOffDTO.companyCode);// 会社コード
                subHoliday.setUserId(workDayOffDTO.requestUserId);// ユーザーID
                subHoliday.setHolidayWorkDay(workDayOffDTO.scheduledWorkDay);// 休日出勤日
                subHoliday.setSubHolidayDays(workDayOffDTO.workTimeKbn.equals("010") ? Float.valueOf("1") : Float.valueOf("0.5"));// 日数
                subHoliday.setRequestNumber(workDayOffDTO.requestNumber);// 申請番号
                subHoliday.setRegistrationUser(workDayOffDTO.registrationUser);// 登録者
                subHoliday.setRegistrationDatetime(date);// 登録日時
                subHoliday.setUpdateUser(updatelastadmitWfDTO.updateUser);// 更新者
                subHoliday.setUpdateDatetime(date);// 更新日時

                if(subHolidayMapper.selectByPrimaryKey(workDayOffDTO.companyCode, workDayOffDTO.requestUserId, workDayOffDTO.scheduledWorkDay) == null)
                {
                    int resultSubHolidy = subHolidayMapper.insertSelective(subHoliday);

                    if (resultSubHolidy == 0) {
                        ServerResponse.createByError();
                    }
                }
            }
        }

        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("ワークフローテーブルにデータ更新しました。");
        }

        return ServerResponse.createByErrorMessage("ワークフローテーブルにデータ更新失敗しました。");
    }

    @Override
    public ServerResponse<List<SelectWfDTO>> selectWf(SelectWfDTO selectWfDTO, HttpServletRequest request, HttpServletResponse response) {

        selectWfDTO.requestTypeKbn = "%" + selectWfDTO.requestTypeKbn + "%";
        if (!"99".equals(selectWfDTO.wfStatusKbn)){
            selectWfDTO.wfStatusKbn = "%" + selectWfDTO.wfStatusKbn + "%";
        }

        selectWfDTO.startDate = StringUtils.isEmpty(selectWfDTO.startDate) ? "" : selectWfDTO.startDate;
        selectWfDTO.endDate = StringUtils.isEmpty(selectWfDTO.endDate) ? "" : selectWfDTO.endDate;
        selectWfDTO.userId = StringUtils.isEmpty(selectWfDTO.userId) ? "%%" : "%" + selectWfDTO.userId + "%";

        List<SelectWfDTO> wc = wfMapper.selectWf(selectWfDTO.companyCode,
                                                 selectWfDTO.reqAppUserId,
                                                 selectWfDTO.requestTypeKbn,
                                                 selectWfDTO.wfStatusKbn,
                                                 selectWfDTO.startDate,
                                                 selectWfDTO.endDate,
                                                 selectWfDTO.userId);

        return ServerResponse.createBySuccess(wc);
    }

    @Override
    public ServerResponse<String> deleteAllWf(WfDTO wfDTO, HttpServletRequest request, HttpServletResponse response) {
        // 会社コード
        String companyCode = wfDTO.getCompanyCode();
        // 申請番号
        String requestNumber = wfDTO.getRequestNumber();

        int deleteWf = wfMapper.deleteAllWf(companyCode,requestNumber);

        // 休日出勤データを検索する。
        List<WorkDayOffDTO> selectWorkDayOffList = workDayOffMapper.selectWorkDayOff(companyCode, requestNumber);

        for (WorkDayOffDTO workDayOffDTO : selectWorkDayOffList) {
            if (workDayOffDTO.workTypeKbn.equals("021")){
                subHolidayMapper.deleteByPrimaryKey(companyCode,workDayOffDTO.requestUserId,workDayOffDTO.scheduledWorkDay);
            }
        }

        if(deleteWf != ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("ワークフローテーブルのデータを削除しました。");
        }

        return ServerResponse.createByErrorMessage("ワークフローテーブルのデータを削除失敗しました。");
    }

    @Override
    public ServerResponse<String> deleteDataWf(List<RemandWfDTO> remandWfDTO, HttpServletRequest request, HttpServletResponse response) {

        if(remandWfDTO.size() >= ConstCode.NUM_1) {
            for(int i = 0; i < remandWfDTO.size(); i++){
                int deleteDataWf = wfMapper.deleteDataWf(remandWfDTO.get(i).companyCode,remandWfDTO.get(i).requestNumber,remandWfDTO.get(i).reqAppOrder);

                // 休日出勤データを検索する。
                List<WorkDayOffDTO> selectWorkDayOffList = workDayOffMapper.selectWorkDayOff(remandWfDTO.get(i).companyCode, remandWfDTO.get(i).requestNumber);

                for (WorkDayOffDTO workDayOffDTO : selectWorkDayOffList) {
                    if (workDayOffDTO.workTypeKbn.equals("021")){
                        subHolidayMapper.deleteByPrimaryKey(remandWfDTO.get(i).companyCode,workDayOffDTO.requestUserId,workDayOffDTO.scheduledWorkDay);
                    }
                }

                if(deleteDataWf != ConstCode.NUM_1) {
                    return ServerResponse.createBySuccessMessage("差戻以降のワークフローテーブルのデータの削除に失敗しました。");
                }
            }
            return ServerResponse.createByErrorMessage("差戻以降のワークフローデータを削除しました。");
        }else{
            return ServerResponse.createByErrorMessage("差戻以降のワークフローデータは存在しません。");
        }

    }
}
