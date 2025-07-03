package com.demo.pojo.wf;

import lombok.Data;

import java.util.Date;

@Data
public class WfDTO {

    public String companyCode;
    public String requestNumber;
    public Byte reqAppOrder;
    public String reqAppUserId;
    public String reqAppKbn;
    public String wfStatusKbn;
    public String reqAppComment;
    public String reqAppDatetime;
    public String registrationUser;
    public String registrationDatetime;
    public String updateUser;
    public String updateDatetime;
    public String userName;
    public String categoryKbnName;
}
