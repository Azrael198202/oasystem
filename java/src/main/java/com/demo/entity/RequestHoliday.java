package com.demo.entity;

import java.util.Date;

public class RequestHoliday {
    private String companyCode;

    private String requestNumber;

    private Byte requestSubNumber;

    private String requestUserId;

    private String holidayTypeKbn;

    private String holidayPlanDay;

    private String holidayTimeKbn;

    private Float holidayDays;

    private String holidayReasonKbn;

    private String remark;

    private String workDayoffRequestNumber;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public RequestHoliday(String companyCode, String requestNumber, Byte requestSubNumber, String requestUserId, String holidayTypeKbn, String holidayPlanDay, String holidayTimeKbn, Float holidayDays, String holidayReasonKbn, String remark, String workDayoffRequestNumber, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.requestNumber = requestNumber;
        this.requestSubNumber = requestSubNumber;
        this.requestUserId = requestUserId;
        this.holidayTypeKbn = holidayTypeKbn;
        this.holidayPlanDay = holidayPlanDay;
        this.holidayTimeKbn = holidayTimeKbn;
        this.holidayDays = holidayDays;
        this.holidayReasonKbn = holidayReasonKbn;
        this.remark = remark;
        this.workDayoffRequestNumber = workDayoffRequestNumber;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public RequestHoliday() {
        super();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber == null ? null : requestNumber.trim();
    }

    public Byte getRequestSubNumber() {
        return requestSubNumber;
    }

    public void setRequestSubNumber(Byte requestSubNumber) {
        this.requestSubNumber = requestSubNumber;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId == null ? null : requestUserId.trim();
    }

    public String getHolidayTypeKbn() {
        return holidayTypeKbn;
    }

    public void setHolidayTypeKbn(String holidayTypeKbn) {
        this.holidayTypeKbn = holidayTypeKbn == null ? null : holidayTypeKbn.trim();
    }

    public String getHolidayPlanDay() {
        return holidayPlanDay;
    }

    public void setHolidayPlanDay(String holidayPlanDay) {
        this.holidayPlanDay = holidayPlanDay == null ? null : holidayPlanDay.trim();
    }

    public String getHolidayTimeKbn() {
        return holidayTimeKbn;
    }

    public void setHolidayTimeKbn(String holidayTimeKbn) {
        this.holidayTimeKbn = holidayTimeKbn == null ? null : holidayTimeKbn.trim();
    }

    public Float getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(Float holidayDays) {
        this.holidayDays = holidayDays;
    }

    public String getHolidayReasonKbn() {
        return holidayReasonKbn;
    }

    public void setHolidayReasonKbn(String holidayReasonKbn) {
        this.holidayReasonKbn = holidayReasonKbn == null ? null : holidayReasonKbn.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getWorkDayoffRequestNumber() {
        return workDayoffRequestNumber;
    }

    public void setWorkDayoffRequestNumber(String workDayoffRequestNumber) {
        this.workDayoffRequestNumber = workDayoffRequestNumber == null ? null : workDayoffRequestNumber.trim();
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