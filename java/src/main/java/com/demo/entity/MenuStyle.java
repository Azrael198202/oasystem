package com.demo.entity;

import java.util.Date;

public class MenuStyle {
    private String companyCode;

    private String menuNumber;

    private String menuButtonName;

    private String buttonColor;

    private String fontColor;

    private Byte fontSize;

    private String menuDispUrl;

    private String menuIcon;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public MenuStyle(String companyCode, String menuNumber, String menuButtonName, String buttonColor, String fontColor, Byte fontSize, String menuDispUrl, String menuIcon, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.menuNumber = menuNumber;
        this.menuButtonName = menuButtonName;
        this.buttonColor = buttonColor;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.menuDispUrl = menuDispUrl;
        this.menuIcon = menuIcon;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public MenuStyle() {
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

    public String getMenuButtonName() {
        return menuButtonName;
    }

    public void setMenuButtonName(String menuButtonName) {
        this.menuButtonName = menuButtonName == null ? null : menuButtonName.trim();
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor == null ? null : buttonColor.trim();
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor == null ? null : fontColor.trim();
    }

    public Byte getFontSize() {
        return fontSize;
    }

    public void setFontSize(Byte fontSize) {
        this.fontSize = fontSize;
    }

    public String getMenuDispUrl() {
        return menuDispUrl;
    }

    public void setMenuDispUrl(String menuDispUrl) {
        this.menuDispUrl = menuDispUrl == null ? null : menuDispUrl.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
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