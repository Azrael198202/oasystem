package com.demo.entity;

public class PaidHolidayContentsUpdate {
    private String companyCode;

    private String userId;

    private String changeDate;

    private String subNumber;

    private String dateYm;

    private String assignmentYear;

    private Float updateDays;

    public PaidHolidayContentsUpdate(String companyCode, String userId, String changeDate, String subNumber, String dateYm, String assignmentYear, Float updateDays) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.changeDate = changeDate;
        this.subNumber = subNumber;
        this.dateYm = dateYm;
        this.assignmentYear = assignmentYear;
        this.updateDays = updateDays;
    }

    public PaidHolidayContentsUpdate() {
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

    public Float getUpdateDays() {
        return updateDays;
    }

    public void setUpdateDays(Float updateDays) {
        this.updateDays = updateDays;
    }
}