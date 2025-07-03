package com.demo.entity;

import java.util.Date;

public class Company {
    private String companyCode;

    private String companyName;

    private String companyNameSh;

    private String companyTel;

    private String companyZip;

    private String addressPrefecture;

    private String addressMunicipality;

    private String addressTown;

    private String addressDetail;

    private String companyUrl;

    private String companyLogo;

    private String companyImage;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public Company(String companyCode, String companyName, String companyNameSh, String companyTel, String companyZip, String addressPrefecture, String addressMunicipality, String addressTown, String addressDetail, String companyUrl, String companyLogo, String companyImage, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.companyNameSh = companyNameSh;
        this.companyTel = companyTel;
        this.companyZip = companyZip;
        this.addressPrefecture = addressPrefecture;
        this.addressMunicipality = addressMunicipality;
        this.addressTown = addressTown;
        this.addressDetail = addressDetail;
        this.companyUrl = companyUrl;
        this.companyLogo = companyLogo;
        this.companyImage = companyImage;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public Company() {
        super();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyNameSh() {
        return companyNameSh;
    }

    public void setCompanyNameSh(String companyNameSh) {
        this.companyNameSh = companyNameSh == null ? null : companyNameSh.trim();
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel == null ? null : companyTel.trim();
    }

    public String getCompanyZip() {
        return companyZip;
    }

    public void setCompanyZip(String companyZip) {
        this.companyZip = companyZip == null ? null : companyZip.trim();
    }

    public String getAddressPrefecture() {
        return addressPrefecture;
    }

    public void setAddressPrefecture(String addressPrefecture) {
        this.addressPrefecture = addressPrefecture == null ? null : addressPrefecture.trim();
    }

    public String getAddressMunicipality() {
        return addressMunicipality;
    }

    public void setAddressMunicipality(String addressMunicipality) {
        this.addressMunicipality = addressMunicipality == null ? null : addressMunicipality.trim();
    }

    public String getAddressTown() {
        return addressTown;
    }

    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown == null ? null : addressTown.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl == null ? null : companyUrl.trim();
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo == null ? null : companyLogo.trim();
    }

    public String getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(String companyImage) {
        this.companyImage = companyImage == null ? null : companyImage.trim();
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