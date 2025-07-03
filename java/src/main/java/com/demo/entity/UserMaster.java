package com.demo.entity;

import java.util.Date;

public class UserMaster {
    private String companyCode;

    private String userId;

    private String userName;

    private String startDate;

    private String passWord;

    private String email;

    private String accountKbn;

    private String permissionKbn;

    private String employmentKbn;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public UserMaster(String companyCode, String userId, String userName, String startDate, String passWord, String email, String accountKbn, String permissionKbn, String employmentKbn, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.userName = userName;
        this.startDate = startDate;
        this.passWord = passWord;
        this.email = email;
        this.accountKbn = accountKbn;
        this.permissionKbn = permissionKbn;
        this.employmentKbn = employmentKbn;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public UserMaster() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAccountKbn() {
        return accountKbn;
    }

    public void setAccountKbn(String accountKbn) {
        this.accountKbn = accountKbn == null ? null : accountKbn.trim();
    }

    public String getPermissionKbn() {
        return permissionKbn;
    }

    public void setPermissionKbn(String permissionKbn) {
        this.permissionKbn = permissionKbn == null ? null : permissionKbn.trim();
    }

    public String getEmploymentKbn() {
        return employmentKbn;
    }

    public void setEmploymentKbn(String employmentKbn) {
        this.employmentKbn = employmentKbn == null ? null : employmentKbn.trim();
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