package com.demo.entity;

import java.util.Date;

public class WorkDayOff {
    private String companyCode;

    private String requestNumber;

    private String requestUserId;

    private String workTypeKbn;

    private String scheduledWorkDay;

    private String workTimeKbn;

    private String workReasonKbn;

    private String remark;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public WorkDayOff(String companyCode, String requestNumber, String requestUserId, String workTypeKbn, String scheduledWorkDay, String workTimeKbn, String workReasonKbn, String remark, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.requestNumber = requestNumber;
        this.requestUserId = requestUserId;
        this.workTypeKbn = workTypeKbn;
        this.scheduledWorkDay = scheduledWorkDay;
        this.workTimeKbn = workTimeKbn;
        this.workReasonKbn = workReasonKbn;
        this.remark = remark;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public WorkDayOff() {
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

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId == null ? null : requestUserId.trim();
    }

    public String getWorkTypeKbn() {
        return workTypeKbn;
    }

    public void setWorkTypeKbn(String workTypeKbn) {
        this.workTypeKbn = workTypeKbn == null ? null : workTypeKbn.trim();
    }

    public String getScheduledWorkDay() {
        return scheduledWorkDay;
    }

    public void setScheduledWorkDay(String scheduledWorkDay) {
        this.scheduledWorkDay = scheduledWorkDay == null ? null : scheduledWorkDay.trim();
    }

    public String getWorkTimeKbn() {
        return workTimeKbn;
    }

    public void setWorkTimeKbn(String workTimeKbn) {
        this.workTimeKbn = workTimeKbn == null ? null : workTimeKbn.trim();
    }

    public String getWorkReasonKbn() {
        return workReasonKbn;
    }

    public void setWorkReasonKbn(String workReasonKbn) {
        this.workReasonKbn = workReasonKbn == null ? null : workReasonKbn.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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