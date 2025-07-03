package com.demo.entity;

import java.util.Date;

public class PaidHolidayCorrect {
    private String companyCode;

    private String userId;

    private String changeDate;

    private String subNumber;

    private String correctDate;

    private String correctKbn;

    private Float correctDays;

    private String correctReason;

    private String correctRemark;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public PaidHolidayCorrect(String companyCode, String userId, String changeDate, String subNumber, String correctDate, String correctKbn, Float correctDays, String correctReason, String correctRemark, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.changeDate = changeDate;
        this.subNumber = subNumber;
        this.correctDate = correctDate;
        this.correctKbn = correctKbn;
        this.correctDays = correctDays;
        this.correctReason = correctReason;
        this.correctRemark = correctRemark;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public PaidHolidayCorrect() {
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

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate == null ? null : changeDate.trim();
    }

    public String getSubNumber() {
        return subNumber;
    }

    public void setSubNumber(String subNumber) {
        this.subNumber = subNumber == null ? null : subNumber.trim();
    }

    public String getCorrectDate() {
        return correctDate;
    }

    public void setCorrectDate(String correctDate) {
        this.correctDate = correctDate == null ? null : correctDate.trim();
    }

    public String getCorrectKbn() {
        return correctKbn;
    }

    public void setCorrectKbn(String correctKbn) {
        this.correctKbn = correctKbn == null ? null : correctKbn.trim();
    }

    public Float getCorrectDays() {
        return correctDays;
    }

    public void setCorrectDays(Float correctDays) {
        this.correctDays = correctDays;
    }

    public String getCorrectReason() {
        return correctReason;
    }

    public void setCorrectReason(String correctReason) {
        this.correctReason = correctReason == null ? null : correctReason.trim();
    }

    public String getCorrectRemark() {
        return correctRemark;
    }

    public void setCorrectRemark(String correctRemark) {
        this.correctRemark = correctRemark == null ? null : correctRemark.trim();
    }

    public String getRegistrationUser() {
        return registrationUser;
    }

    public void setRegistrationUser(String registrationUser) {
        this.registrationUser = registrationUser == null ? null : registrationUser.trim();
    }

    public Date getRegistrationDatetime() {
        return registrationDatetime;
    }

    public void setRegistrationDatetime(Date registrationDatetime) {
        this.registrationDatetime = registrationDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}