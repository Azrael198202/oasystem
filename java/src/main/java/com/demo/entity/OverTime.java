package com.demo.entity;

import java.util.Date;

public class OverTime {
    private String companyCode;

    private String requestNumber;

    private String requestUserId;

    private String workTypeKbn;

    private String scheduledOvertimeDay;

    private Date startTime;

    private Date endTime;

    private Date estimatedWorkHours;

    private String overtimeReasonKbn;

    private String remark;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public OverTime(String companyCode, String requestNumber, String requestUserId, String workTypeKbn, String scheduledOvertimeDay, Date startTime, Date endTime, Date estimatedWorkHours, String overtimeReasonKbn, String remark, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.requestNumber = requestNumber;
        this.requestUserId = requestUserId;
        this.workTypeKbn = workTypeKbn;
        this.scheduledOvertimeDay = scheduledOvertimeDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.estimatedWorkHours = estimatedWorkHours;
        this.overtimeReasonKbn = overtimeReasonKbn;
        this.remark = remark;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public OverTime() {
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

    public String getScheduledOvertimeDay() {
        return scheduledOvertimeDay;
    }

    public void setScheduledOvertimeDay(String scheduledOvertimeDay) {
        this.scheduledOvertimeDay = scheduledOvertimeDay == null ? null : scheduledOvertimeDay.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEstimatedWorkHours() {
        return estimatedWorkHours;
    }

    public void setEstimatedWorkHours(Date estimatedWorkHours) {
        this.estimatedWorkHours = estimatedWorkHours;
    }

    public String getOvertimeReasonKbn() {
        return overtimeReasonKbn;
    }

    public void setOvertimeReasonKbn(String overtimeReasonKbn) {
        this.overtimeReasonKbn = overtimeReasonKbn == null ? null : overtimeReasonKbn.trim();
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