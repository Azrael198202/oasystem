package com.demo.pojo.resume;

import com.demo.entity.ResumeDetail;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/*
 * Author  : libin
   Time    : 2022/07/11
   Function:
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExportResumeDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    private String companyCode;

    private String userId;

    private String nameKana;
    // 姓名
    private String nameKanji;

    private String sexual;

    private String birthdayYmd;

    private String ages;

    private String address;

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

    private String nowDate;

    private List<ResumeDetailDTO> ResumeDetailList;

}
