package com.demo.entity;

import java.util.Date;

public class Category {
    private String companyCode;

    private String categoryClassNumber;

    private String categoryKbn;

    private String categoryKbnName;

    private String categoryKbnAbbreviation;

    private String categoryKbnOption1;

    private String categoryKbnOption2;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public Category(String companyCode, String categoryClassNumber, String categoryKbn, String categoryKbnName, String categoryKbnAbbreviation, String categoryKbnOption1, String categoryKbnOption2, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.categoryClassNumber = categoryClassNumber;
        this.categoryKbn = categoryKbn;
        this.categoryKbnName = categoryKbnName;
        this.categoryKbnAbbreviation = categoryKbnAbbreviation;
        this.categoryKbnOption1 = categoryKbnOption1;
        this.categoryKbnOption2 = categoryKbnOption2;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public Category() {
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

    public String getCategoryKbn() {
        return categoryKbn;
    }

    public void setCategoryKbn(String categoryKbn) {
        this.categoryKbn = categoryKbn == null ? null : categoryKbn.trim();
    }

    public String getCategoryKbnName() {
        return categoryKbnName;
    }

    public void setCategoryKbnName(String categoryKbnName) {
        this.categoryKbnName = categoryKbnName == null ? null : categoryKbnName.trim();
    }

    public String getCategoryKbnAbbreviation() {
        return categoryKbnAbbreviation;
    }

    public void setCategoryKbnAbbreviation(String categoryKbnAbbreviation) {
        this.categoryKbnAbbreviation = categoryKbnAbbreviation == null ? null : categoryKbnAbbreviation.trim();
    }

    public String getCategoryKbnOption1() {
        return categoryKbnOption1;
    }

    public void setCategoryKbnOption1(String categoryKbnOption1) {
        this.categoryKbnOption1 = categoryKbnOption1 == null ? null : categoryKbnOption1.trim();
    }

    public String getCategoryKbnOption2() {
        return categoryKbnOption2;
    }

    public void setCategoryKbnOption2(String categoryKbnOption2) {
        this.categoryKbnOption2 = categoryKbnOption2 == null ? null : categoryKbnOption2.trim();
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