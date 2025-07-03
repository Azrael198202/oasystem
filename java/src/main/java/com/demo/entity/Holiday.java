package com.demo.entity;

import java.util.Date;

public class Holiday {
    private Integer id;

    private String companyCode;

    private String holidayDate;

    private String holidayYear;

    private String holidayMonth;

    private String holidayDay;

    private String holidayName;

    private String holidayRemark;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public Holiday(Integer id, String companyCode, String holidayDate, String holidayYear, String holidayMonth, String holidayDay, String holidayName, String holidayRemark, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.id = id;
        this.companyCode = companyCode;
        this.holidayDate = holidayDate;
        this.holidayYear = holidayYear;
        this.holidayMonth = holidayMonth;
        this.holidayDay = holidayDay;
        this.holidayName = holidayName;
        this.holidayRemark = holidayRemark;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public Holiday() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate == null ? null : holidayDate.trim();
    }

    public String getHolidayYear() {
        return holidayYear;
    }

    public void setHolidayYear(String holidayYear) {
        this.holidayYear = holidayYear == null ? null : holidayYear.trim();
    }

    public String getHolidayMonth() {
        return holidayMonth;
    }

    public void setHolidayMonth(String holidayMonth) {
        this.holidayMonth = holidayMonth == null ? null : holidayMonth.trim();
    }

    public String getHolidayDay() {
        return holidayDay;
    }

    public void setHolidayDay(String holidayDay) {
        this.holidayDay = holidayDay == null ? null : holidayDay.trim();
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName == null ? null : holidayName.trim();
    }

    public String getHolidayRemark() {
        return holidayRemark;
    }

    public void setHolidayRemark(String holidayRemark) {
        this.holidayRemark = holidayRemark == null ? null : holidayRemark.trim();
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }

    public String getRegAccount() {
        return regAccount;
    }

    public void setRegAccount(String regAccount) {
        this.regAccount = regAccount == null ? null : regAccount.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getUpdAccount() {
        return updAccount;
    }

    public void setUpdAccount(String updAccount) {
        this.updAccount = updAccount == null ? null : updAccount.trim();
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }
}