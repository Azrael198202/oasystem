package com.demo.entity;

import java.util.Date;

public class AttendanceHistory {
    private String companyCode;

    private String userId;

    private String historyNo;

    private String workDate;

    private String workDateYm;

    private String workDateWeek;

    private String workKbn;

    private Date workStart1;

    private Date workEnd1;

    private Date workStart2;

    private Date workEnd2;

    private Date workStart3;

    private Date workEnd3;

    private Date workTime;

    private Date workBreakTime1;

    private Date workBreakTime2;

    private Date workBreakTime3;

    private Date workOverTime;

    private Date workMidnightTime;

    private Date workHoliTime;

    private Date workMidnightHoliTime;

    private Date workSumHoliTime;

    private Date workSumTime;

    private String remark;

    private String sendReportFlg;

    private Date workTcStart1;

    private Date workTcEnd1;

    private Date workTcStart2;

    private Date workTcEnd2;

    private Date workTcStart3;

    private Date workTcEnd3;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public AttendanceHistory(String companyCode, String userId, String historyNo, String workDate, String workDateYm, String workDateWeek, String workKbn, Date workStart1, Date workEnd1, Date workStart2, Date workEnd2, Date workStart3, Date workEnd3, Date workTime, Date workBreakTime1, Date workBreakTime2, Date workBreakTime3, Date workOverTime, Date workMidnightTime, Date workHoliTime, Date workMidnightHoliTime, Date workSumHoliTime, Date workSumTime, String remark, String sendReportFlg, Date workTcStart1, Date workTcEnd1, Date workTcStart2, Date workTcEnd2, Date workTcStart3, Date workTcEnd3, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.historyNo = historyNo;
        this.workDate = workDate;
        this.workDateYm = workDateYm;
        this.workDateWeek = workDateWeek;
        this.workKbn = workKbn;
        this.workStart1 = workStart1;
        this.workEnd1 = workEnd1;
        this.workStart2 = workStart2;
        this.workEnd2 = workEnd2;
        this.workStart3 = workStart3;
        this.workEnd3 = workEnd3;
        this.workTime = workTime;
        this.workBreakTime1 = workBreakTime1;
        this.workBreakTime2 = workBreakTime2;
        this.workBreakTime3 = workBreakTime3;
        this.workOverTime = workOverTime;
        this.workMidnightTime = workMidnightTime;
        this.workHoliTime = workHoliTime;
        this.workMidnightHoliTime = workMidnightHoliTime;
        this.workSumHoliTime = workSumHoliTime;
        this.workSumTime = workSumTime;
        this.remark = remark;
        this.sendReportFlg = sendReportFlg;
        this.workTcStart1 = workTcStart1;
        this.workTcEnd1 = workTcEnd1;
        this.workTcStart2 = workTcStart2;
        this.workTcEnd2 = workTcEnd2;
        this.workTcStart3 = workTcStart3;
        this.workTcEnd3 = workTcEnd3;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public AttendanceHistory() {
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

    public String getHistoryNo() {
        return historyNo;
    }

    public void setHistoryNo(String historyNo) {
        this.historyNo = historyNo == null ? null : historyNo.trim();
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate == null ? null : workDate.trim();
    }

    public String getWorkDateYm() {
        return workDateYm;
    }

    public void setWorkDateYm(String workDateYm) {
        this.workDateYm = workDateYm == null ? null : workDateYm.trim();
    }

    public String getWorkDateWeek() {
        return workDateWeek;
    }

    public void setWorkDateWeek(String workDateWeek) {
        this.workDateWeek = workDateWeek == null ? null : workDateWeek.trim();
    }

    public String getWorkKbn() {
        return workKbn;
    }

    public void setWorkKbn(String workKbn) {
        this.workKbn = workKbn == null ? null : workKbn.trim();
    }

    public Date getWorkStart1() {
        return workStart1;
    }

    public void setWorkStart1(Date workStart1) {
        this.workStart1 = workStart1;
    }

    public Date getWorkEnd1() {
        return workEnd1;
    }

    public void setWorkEnd1(Date workEnd1) {
        this.workEnd1 = workEnd1;
    }

    public Date getWorkStart2() {
        return workStart2;
    }

    public void setWorkStart2(Date workStart2) {
        this.workStart2 = workStart2;
    }

    public Date getWorkEnd2() {
        return workEnd2;
    }

    public void setWorkEnd2(Date workEnd2) {
        this.workEnd2 = workEnd2;
    }

    public Date getWorkStart3() {
        return workStart3;
    }

    public void setWorkStart3(Date workStart3) {
        this.workStart3 = workStart3;
    }

    public Date getWorkEnd3() {
        return workEnd3;
    }

    public void setWorkEnd3(Date workEnd3) {
        this.workEnd3 = workEnd3;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Date getWorkBreakTime1() {
        return workBreakTime1;
    }

    public void setWorkBreakTime1(Date workBreakTime1) {
        this.workBreakTime1 = workBreakTime1;
    }

    public Date getWorkBreakTime2() {
        return workBreakTime2;
    }

    public void setWorkBreakTime2(Date workBreakTime2) {
        this.workBreakTime2 = workBreakTime2;
    }

    public Date getWorkBreakTime3() {
        return workBreakTime3;
    }

    public void setWorkBreakTime3(Date workBreakTime3) {
        this.workBreakTime3 = workBreakTime3;
    }

    public Date getWorkOverTime() {
        return workOverTime;
    }

    public void setWorkOverTime(Date workOverTime) {
        this.workOverTime = workOverTime;
    }

    public Date getWorkMidnightTime() {
        return workMidnightTime;
    }

    public void setWorkMidnightTime(Date workMidnightTime) {
        this.workMidnightTime = workMidnightTime;
    }

    public Date getWorkHoliTime() {
        return workHoliTime;
    }

    public void setWorkHoliTime(Date workHoliTime) {
        this.workHoliTime = workHoliTime;
    }

    public Date getWorkMidnightHoliTime() {
        return workMidnightHoliTime;
    }

    public void setWorkMidnightHoliTime(Date workMidnightHoliTime) {
        this.workMidnightHoliTime = workMidnightHoliTime;
    }

    public Date getWorkSumHoliTime() {
        return workSumHoliTime;
    }

    public void setWorkSumHoliTime(Date workSumHoliTime) {
        this.workSumHoliTime = workSumHoliTime;
    }

    public Date getWorkSumTime() {
        return workSumTime;
    }

    public void setWorkSumTime(Date workSumTime) {
        this.workSumTime = workSumTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSendReportFlg() {
        return sendReportFlg;
    }

    public void setSendReportFlg(String sendReportFlg) {
        this.sendReportFlg = sendReportFlg == null ? null : sendReportFlg.trim();
    }

    public Date getWorkTcStart1() {
        return workTcStart1;
    }

    public void setWorkTcStart1(Date workTcStart1) {
        this.workTcStart1 = workTcStart1;
    }

    public Date getWorkTcEnd1() {
        return workTcEnd1;
    }

    public void setWorkTcEnd1(Date workTcEnd1) {
        this.workTcEnd1 = workTcEnd1;
    }

    public Date getWorkTcStart2() {
        return workTcStart2;
    }

    public void setWorkTcStart2(Date workTcStart2) {
        this.workTcStart2 = workTcStart2;
    }

    public Date getWorkTcEnd2() {
        return workTcEnd2;
    }

    public void setWorkTcEnd2(Date workTcEnd2) {
        this.workTcEnd2 = workTcEnd2;
    }

    public Date getWorkTcStart3() {
        return workTcStart3;
    }

    public void setWorkTcStart3(Date workTcStart3) {
        this.workTcStart3 = workTcStart3;
    }

    public Date getWorkTcEnd3() {
        return workTcEnd3;
    }

    public void setWorkTcEnd3(Date workTcEnd3) {
        this.workTcEnd3 = workTcEnd3;
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