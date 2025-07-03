package com.demo.entity;

import java.util.Date;

public class Resume {
    private String companyCode;

    private String userId;

    private String resumeVerKbn;

    private String nearestStation;

    private String graduationYm;

    private String degree;

    private String schoolName;

    private String major;

    private String certification1;

    private String certification2;

    private String certification3;

    private String certification4;

    private String certification5;

    private String certification6;

    private Float experienceYears;

    private String responsible;

    private String lgJpBusinessCon;

    private String lgJpDailyCon;

    private String lgJpReading;

    private String lgJpListening;

    private String lgJpWritting;

    private String lgEnBusinessCon;

    private String lgEnDailyCon;

    private String lgEnReading;

    private String lgEnListening;

    private String lgEnWritting;

    private String tcOsWindows;

    private String tcOsUnix;

    private String tcOsSolaris;

    private String tcOsHpux;

    private String tcOsAix;

    private String tcOsLinux;

    private String tcOsDos;

    private String tcOsMac;

    private String tcPgCobol;

    private String tcPgJava;

    private String tcPgJsp;

    private String tcPgC;

    private String tcPgVb;

    private String tcPgVc;

    private String tcPgSql;

    private String tcPgNet;

    private String tcPgPerl;

    private String tcPgXml;

    private String tcDbOracle;

    private String tcDbHirdb;

    private String tcDbSqlserver;

    private String tcDbDb2;

    private String tcDbMysql;

    private String tcDbPostgresql;

    private String tcDbAccess;

    private String tcEtcWeblogic;

    private String tcEtcJ2ee;

    private String tcEtcWebsphere;

    private String tcEtcTomcat;

    private String tcEtcJp1;

    private String tcEtcCosminexus;

    private String tcEtcXamarin;

    private String remarks;

    private String delFlg;

    private String regAccount;

    private Date regTime;

    private String updAccount;

    private Date updTime;

    public Resume(String companyCode, String userId, String resumeVerKbn, String nearestStation, String graduationYm, String degree, String schoolName, String major, String certification1, String certification2, String certification3, String certification4, String certification5, String certification6, Float experienceYears, String responsible, String lgJpBusinessCon, String lgJpDailyCon, String lgJpReading, String lgJpListening, String lgJpWritting, String lgEnBusinessCon, String lgEnDailyCon, String lgEnReading, String lgEnListening, String lgEnWritting, String tcOsWindows, String tcOsUnix, String tcOsSolaris, String tcOsHpux, String tcOsAix, String tcOsLinux, String tcOsDos, String tcOsMac, String tcPgCobol, String tcPgJava, String tcPgJsp, String tcPgC, String tcPgVb, String tcPgVc, String tcPgSql, String tcPgNet, String tcPgPerl, String tcPgXml, String tcDbOracle, String tcDbHirdb, String tcDbSqlserver, String tcDbDb2, String tcDbMysql, String tcDbPostgresql, String tcDbAccess, String tcEtcWeblogic, String tcEtcJ2ee, String tcEtcWebsphere, String tcEtcTomcat, String tcEtcJp1, String tcEtcCosminexus, String tcEtcXamarin, String remarks, String delFlg, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.userId = userId;
        this.resumeVerKbn = resumeVerKbn;
        this.nearestStation = nearestStation;
        this.graduationYm = graduationYm;
        this.degree = degree;
        this.schoolName = schoolName;
        this.major = major;
        this.certification1 = certification1;
        this.certification2 = certification2;
        this.certification3 = certification3;
        this.certification4 = certification4;
        this.certification5 = certification5;
        this.certification6 = certification6;
        this.experienceYears = experienceYears;
        this.responsible = responsible;
        this.lgJpBusinessCon = lgJpBusinessCon;
        this.lgJpDailyCon = lgJpDailyCon;
        this.lgJpReading = lgJpReading;
        this.lgJpListening = lgJpListening;
        this.lgJpWritting = lgJpWritting;
        this.lgEnBusinessCon = lgEnBusinessCon;
        this.lgEnDailyCon = lgEnDailyCon;
        this.lgEnReading = lgEnReading;
        this.lgEnListening = lgEnListening;
        this.lgEnWritting = lgEnWritting;
        this.tcOsWindows = tcOsWindows;
        this.tcOsUnix = tcOsUnix;
        this.tcOsSolaris = tcOsSolaris;
        this.tcOsHpux = tcOsHpux;
        this.tcOsAix = tcOsAix;
        this.tcOsLinux = tcOsLinux;
        this.tcOsDos = tcOsDos;
        this.tcOsMac = tcOsMac;
        this.tcPgCobol = tcPgCobol;
        this.tcPgJava = tcPgJava;
        this.tcPgJsp = tcPgJsp;
        this.tcPgC = tcPgC;
        this.tcPgVb = tcPgVb;
        this.tcPgVc = tcPgVc;
        this.tcPgSql = tcPgSql;
        this.tcPgNet = tcPgNet;
        this.tcPgPerl = tcPgPerl;
        this.tcPgXml = tcPgXml;
        this.tcDbOracle = tcDbOracle;
        this.tcDbHirdb = tcDbHirdb;
        this.tcDbSqlserver = tcDbSqlserver;
        this.tcDbDb2 = tcDbDb2;
        this.tcDbMysql = tcDbMysql;
        this.tcDbPostgresql = tcDbPostgresql;
        this.tcDbAccess = tcDbAccess;
        this.tcEtcWeblogic = tcEtcWeblogic;
        this.tcEtcJ2ee = tcEtcJ2ee;
        this.tcEtcWebsphere = tcEtcWebsphere;
        this.tcEtcTomcat = tcEtcTomcat;
        this.tcEtcJp1 = tcEtcJp1;
        this.tcEtcCosminexus = tcEtcCosminexus;
        this.tcEtcXamarin = tcEtcXamarin;
        this.remarks = remarks;
        this.delFlg = delFlg;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }

    public Resume() {
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

    public String getNearestStation() {
        return nearestStation;
    }

    public void setNearestStation(String nearestStation) {
        this.nearestStation = nearestStation == null ? null : nearestStation.trim();
    }

    public String getGraduationYm() {
        return graduationYm;
    }

    public void setGraduationYm(String graduationYm) {
        this.graduationYm = graduationYm == null ? null : graduationYm.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getCertification1() {
        return certification1;
    }

    public void setCertification1(String certification1) {
        this.certification1 = certification1 == null ? null : certification1.trim();
    }

    public String getCertification2() {
        return certification2;
    }

    public void setCertification2(String certification2) {
        this.certification2 = certification2 == null ? null : certification2.trim();
    }

    public String getCertification3() {
        return certification3;
    }

    public void setCertification3(String certification3) {
        this.certification3 = certification3 == null ? null : certification3.trim();
    }

    public String getCertification4() {
        return certification4;
    }

    public void setCertification4(String certification4) {
        this.certification4 = certification4 == null ? null : certification4.trim();
    }

    public String getCertification5() {
        return certification5;
    }

    public void setCertification5(String certification5) {
        this.certification5 = certification5 == null ? null : certification5.trim();
    }

    public String getCertification6() {
        return certification6;
    }

    public void setCertification6(String certification6) {
        this.certification6 = certification6 == null ? null : certification6.trim();
    }

    public Float getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Float experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible == null ? null : responsible.trim();
    }

    public String getLgJpBusinessCon() {
        return lgJpBusinessCon;
    }

    public void setLgJpBusinessCon(String lgJpBusinessCon) {
        this.lgJpBusinessCon = lgJpBusinessCon == null ? null : lgJpBusinessCon.trim();
    }

    public String getLgJpDailyCon() {
        return lgJpDailyCon;
    }

    public void setLgJpDailyCon(String lgJpDailyCon) {
        this.lgJpDailyCon = lgJpDailyCon == null ? null : lgJpDailyCon.trim();
    }

    public String getLgJpReading() {
        return lgJpReading;
    }

    public void setLgJpReading(String lgJpReading) {
        this.lgJpReading = lgJpReading == null ? null : lgJpReading.trim();
    }

    public String getLgJpListening() {
        return lgJpListening;
    }

    public void setLgJpListening(String lgJpListening) {
        this.lgJpListening = lgJpListening == null ? null : lgJpListening.trim();
    }

    public String getLgJpWritting() {
        return lgJpWritting;
    }

    public void setLgJpWritting(String lgJpWritting) {
        this.lgJpWritting = lgJpWritting == null ? null : lgJpWritting.trim();
    }

    public String getLgEnBusinessCon() {
        return lgEnBusinessCon;
    }

    public void setLgEnBusinessCon(String lgEnBusinessCon) {
        this.lgEnBusinessCon = lgEnBusinessCon == null ? null : lgEnBusinessCon.trim();
    }

    public String getLgEnDailyCon() {
        return lgEnDailyCon;
    }

    public void setLgEnDailyCon(String lgEnDailyCon) {
        this.lgEnDailyCon = lgEnDailyCon == null ? null : lgEnDailyCon.trim();
    }

    public String getLgEnReading() {
        return lgEnReading;
    }

    public void setLgEnReading(String lgEnReading) {
        this.lgEnReading = lgEnReading == null ? null : lgEnReading.trim();
    }

    public String getLgEnListening() {
        return lgEnListening;
    }

    public void setLgEnListening(String lgEnListening) {
        this.lgEnListening = lgEnListening == null ? null : lgEnListening.trim();
    }

    public String getLgEnWritting() {
        return lgEnWritting;
    }

    public void setLgEnWritting(String lgEnWritting) {
        this.lgEnWritting = lgEnWritting == null ? null : lgEnWritting.trim();
    }

    public String getTcOsWindows() {
        return tcOsWindows;
    }

    public void setTcOsWindows(String tcOsWindows) {
        this.tcOsWindows = tcOsWindows == null ? null : tcOsWindows.trim();
    }

    public String getTcOsUnix() {
        return tcOsUnix;
    }

    public void setTcOsUnix(String tcOsUnix) {
        this.tcOsUnix = tcOsUnix == null ? null : tcOsUnix.trim();
    }

    public String getTcOsSolaris() {
        return tcOsSolaris;
    }

    public void setTcOsSolaris(String tcOsSolaris) {
        this.tcOsSolaris = tcOsSolaris == null ? null : tcOsSolaris.trim();
    }

    public String getTcOsHpux() {
        return tcOsHpux;
    }

    public void setTcOsHpux(String tcOsHpux) {
        this.tcOsHpux = tcOsHpux == null ? null : tcOsHpux.trim();
    }

    public String getTcOsAix() {
        return tcOsAix;
    }

    public void setTcOsAix(String tcOsAix) {
        this.tcOsAix = tcOsAix == null ? null : tcOsAix.trim();
    }

    public String getTcOsLinux() {
        return tcOsLinux;
    }

    public void setTcOsLinux(String tcOsLinux) {
        this.tcOsLinux = tcOsLinux == null ? null : tcOsLinux.trim();
    }

    public String getTcOsDos() {
        return tcOsDos;
    }

    public void setTcOsDos(String tcOsDos) {
        this.tcOsDos = tcOsDos == null ? null : tcOsDos.trim();
    }

    public String getTcOsMac() {
        return tcOsMac;
    }

    public void setTcOsMac(String tcOsMac) {
        this.tcOsMac = tcOsMac == null ? null : tcOsMac.trim();
    }

    public String getTcPgCobol() {
        return tcPgCobol;
    }

    public void setTcPgCobol(String tcPgCobol) {
        this.tcPgCobol = tcPgCobol == null ? null : tcPgCobol.trim();
    }

    public String getTcPgJava() {
        return tcPgJava;
    }

    public void setTcPgJava(String tcPgJava) {
        this.tcPgJava = tcPgJava == null ? null : tcPgJava.trim();
    }

    public String getTcPgJsp() {
        return tcPgJsp;
    }

    public void setTcPgJsp(String tcPgJsp) {
        this.tcPgJsp = tcPgJsp == null ? null : tcPgJsp.trim();
    }

    public String getTcPgC() {
        return tcPgC;
    }

    public void setTcPgC(String tcPgC) {
        this.tcPgC = tcPgC == null ? null : tcPgC.trim();
    }

    public String getTcPgVb() {
        return tcPgVb;
    }

    public void setTcPgVb(String tcPgVb) {
        this.tcPgVb = tcPgVb == null ? null : tcPgVb.trim();
    }

    public String getTcPgVc() {
        return tcPgVc;
    }

    public void setTcPgVc(String tcPgVc) {
        this.tcPgVc = tcPgVc == null ? null : tcPgVc.trim();
    }

    public String getTcPgSql() {
        return tcPgSql;
    }

    public void setTcPgSql(String tcPgSql) {
        this.tcPgSql = tcPgSql == null ? null : tcPgSql.trim();
    }

    public String getTcPgNet() {
        return tcPgNet;
    }

    public void setTcPgNet(String tcPgNet) {
        this.tcPgNet = tcPgNet == null ? null : tcPgNet.trim();
    }

    public String getTcPgPerl() {
        return tcPgPerl;
    }

    public void setTcPgPerl(String tcPgPerl) {
        this.tcPgPerl = tcPgPerl == null ? null : tcPgPerl.trim();
    }

    public String getTcPgXml() {
        return tcPgXml;
    }

    public void setTcPgXml(String tcPgXml) {
        this.tcPgXml = tcPgXml == null ? null : tcPgXml.trim();
    }

    public String getTcDbOracle() {
        return tcDbOracle;
    }

    public void setTcDbOracle(String tcDbOracle) {
        this.tcDbOracle = tcDbOracle == null ? null : tcDbOracle.trim();
    }

    public String getTcDbHirdb() {
        return tcDbHirdb;
    }

    public void setTcDbHirdb(String tcDbHirdb) {
        this.tcDbHirdb = tcDbHirdb == null ? null : tcDbHirdb.trim();
    }

    public String getTcDbSqlserver() {
        return tcDbSqlserver;
    }

    public void setTcDbSqlserver(String tcDbSqlserver) {
        this.tcDbSqlserver = tcDbSqlserver == null ? null : tcDbSqlserver.trim();
    }

    public String getTcDbDb2() {
        return tcDbDb2;
    }

    public void setTcDbDb2(String tcDbDb2) {
        this.tcDbDb2 = tcDbDb2 == null ? null : tcDbDb2.trim();
    }

    public String getTcDbMysql() {
        return tcDbMysql;
    }

    public void setTcDbMysql(String tcDbMysql) {
        this.tcDbMysql = tcDbMysql == null ? null : tcDbMysql.trim();
    }

    public String getTcDbPostgresql() {
        return tcDbPostgresql;
    }

    public void setTcDbPostgresql(String tcDbPostgresql) {
        this.tcDbPostgresql = tcDbPostgresql == null ? null : tcDbPostgresql.trim();
    }

    public String getTcDbAccess() {
        return tcDbAccess;
    }

    public void setTcDbAccess(String tcDbAccess) {
        this.tcDbAccess = tcDbAccess == null ? null : tcDbAccess.trim();
    }

    public String getTcEtcWeblogic() {
        return tcEtcWeblogic;
    }

    public void setTcEtcWeblogic(String tcEtcWeblogic) {
        this.tcEtcWeblogic = tcEtcWeblogic == null ? null : tcEtcWeblogic.trim();
    }

    public String getTcEtcJ2ee() {
        return tcEtcJ2ee;
    }

    public void setTcEtcJ2ee(String tcEtcJ2ee) {
        this.tcEtcJ2ee = tcEtcJ2ee == null ? null : tcEtcJ2ee.trim();
    }

    public String getTcEtcWebsphere() {
        return tcEtcWebsphere;
    }

    public void setTcEtcWebsphere(String tcEtcWebsphere) {
        this.tcEtcWebsphere = tcEtcWebsphere == null ? null : tcEtcWebsphere.trim();
    }

    public String getTcEtcTomcat() {
        return tcEtcTomcat;
    }

    public void setTcEtcTomcat(String tcEtcTomcat) {
        this.tcEtcTomcat = tcEtcTomcat == null ? null : tcEtcTomcat.trim();
    }

    public String getTcEtcJp1() {
        return tcEtcJp1;
    }

    public void setTcEtcJp1(String tcEtcJp1) {
        this.tcEtcJp1 = tcEtcJp1 == null ? null : tcEtcJp1.trim();
    }

    public String getTcEtcCosminexus() {
        return tcEtcCosminexus;
    }

    public void setTcEtcCosminexus(String tcEtcCosminexus) {
        this.tcEtcCosminexus = tcEtcCosminexus == null ? null : tcEtcCosminexus.trim();
    }

    public String getTcEtcXamarin() {
        return tcEtcXamarin;
    }

    public void setTcEtcXamarin(String tcEtcXamarin) {
        this.tcEtcXamarin = tcEtcXamarin == null ? null : tcEtcXamarin.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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