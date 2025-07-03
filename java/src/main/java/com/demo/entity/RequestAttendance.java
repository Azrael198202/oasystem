package com.demo.entity;

import java.util.Date;

public class RequestAttendance {
    private String companyCode;

    private String userId;

    private String workDateYm;

    private String requestNumber;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public RequestAttendance(String companyCode, String userId, String workDateYm, String requestNumber, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.workDateYm = workDateYm;
        this.requestNumber = requestNumber;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public RequestAttendance() {
        super();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getWorkDateYm() {
        return workDateYm;
    }

    public void setWorkDateYm(String workDateYm) {
        this.workDateYm = workDateYm == null ? null : workDateYm.trim();
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber == null ? null : requestNumber.trim();
    }

    public String getRegAccount() {
        return regAccount;
    }

    public void setRegAccount(String regAccount) {
        this.regAccount = regAccount == null ? null : regAccount.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getUpdAccount() {
        return updAccount;
    }

    public void setUpdAccount(String updAccount) {
        this.updAccount = updAccount == null ? null : updAccount.trim();
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }
}