package com.demo.entity;

import java.util.Date;

public class ResumeDetail {
    private String companyCode;

    private String userId;

    private String resumeVerKbn;

    private String resumeSeqNo;

    private String developPeriodFrom;

    private String developPeriodTo;

    private String developSummarySys;

    private String developSummarySry;

    private String developSummaryCts;

    private String developSummaryMbs;

    private String developLanguage1;

    private String developLanguage2;

    private String developLanguage3;

    private String developDb1;

    private String developDb2;

    private String developDb3;

    private String developOs1;

    private String developOs2;

    private String developOs3;

    private String responsible;

    private String requirementsFlg;

    private String basicFlg;

    private String specificFlg;

    private String codingFlg;

    private String unitTestFlg;

    private String interfaceTestFlg;

    private String systemTestFlg;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public ResumeDetail(String companyCode, String userId, String resumeVerKbn, String resumeSeqNo, String developPeriodFrom, String developPeriodTo, String developSummarySys, String developSummarySry, String developSummaryCts, String developSummaryMbs, String developLanguage1, String developLanguage2, String developLanguage3, String developDb1, String developDb2, String developDb3, String developOs1, String developOs2, String developOs3, String responsible, String requirementsFlg, String basicFlg, String specificFlg, String codingFlg, String unitTestFlg, String interfaceTestFlg, String systemTestFlg, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.resumeVerKbn = resumeVerKbn;
        this.resumeSeqNo = resumeSeqNo;
        this.developPeriodFrom = developPeriodFrom;
        this.developPeriodTo = developPeriodTo;
        this.developSummarySys = developSummarySys;
        this.developSummarySry = developSummarySry;
        this.developSummaryCts = developSummaryCts;
        this.developSummaryMbs = developSummaryMbs;
        this.developLanguage1 = developLanguage1;
        this.developLanguage2 = developLanguage2;
        this.developLanguage3 = developLanguage3;
        this.developDb1 = developDb1;
        this.developDb2 = developDb2;
        this.developDb3 = developDb3;
        this.developOs1 = developOs1;
        this.developOs2 = developOs2;
        this.developOs3 = developOs3;
        this.responsible = responsible;
        this.requirementsFlg = requirementsFlg;
        this.basicFlg = basicFlg;
        this.specificFlg = specificFlg;
        this.codingFlg = codingFlg;
        this.unitTestFlg = unitTestFlg;
        this.interfaceTestFlg = interfaceTestFlg;
        this.systemTestFlg = systemTestFlg;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public ResumeDetail() {
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

    public String getResumeVerKbn() {
        return resumeVerKbn;
    }

    public void setResumeVerKbn(String resumeVerKbn) {
        this.resumeVerKbn = resumeVerKbn == null ? null : resumeVerKbn.trim();
    }

    public String getResumeSeqNo() {
        return resumeSeqNo;
    }

    public void setResumeSeqNo(String resumeSeqNo) {
        this.resumeSeqNo = resumeSeqNo == null ? null : resumeSeqNo.trim();
    }

    public String getDevelopPeriodFrom() {
        return developPeriodFrom;
    }

    public void setDevelopPeriodFrom(String developPeriodFrom) {
        this.developPeriodFrom = developPeriodFrom == null ? null : developPeriodFrom.trim();
    }

    public String getDevelopPeriodTo() {
        return developPeriodTo;
    }

    public void setDevelopPeriodTo(String developPeriodTo) {
        this.developPeriodTo = developPeriodTo == null ? null : developPeriodTo.trim();
    }

    public String getDevelopSummarySys() {
        return developSummarySys;
    }

    public void setDevelopSummarySys(String developSummarySys) {
        this.developSummarySys = developSummarySys == null ? null : developSummarySys.trim();
    }

    public String getDevelopSummarySry() {
        return developSummarySry;
    }

    public void setDevelopSummarySry(String developSummarySry) {
        this.developSummarySry = developSummarySry == null ? null : developSummarySry.trim();
    }

    public String getDevelopSummaryCts() {
        return developSummaryCts;
    }

    public void setDevelopSummaryCts(String developSummaryCts) {
        this.developSummaryCts = developSummaryCts == null ? null : developSummaryCts.trim();
    }

    public String getDevelopSummaryMbs() {
        return developSummaryMbs;
    }

    public void setDevelopSummaryMbs(String developSummaryMbs) {
        this.developSummaryMbs = developSummaryMbs == null ? null : developSummaryMbs.trim();
    }

    public String getDevelopLanguage1() {
        return developLanguage1;
    }

    public void setDevelopLanguage1(String developLanguage1) {
        this.developLanguage1 = developLanguage1 == null ? null : developLanguage1.trim();
    }

    public String getDevelopLanguage2() {
        return developLanguage2;
    }

    public void setDevelopLanguage2(String developLanguage2) {
        this.developLanguage2 = developLanguage2 == null ? null : developLanguage2.trim();
    }

    public String getDevelopLanguage3() {
        return developLanguage3;
    }

    public void setDevelopLanguage3(String developLanguage3) {
        this.developLanguage3 = developLanguage3 == null ? null : developLanguage3.trim();
    }

    public String getDevelopDb1() {
        return developDb1;
    }

    public void setDevelopDb1(String developDb1) {
        this.developDb1 = developDb1 == null ? null : developDb1.trim();
    }

    public String getDevelopDb2() {
        return developDb2;
    }

    public void setDevelopDb2(String developDb2) {
        this.developDb2 = developDb2 == null ? null : developDb2.trim();
    }

    public String getDevelopDb3() {
        return developDb3;
    }

    public void setDevelopDb3(String developDb3) {
        this.developDb3 = developDb3 == null ? null : developDb3.trim();
    }

    public String getDevelopOs1() {
        return developOs1;
    }

    public void setDevelopOs1(String developOs1) {
        this.developOs1 = developOs1 == null ? null : developOs1.trim();
    }

    public String getDevelopOs2() {
        return developOs2;
    }

    public void setDevelopOs2(String developOs2) {
        this.developOs2 = developOs2 == null ? null : developOs2.trim();
    }

    public String getDevelopOs3() {
        return developOs3;
    }

    public void setDevelopOs3(String developOs3) {
        this.developOs3 = developOs3 == null ? null : developOs3.trim();
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible == null ? null : responsible.trim();
    }

    public String getRequirementsFlg() {
        return requirementsFlg;
    }

    public void setRequirementsFlg(String requirementsFlg) {
        this.requirementsFlg = requirementsFlg == null ? null : requirementsFlg.trim();
    }

    public String getBasicFlg() {
        return basicFlg;
    }

    public void setBasicFlg(String basicFlg) {
        this.basicFlg = basicFlg == null ? null : basicFlg.trim();
    }

    public String getSpecificFlg() {
        return specificFlg;
    }

    public void setSpecificFlg(String specificFlg) {
        this.specificFlg = specificFlg == null ? null : specificFlg.trim();
    }

    public String getCodingFlg() {
        return codingFlg;
    }

    public void setCodingFlg(String codingFlg) {
        this.codingFlg = codingFlg == null ? null : codingFlg.trim();
    }

    public String getUnitTestFlg() {
        return unitTestFlg;
    }

    public void setUnitTestFlg(String unitTestFlg) {
        this.unitTestFlg = unitTestFlg == null ? null : unitTestFlg.trim();
    }

    public String getInterfaceTestFlg() {
        return interfaceTestFlg;
    }

    public void setInterfaceTestFlg(String interfaceTestFlg) {
        this.interfaceTestFlg = interfaceTestFlg == null ? null : interfaceTestFlg.trim();
    }

    public String getSystemTestFlg() {
        return systemTestFlg;
    }

    public void setSystemTestFlg(String systemTestFlg) {
        this.systemTestFlg = systemTestFlg == null ? null : systemTestFlg.trim();
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