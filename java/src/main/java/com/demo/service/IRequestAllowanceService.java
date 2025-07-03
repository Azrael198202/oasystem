package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.requestAllowance.GetApplyYmDTO;
import com.demo.pojo.requestAllowance.QualificationAllowanceDTO;
import com.demo.pojo.requestAllowance.RequestAllowanceDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
public interface IRequestAllowanceService {
    // 手当申請：詳細処理
    ServerResponse<List<RequestAllowanceDTO>> getRequestAllowance(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response);
    // 手当申請：レコード削除処理
    ServerResponse<String> deleteRequestAllowance(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response);
    // 手当申請：出張,リーダー手当申請の保存処理
    ServerResponse<String> insertAllowance(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response);
    // 手当申請：参照処理
    ServerResponse<List<RequestAllowanceDTO>> searchReAllowanceMonthData(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response);
    // 手当申請：年月取得
    ServerResponse<List<GetApplyYmDTO>> searchMonth(GetApplyYmDTO getApplyYmDTO, HttpServletRequest request, HttpServletResponse response);
    // 手当申請：資格手当申請の保存処理
    ServerResponse<String> insertQualificationAllowance(QualificationAllowanceDTO qualificationAllowanceDTO, HttpServletRequest request, HttpServletResponse response);
    // 手当申請：資格手当申請のチェック処理
    ServerResponse<String> qualificationAllowanceCheck(QualificationAllowanceDTO qualificationAllowanceDTO, HttpServletRequest request, HttpServletResponse response);
    // 手当申請：慶弔見舞金申請の保存処理
    ServerResponse<String> insertCongratulatoryOrCondolencePayments(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response);
}
