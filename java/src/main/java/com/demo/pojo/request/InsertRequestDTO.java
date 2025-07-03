package com.demo.pojo.request;

import lombok.Data;

@Data
public class InsertRequestDTO {
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
}
