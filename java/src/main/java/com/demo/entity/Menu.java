package com.demo.entity;

import java.util.Date;

public class Menu {
    private String companyCode;

    private String menuNumber;

    private String permissionKbn;

    private String availableFlg;

    private String optionValue1;

    private String optionValue2;

    private String optionValue3;

    private String optionValue4;

    private String optionValue5;

    private String optionValue6;

    private String optionValue1Exp;

    private String optionValue2Exp;

    private String optionValue3Exp;

    private String optionValue4Exp;

    private String optionValue5Exp;

    private String optionValue6Exp;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public Menu(String companyCode, String menuNumber, String permissionKbn, String availableFlg, String optionValue1, String optionValue2, String optionValue3, String optionValue4, String optionValue5, String optionValue6, String optionValue1Exp, String optionValue2Exp, String optionValue3Exp, String optionValue4Exp, String optionValue5Exp, String optionValue6Exp, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.menuNumber = menuNumber;
        this.permissionKbn = permissionKbn;
        this.availableFlg = availableFlg;
        this.optionValue1 = optionValue1;
        this.optionValue2 = optionValue2;
        this.optionValue3 = optionValue3;
        this.optionValue4 = optionValue4;
        this.optionValue5 = optionValue5;
        this.optionValue6 = optionValue6;
        this.optionValue1Exp = optionValue1Exp;
        this.optionValue2Exp = optionValue2Exp;
        this.optionValue3Exp = optionValue3Exp;
        this.optionValue4Exp = optionValue4Exp;
        this.optionValue5Exp = optionValue5Exp;
        this.optionValue6Exp = optionValue6Exp;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public Menu() {
        super();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getMenuNumber() {
        return menuNumber;
    }

    public void setMenuNumber(String menuNumber) {
        this.menuNumber = menuNumber == null ? null : menuNumber.trim();
    }

    public String getPermissionKbn() {
        return permissionKbn;
    }

    public void setPermissionKbn(String permissionKbn) {
        this.permissionKbn = permissionKbn == null ? null : permissionKbn.trim();
    }

    public String getAvailableFlg() {
        return availableFlg;
    }

    public void setAvailableFlg(String availableFlg) {
        this.availableFlg = availableFlg == null ? null : availableFlg.trim();
    }

    public String getOptionValue1() {
        return optionValue1;
    }

    public void setOptionValue1(String optionValue1) {
        this.optionValue1 = optionValue1 == null ? null : optionValue1.trim();
    }

    public String getOptionValue2() {
        return optionValue2;
    }

    public void setOptionValue2(String optionValue2) {
        this.optionValue2 = optionValue2 == null ? null : optionValue2.trim();
    }

    public String getOptionValue3() {
        return optionValue3;
    }

    public void setOptionValue3(String optionValue3) {
        this.optionValue3 = optionValue3 == null ? null : optionValue3.trim();
    }

    public String getOptionValue4() {
        return optionValue4;
    }

    public void setOptionValue4(String optionValue4) {
        this.optionValue4 = optionValue4 == null ? null : optionValue4.trim();
    }

    public String getOptionValue5() {
        return optionValue5;
    }

    public void setOptionValue5(String optionValue5) {
        this.optionValue5 = optionValue5 == null ? null : optionValue5.trim();
    }

    public String getOptionValue6() {
        return optionValue6;
    }

    public void setOptionValue6(String optionValue6) {
        this.optionValue6 = optionValue6 == null ? null : optionValue6.trim();
    }

    public String getOptionValue1Exp() {
        return optionValue1Exp;
    }

    public void setOptionValue1Exp(String optionValue1Exp) {
        this.optionValue1Exp = optionValue1Exp == null ? null : optionValue1Exp.trim();
    }

    public String getOptionValue2Exp() {
        return optionValue2Exp;
    }

    public void setOptionValue2Exp(String optionValue2Exp) {
        this.optionValue2Exp = optionValue2Exp == null ? null : optionValue2Exp.trim();
    }

    public String getOptionValue3Exp() {
        return optionValue3Exp;
    }

    public void setOptionValue3Exp(String optionValue3Exp) {
        this.optionValue3Exp = optionValue3Exp == null ? null : optionValue3Exp.trim();
    }

    public String getOptionValue4Exp() {
        return optionValue4Exp;
    }

    public void setOptionValue4Exp(String optionValue4Exp) {
        this.optionValue4Exp = optionValue4Exp == null ? null : optionValue4Exp.trim();
    }

    public String getOptionValue5Exp() {
        return optionValue5Exp;
    }

    public void setOptionValue5Exp(String optionValue5Exp) {
        this.optionValue5Exp = optionValue5Exp == null ? null : optionValue5Exp.trim();
    }

    public String getOptionValue6Exp() {
        return optionValue6Exp;
    }

    public void setOptionValue6Exp(String optionValue6Exp) {
        this.optionValue6Exp = optionValue6Exp == null ? null : optionValue6Exp.trim();
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