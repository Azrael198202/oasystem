package com.demo.entity;

import java.util.Date;

public class Notice {
    private String companyCode;

    private String noticeNumber;

    private String noticeCategory;

    private String noticePublicationStartDate;

    private String noticePublicationEndDate;
    
    private String noticeTitle;

    private String noticeContents;

    private Integer noticeNewPeriod;
    
    private String noticeAttachment1;

    private String noticeAttachment2;

    private String noticeAttachment3;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public Notice(String companyCode, String noticeNumber, String noticeCategory , String noticePublicationStartDate, String noticePublicationEndDate, String noticeTitle, Integer noticeNewPeriod, String noticeAttachment1, String noticeAttachment2, String noticeAttachment3, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime, String noticeContents) {
        this.companyCode = companyCode;
        this.noticeNumber = noticeNumber;
		this.noticeCategory = noticeCategory;
        this.noticePublicationStartDate = noticePublicationStartDate;
        this.noticePublicationEndDate = noticePublicationEndDate;
        this.noticeTitle = noticeTitle;
		this.noticeContents = noticeContents;
        this.noticeNewPeriod = noticeNewPeriod;
        this.noticeAttachment1 = noticeAttachment1;
        this.noticeAttachment2 = noticeAttachment2;
        this.noticeAttachment3 = noticeAttachment3;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public Notice() {
        super();
    }

    public String getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getNoticeNumber() {
        return noticeNumber;
    }
    public void setNoticeNumber(String noticeNumber) {
        this.noticeNumber = noticeNumber == null ? null : noticeNumber.trim();
    }

    public String getNoticeCategory(){ return noticeCategory; }
    public void setNoticeCategory(String noticeCategory) {
        this.noticeCategory = noticeCategory;
    }

    public void setNoticePublicationStartDate(String noticePublicationStartDate) {
        this.noticePublicationStartDate = noticePublicationStartDate;
    }

    public void setNoticePublicationEndDate(String noticePublicationEndDate) {
        this.noticePublicationEndDate = noticePublicationEndDate;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
    }

    public Integer getNoticeNewPeriod() {
        return noticeNewPeriod;
    }
    public void setNoticeNewPeriod(Integer noticeNewPeriod) {
        this.noticeNewPeriod = noticeNewPeriod;
    }

    public String getNoticeAttachment1() {
        return noticeAttachment1;
    }
    public void setNoticeAttachment1(String noticeAttachment1) {
        this.noticeAttachment1 = noticeAttachment1 == null ? null : noticeAttachment1.trim();
    }

    public String getNoticeAttachment2() {
        return noticeAttachment2;
    }
    public void setNoticeAttachment2(String noticeAttachment2) {
        this.noticeAttachment2 = noticeAttachment2 == null ? null : noticeAttachment2.trim();
    }

    public String getNoticeAttachment3() {
        return noticeAttachment3;
    }
    public void setNoticeAttachment3(String noticeAttachment3) {
        this.noticeAttachment3 = noticeAttachment3 == null ? null : noticeAttachment3.trim();
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

    public String getNoticeContents() {
        return noticeContents;
    }
    public void setNoticeContents(String noticeContents) {
        this.noticeContents = noticeContents == null ? null : noticeContents.trim();
    }
}