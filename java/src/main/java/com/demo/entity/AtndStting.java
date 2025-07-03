package com.demo.entity;

import java.util.Date;

public class AtndStting {
    private String companyCode;

    private String userId;

    private String applicationDate;

    private Date workPrescribedTime;

    private Byte workEveryTime;

    private Date workStartTime;

    private Date workEndTime;

    private Date workBreakStart1;

    private Date workBreakEnd1;

    private Date workBreakStart2;

    private Date workBreakEnd2;

    private Date workBreakStart3;

    private Date workBreakEnd3;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public AtndStting(String companyCode, String userId, String applicationDate, Date workPrescribedTime, Byte workEveryTime, Date workStartTime, Date workEndTime, Date workBreakStart1, Date workBreakEnd1, Date workBreakStart2, Date workBreakEnd2, Date workBreakStart3, Date workBreakEnd3, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.applicationDate = applicationDate;
        this.workPrescribedTime = workPrescribedTime;
        this.workEveryTime = workEveryTime;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.workBreakStart1 = workBreakStart1;
        this.workBreakEnd1 = workBreakEnd1;
        this.workBreakStart2 = workBreakStart2;
        this.workBreakEnd2 = workBreakEnd2;
        this.workBreakStart3 = workBreakStart3;
        this.workBreakEnd3 = workBreakEnd3;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public AtndStting() {
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

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate == null ? null : applicationDate.trim();
    }

    public Date getWorkPrescribedTime() {
        return workPrescribedTime;
    }

    public void setWorkPrescribedTime(Date workPrescribedTime) {
        this.workPrescribedTime = workPrescribedTime;
    }

    public Byte getWorkEveryTime() {
        return workEveryTime;
    }

    public void setWorkEveryTime(Byte workEveryTime) {
        this.workEveryTime = workEveryTime;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    public Date getWorkBreakStart1() {
        return workBreakStart1;
    }

    public void setWorkBreakStart1(Date workBreakStart1) {
        this.workBreakStart1 = workBreakStart1;
    }

    public Date getWorkBreakEnd1() {
        return workBreakEnd1;
    }

    public void setWorkBreakEnd1(Date workBreakEnd1) {
        this.workBreakEnd1 = workBreakEnd1;
    }

    public Date getWorkBreakStart2() {
        return workBreakStart2;
    }

    public void setWorkBreakStart2(Date workBreakStart2) {
        this.workBreakStart2 = workBreakStart2;
    }

    public Date getWorkBreakEnd2() {
        return workBreakEnd2;
    }

    public void setWorkBreakEnd2(Date workBreakEnd2) {
        this.workBreakEnd2 = workBreakEnd2;
    }

    public Date getWorkBreakStart3() {
        return workBreakStart3;
    }

    public void setWorkBreakStart3(Date workBreakStart3) {
        this.workBreakStart3 = workBreakStart3;
    }

    public Date getWorkBreakEnd3() {
        return workBreakEnd3;
    }

    public void setWorkBreakEnd3(Date workBreakEnd3) {
        this.workBreakEnd3 = workBreakEnd3;
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