package com.demo.serviceImpl;

import com.demo.common.Const;
import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.RequestAttendanceMapper;
import com.demo.dao.WfMapper;
import com.demo.entity.RequestAttendance;
import com.demo.pojo.group.GroupDTO;
import com.demo.pojo.requestAttendance.*;
import com.demo.pojo.wf.WfCount;
import com.demo.pojo.wf.WfDTO;
import com.demo.service.IRequestAttendanceService;
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
public class RequestAttendanceServiceImpl implements IRequestAttendanceService {

    @Autowired
    RequestAttendanceMapper requestAttendanceMapper;

    @Override
    public ServerResponse<List<RequestAttendanceDTO>> getRequestAttendanceList(RequestAttendanceDTO requestAttendanceDTO, HttpServletRequest request, HttpServletResponse response) {

        List<RequestAttendanceDTO> requestAttendanceRpnDTOList = requestAttendanceMapper.selectAll(requestAttendanceDTO.getCompanyCode());

        return ServerResponse.createBySuccess(requestAttendanceRpnDTOList);
    }


    @Override
    public ServerResponse<RequestAttendance> getRequestAttendanceByList(RequestAttendanceByDTO requestAttendanceByDTO) {

        RequestAttendance requestAttendanceRpnDTOList = requestAttendanceMapper.selectByPrimaryKey(requestAttendanceByDTO.companyCode, requestAttendanceByDTO.userId, requestAttendanceByDTO.workDateYm);

        return ServerResponse.createBySuccess(requestAttendanceRpnDTOList);
    }

    @Override
    public ServerResponse<RequestAttendanceReqDTO> selectreqList(RequestAttendanceReqDTO requestAttendanceReqDTO) {

        RequestAttendanceReqDTO requestAttendanceRpnDTOList = requestAttendanceMapper.selectreq(requestAttendanceReqDTO.companyCode, requestAttendanceReqDTO.userId, requestAttendanceReqDTO.workDateYm);

        return ServerResponse.createBySuccess(requestAttendanceRpnDTOList);
    }



    @Override
    public ServerResponse updateNumRequestAttendanceList(UpdateNumRequestAttendanceDTO updateNumRequestAttendanceDTO) {

        RequestAttendance requestAttendanceList = new RequestAttendance();

        BeanCopier beanCopier = BeanCopier.create(UpdateNumRequestAttendanceDTO.class,RequestAttendance.class,false);
        beanCopier.copy(updateNumRequestAttendanceDTO,requestAttendanceList,null);

        int result = requestAttendanceMapper.updateByPrimaryKeyReqNum(requestAttendanceList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("勤怠申請テーブルに(二行目)データ更新しました。");
        }
        return ServerResponse.createByErrorMessage("勤怠申請テーブルに(二行目)データ更新失敗しました。");
    }
    @Override
    public ServerResponse updateRequestAttendanceList(UpdateRequestAttendanceDTO updateRequestAttendanceDTO) {

        RequestAttendance requestAttendanceList = new RequestAttendance();

        BeanCopier beanCopier = BeanCopier.create(UpdateRequestAttendanceDTO.class,RequestAttendance.class,false);
        beanCopier.copy(updateRequestAttendanceDTO,requestAttendanceList,null);
        //给申请番号赋个空值
        requestAttendanceList.setRequestNumber("");

        int result = requestAttendanceMapper.updateByPrimaryKeyReq(requestAttendanceList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("勤怠申請テーブルに(一行目)データ更新しました。");
        }
        return ServerResponse.createByErrorMessage("勤怠申請テーブルに(一行目)データ更新失敗しました。");
    }

    @Override
    public ServerResponse insertRequestAttendanceList(InsertRequestAttendanceDTO insertRequestAttendanceDTO) {

        RequestAttendance requestAttendanceList = new RequestAttendance();

        BeanCopier beanCopier = BeanCopier.create(InsertRequestAttendanceDTO.class,RequestAttendance.class,false);
        beanCopier.copy(insertRequestAttendanceDTO,requestAttendanceList,null);

        Date date = new Date();

        requestAttendanceList.setRegAccount(insertRequestAttendanceDTO.userId);
        requestAttendanceList.setUpdAccount(insertRequestAttendanceDTO.userId);
        requestAttendanceList.setRegTime(date);
        requestAttendanceList.setUpdTime(date);

        int result = requestAttendanceMapper.insert(requestAttendanceList);
        if(result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("勤怠申請テーブルにデータ登録しました。");
        }
        return ServerResponse.createByErrorMessage("勤怠申請テーブルにデータ登録失敗しました。");
    }

    @Override
    public ServerResponse<String> deleteRequestAttendanceList(RequestAttendanceDTO requestAttendanceDTO, HttpServletRequest request, HttpServletResponse response) {
        String companyCode = requestAttendanceDTO.getCompanyCode();

        String userId = requestAttendanceDTO.getUserId();

        String workDateYm = requestAttendanceDTO.getWorkDateYm();

        int deleteWf = requestAttendanceMapper.deleteByPrimaryKey(companyCode,userId,workDateYm);
        if(deleteWf == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("勤怠申請テーブルにデータ削除しました。");
        }
        return ServerResponse.createByErrorMessage("勤怠申請テーブルにデータ削除失敗しました。");
    }

    @Override
    public ServerResponse<GetStatusAttendanceDTO> getStatusAttendance(GetStatusAttendanceDTO getStatusAttendanceDTO) {

        GetStatusAttendanceDTO getStatusAttendance = requestAttendanceMapper.getStatus(getStatusAttendanceDTO.companyCode, getStatusAttendanceDTO.userId, getStatusAttendanceDTO.workDateYm);

        return ServerResponse.createBySuccess(getStatusAttendance);
    }
}

