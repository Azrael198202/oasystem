package com.demo.entity;

import java.util.Date;

public class AtndApply {
    private String atndApplyNo;

    private String companyCode;

    private String userId;

    private String applyYm;

    private String applyKbn;

    private String applyLevel;

    private String applyFromTo;

    private String applyStatus;

    private String approveStatus;

    private String remark;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public AtndApply(String atndApplyNo, String companyCode, String userId, String applyYm, String applyKbn, String applyLevel, String applyFromTo, String applyStatus, String approveStatus, String remark, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.atndApplyNo = atndApplyNo;
        this.companyCode = companyCode;
        this.userId = userId;
        this.applyYm = applyYm;
        this.applyKbn = applyKbn;
        this.applyLevel = applyLevel;
        this.applyFromTo = applyFromTo;
        this.applyStatus = applyStatus;
        this.approveStatus = approveStatus;
        this.remark = remark;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public AtndApply() {
        super();
    }

    public String getAtndApplyNo() {
        return atndApplyNo;
    }

    public void setAtndApplyNo(String atndApplyNo) {
        this.atndApplyNo = atndApplyNo == null ? null : atndApplyNo.trim();
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

    public String getApplyYm() {
        return applyYm;
    }

    public void setApplyYm(String applyYm) {
        this.applyYm = applyYm == null ? null : applyYm.trim();
    }

    public String getApplyKbn() {
        return applyKbn;
    }

    public void setApplyKbn(String applyKbn) {
        this.applyKbn = applyKbn == null ? null : applyKbn.trim();
    }

    public String getApplyLevel() {
        return applyLevel;
    }

    public void setApplyLevel(String applyLevel) {
        this.applyLevel = applyLevel == null ? null : applyLevel.trim();
    }

    public String getApplyFromTo() {
        return applyFromTo;
    }

    public void setApplyFromTo(String applyFromTo) {
        this.applyFromTo = applyFromTo == null ? null : applyFromTo.trim();
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus == null ? null : approveStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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