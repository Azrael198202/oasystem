package com.demo.entity;

import java.util.Date;

public class CategoryDetail {
    private String companyCode;

    private String categoryClassNumber;

    private String categoryClassName;

    private String categorySystemFlg;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    private String categoryDescription;

    public CategoryDetail(String companyCode, String categoryClassNumber, String categoryClassName, String categorySystemFlg, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.categoryClassNumber = categoryClassNumber;
        this.categoryClassName = categoryClassName;
        this.categorySystemFlg = categorySystemFlg;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public CategoryDetail(String companyCode, String categoryClassNumber, String categoryClassName, String categorySystemFlg, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime, String categoryDescription) {
        this.companyCode = companyCode;
        this.categoryClassNumber = categoryClassNumber;
        this.categoryClassName = categoryClassName;
        this.categorySystemFlg = categorySystemFlg;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
        this.categoryDescription = categoryDescription;
    }

    public CategoryDetail() {
        super();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCategoryClassNumber() {
        return categoryClassNumber;
    }

    public void setCategoryClassNumber(String categoryClassNumber) {
        this.categoryClassNumber = categoryClassNumber == null ? null : categoryClassNumber.trim();
    }

    public String getCategoryClassName() {
        return categoryClassName;
    }

    public void setCategoryClassName(String categoryClassName) {
        this.categoryClassName = categoryClassName == null ? null : categoryClassName.trim();
    }

    public String getCategorySystemFlg() {
        return categorySystemFlg;
    }

    public void setCategorySystemFlg(String categorySystemFlg) {
        this.categorySystemFlg = categorySystemFlg == null ? null : categorySystemFlg.trim();
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

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription == null ? null : categoryDescription.trim();
    }
}