package com.demo.entity;

import java.util.Date;

public class Wf {
    private String companyCode;

    private String requestNumber;

    private int reqAppOrder;

    private String reqAppUserId;

    private String reqAppKbn;

    private String wfStatusKbn;

    private String reqAppComment;

    private Date reqAppDatetime;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public Wf(String companyCode, String requestNumber, int reqAppOrder, String reqAppUserId, String reqAppKbn, String wfStatusKbn, String reqAppComment, Date reqAppDatetime, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.requestNumber = requestNumber;
        this.reqAppOrder = reqAppOrder;
        this.reqAppUserId = reqAppUserId;
        this.reqAppKbn = reqAppKbn;
        this.wfStatusKbn = wfStatusKbn;
        this.reqAppComment = reqAppComment;
        this.reqAppDatetime = reqAppDatetime;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public Wf() {
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

    public int getReqAppOrder() {
        return reqAppOrder;
    }

    public void setReqAppOrder(int reqAppOrder) {
        this.reqAppOrder = reqAppOrder;
    }

    public String getReqAppUserId() {
        return reqAppUserId;
    }

    public void setReqAppUserId(String reqAppUserId) {
        this.reqAppUserId = reqAppUserId == null ? null : reqAppUserId.trim();
    }

    public String getReqAppKbn() {
        return reqAppKbn;
    }

    public void setReqAppKbn(String reqAppKbn) {
        this.reqAppKbn = reqAppKbn == null ? null : reqAppKbn.trim();
    }

    public String getWfStatusKbn() {
        return wfStatusKbn;
    }

    public void setWfStatusKbn(String wfStatusKbn) {
        this.wfStatusKbn = wfStatusKbn == null ? null : wfStatusKbn.trim();
    }

    public String getReqAppComment() {
        return reqAppComment;
    }

    public void setReqAppComment(String reqAppComment) {
        this.reqAppComment = reqAppComment == null ? null : reqAppComment.trim();
    }

    public Date getReqAppDatetime() {
        return reqAppDatetime;
    }

    public void setReqAppDatetime(Date reqAppDatetime) {
        this.reqAppDatetime = reqAppDatetime;
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