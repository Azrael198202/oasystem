package com.demo.entity;

import java.util.Date;

public class PaidHoliday {
    private String companyCode;

    private String userId;

    private String dateYm;

    private String assignmentYear;

    private String assignmentDay;

    private Float paidHolidayRemaining;

    private String expiredYmd;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public PaidHoliday(String companyCode, String userId, String dateYm, String assignmentYear, String assignmentDay,Float paidHolidayRemaining, String expiredYmd, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.dateYm = dateYm;
        this.assignmentYear = assignmentYear;
        this.assignmentDay= assignmentDay;
        this.paidHolidayRemaining = paidHolidayRemaining;
        this.expiredYmd = expiredYmd;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public PaidHoliday() {
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

    public String getDateYm() {
        return dateYm;
    }

    public void setDateYm(String dateYm) {
        this.dateYm = dateYm == null ? null : dateYm.trim();
    }

    public String getAssignmentYear() {
        return assignmentYear;
    }

    public void setAssignmentYear(String assignmentYear) {
        this.assignmentYear = assignmentYear == null ? null : assignmentYear.trim();
    }
    public String getAssignmentDay() {
        return assignmentDay;
    }

    public void setAssignmentDay(String assignmentDay) {
        this.assignmentDay = assignmentDay == null ? null : assignmentDay.trim();
    }

    public Float getPaidHolidayRemaining() {
        return paidHolidayRemaining;
    }

    public void setPaidHolidayRemaining(Float paidHolidayRemaining) {
        this.paidHolidayRemaining = paidHolidayRemaining;
    }

    public String getExpiredYmd() {
        return expiredYmd;
    }

    public void setExpiredYmd(String expiredYmd) {
        this.expiredYmd = expiredYmd == null ? null : expiredYmd.trim();
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