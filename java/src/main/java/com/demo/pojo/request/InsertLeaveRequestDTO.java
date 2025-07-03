package com.demo.pojo.request;

import lombok.Data;

@Data
public class InsertLeaveRequestDTO {
    // 会社コード
    public String companyCode;
    // 申請番号
    public String requestNumber;
    // ユーザーID
    public String requestUserId;
    // 申請日
    public String requestDate;
    // 申請概要
    public String requestOverview;
    // 申請種類
    public String requestTypeKbn;
    // 申請状態
    public String requestStatusKbn;

}