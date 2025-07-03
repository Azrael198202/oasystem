package com.demo.entity;

import java.util.Date;

public class MonthCloseHistory {
    private String companyCode;

    private String userId;

    private String closeMonth;

    private String processDay;

    private String processId;

    private String processResult;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public MonthCloseHistory(String companyCode, String userId, String closeMonth, String processDay, String processId, String processResult, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.closeMonth = closeMonth;
        this.processDay = processDay;
        this.processId = processId;
        this.processResult = processResult;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public MonthCloseHistory() {
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

    public String getCloseMonth() {
        return closeMonth;
    }

    public void setCloseMonth(String closeMonth) {
        this.closeMonth = closeMonth == null ? null : closeMonth.trim();
    }

    public String getCloseDay() {
        return processDay;
    }

    public void setProcessDay(String processDay) {
        this.processDay = processDay == null ? null : processDay.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult == null ? null : processResult.trim();
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