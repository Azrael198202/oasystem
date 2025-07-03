package com.demo.entity;

import java.util.Date;

public class SubHoliday {
    private String companyCode;

    private String userId;

    private String holidayWorkDay;

    private Float subHolidayDays;

    private String requestNumber;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public SubHoliday(String companyCode, String userId, String holidayWorkDay, Float subHolidayDays, String requestNumber, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.holidayWorkDay = holidayWorkDay;
        this.subHolidayDays = subHolidayDays;
        this.requestNumber = requestNumber;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public SubHoliday() {
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

    public String getHolidayWorkDay() {
        return holidayWorkDay;
    }

    public void setHolidayWorkDay(String holidayWorkDay) {
        this.holidayWorkDay = holidayWorkDay == null ? null : holidayWorkDay.trim();
    }

    public Float getSubHolidayDays() {
        return subHolidayDays;
    }

    public void setSubHolidayDays(Float subHolidayDays) {
        this.subHolidayDays = subHolidayDays;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber == null ? null : requestNumber.trim();
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