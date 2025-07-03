package com.demo.pojo.user;

import lombok.Data;

import java.io.Serializable;


@Data
public class UsermailDTO{

    // 会社コード
    public String companyCode;
    public String userId;
    //承認者
    public String userAppId;
    // 申請名前
    public String userName;
    //承認者
    public String appName;
    //メール区分
    public String reqMailKbn;
    //申請種類区分名
    public String appMailKbn;

    public String reqAppComment;

    public String requestOverview;

    public String requestNumber;


}
