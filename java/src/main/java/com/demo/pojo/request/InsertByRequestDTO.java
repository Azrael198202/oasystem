package com.demo.pojo.request;

import lombok.Data;

@Data
public class InsertByRequestDTO {
    // 会社コード
    public String companyCode;
    // 申請番号
    public String requestNumber;

    public String requestOverview;

    public String userName;

    public String userId;
}
