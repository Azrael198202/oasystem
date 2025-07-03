package com.demo.entity;

import java.util.Date;

public class Request {
    private String companyCode;

    private String requestNumber;

    private String requestUserId;

    private String requestStatusKbn;

    private String requestDate;

    private String requestTypeKbn;

    private String requestOverview;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public Request(String companyCode, String requestNumber, String requestUserId, String requestStatusKbn, String requestDate, String requestTypeKbn, String requestOverview, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.requestNumber = requestNumber;
        this.requestUserId = requestUserId;
        this.requestStatusKbn = requestStatusKbn;
        this.requestDate = requestDate;
        this.requestTypeKbn = requestTypeKbn;
        this.requestOverview = requestOverview;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public Request() {
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

    public String getRequestStatusKbn() {
        return requestStatusKbn;
    }

    public void setRequestStatusKbn(String requestStatusKbn) {
        this.requestStatusKbn = requestStatusKbn == null ? null : requestStatusKbn.trim();
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate == null ? null : requestDate.trim();
    }

    public String getRequestTypeKbn() {
        return requestTypeKbn;
    }

    public void setRequestTypeKbn(String requestTypeKbn) {
        this.requestTypeKbn = requestTypeKbn == null ? null : requestTypeKbn.trim();
    }

    public String getRequestOverview() {
        return requestOverview;
    }

    public void setRequestOverview(String requestOverview) {
        this.requestOverview = requestOverview == null ? null : requestOverview.trim();
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