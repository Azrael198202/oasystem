package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.CategoryMapper;
import com.demo.dao.ResumeDetailMapper;
import com.demo.dao.ResumeMapper;
import com.demo.dao.UserMasterMapper;
import com.demo.entity.Category;
import com.demo.entity.Resume;
import com.demo.entity.ResumeDetail;
import com.demo.pojo.resume.*;
import com.demo.pojo.user.UserDetailInfo;
import com.demo.service.IResumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/*
 * Author  : libin
   Time    : 2022/07/11
   Function: 履歴経歴書一覧
*/
@Service
@Slf4j

public class ResumeServiceImpl implements IResumeService {

    @Autowired
    ResumeMapper resumeMapper;

    @Autowired
    ResumeDetailMapper resumeDetailMapper;

    @Autowired
    private UserMasterMapper userMasterMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    String lgKbn = "071";
    String tcKbn = "072";
    String reKbn = "073";

    @Override
    public ServerResponse<List<SearchResumeDTO>> getResumeList(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {

        List<SearchResumeDTO> searchResumeDTOList = resumeMapper.searchResumeAll(searchResumeDTO.getCompanyCode(), searchResumeDTO.getUserId());

        return ServerResponse.createBySuccess(searchResumeDTOList);
    }

    @Override
    public ServerResponse<String> insertResume(InsertResumeDTO insertResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        Resume resume = new Resume();
        Resume resumeCheck = resumeMapper.selectByPrimaryKey(insertResumeDTO.getCompanyCode(), insertResumeDTO.getUserId(), insertResumeDTO.getResumeVerKbn());
        if (resumeCheck != null){
            return ServerResponse.createByErrorMessage("データは既に存在します。");
        }
        BeanCopier beanCopier = BeanCopier.create(InsertResumeDTO.class, Resume.class, false);
        beanCopier.copy(insertResumeDTO, resume, null);
        if (StringUtils.isEmpty(insertResumeDTO.getRemarks())){
            resume.setRemarks("社員ID"+insertResumeDTO.getUserId()+"：新規登録しました。");
        }
        resume.setDelFlg("0");
        resume.setRegAccount(insertResumeDTO.getUserId());
        resume.setRegTime(date);
        resume.setUpdAccount(insertResumeDTO.getUserId());
        resume.setUpdTime(date);

        int result1 = resumeMapper.insert(resume);
        int result2 = 0;
        if (!insertResumeDTO.getResumeDetailList().isEmpty() && insertResumeDTO.getResumeDetailList() != null){
            for (ResumeDetail detail :insertResumeDTO.getResumeDetailList()){
                ResumeDetail resumeDetail = new ResumeDetail();
                BeanCopier beanCopier1 = BeanCopier.create(ResumeDetail.class, ResumeDetail.class, false);
                beanCopier1.copy(detail, resumeDetail, null);
                resumeDetail.setCompanyCode(insertResumeDTO.getCompanyCode());
                resumeDetail.setDevelopPeriodFrom(detail.getDevelopPeriodFrom().replaceAll("/",""));
                resumeDetail.setDevelopPeriodTo(detail.getDevelopPeriodTo().replaceAll("/",""));
                resumeDetail.setUserId(insertResumeDTO.getUserId());
                resumeDetail.setResumeVerKbn(insertResumeDTO.getResumeVerKbn());
                resumeDetail.setDelFlg("0");
                resumeDetail.setRegAccount(insertResumeDTO.getUserId());
                resumeDetail.setRegTime(date);
                resumeDetail.setUpdAccount(insertResumeDTO.getUserId());
                resumeDetail.setUpdTime(date);

                result2 = resumeDetailMapper.insert(resumeDetail);

                if (result1 != ConstCode.NUM_1) {
                    return ServerResponse.createByErrorMessage("データ登録失敗しました。");
                }
            }
        }

        if (result1 == ConstCode.NUM_1 && result2 == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("データ登録しました。");
        }
        return ServerResponse.createByErrorMessage("データ登録失敗しました。");
    }

    @Override
    public ServerResponse<String> updateResume(InsertResumeDTO insertResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        Resume resume = new Resume();
        resumeMapper.deleteByPrimaryKey(insertResumeDTO.getCompanyCode(), insertResumeDTO.getUserId(), insertResumeDTO.getResumeVerKbn());

        resumeDetailMapper.deleteAll(insertResumeDTO.getCompanyCode(), insertResumeDTO.getUserId(), insertResumeDTO.getResumeVerKbn());

        BeanCopier beanCopier = BeanCopier.create(InsertResumeDTO.class, Resume.class, false);
        beanCopier.copy(insertResumeDTO, resume, null);
        resume.setDelFlg("0");
        resume.setRegAccount(insertResumeDTO.getUserId());
        resume.setRegTime(date);
        resume.setUpdAccount(insertResumeDTO.getUserId());
        resume.setUpdTime(date);
        if (insertResumeDTO.getRemarks().contains("：新規登録しました。")){
            resume.setRemarks("社員ID"+insertResumeDTO.getUserId()+"：更新しました。");
        }
        int result1 = resumeMapper.insert(resume);
        int result2 = 0;
        if (!insertResumeDTO.getResumeDetailList().isEmpty() && insertResumeDTO.getResumeDetailList() != null){
            for (ResumeDetail detail :insertResumeDTO.getResumeDetailList()){
                ResumeDetail resumeDetail = new ResumeDetail();
                BeanCopier beanCopier1 = BeanCopier.create(ResumeDetail.class, ResumeDetail.class, false);
                beanCopier1.copy(detail, resumeDetail, null);
                resumeDetail.setCompanyCode(insertResumeDTO.getCompanyCode());
                resumeDetail.setDevelopPeriodFrom(detail.getDevelopPeriodFrom().replaceAll("/",""));
                resumeDetail.setDevelopPeriodTo(detail.getDevelopPeriodTo().replaceAll("/",""));
                resumeDetail.setUserId(insertResumeDTO.getUserId());
                resumeDetail.setResumeVerKbn(insertResumeDTO.getResumeVerKbn());
                resumeDetail.setDelFlg("0");
                resumeDetail.setRegAccount(insertResumeDTO.getUserId());
                resumeDetail.setRegTime(date);
                resumeDetail.setUpdAccount(insertResumeDTO.getUserId());
                resumeDetail.setUpdTime(date);

                result2 = resumeDetailMapper.insert(resumeDetail);

                if (result1 != ConstCode.NUM_1) {
                    return ServerResponse.createByErrorMessage("データ登録失敗しました。");
                }
            }
        }

        if (result1 == ConstCode.NUM_1 && result2 == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("データ登録しました。");
        }
        return ServerResponse.createByErrorMessage("データ登録失敗しました。");
    }

    @Override
    public ServerResponse<InsertResumeDTO> getResume(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {
        Resume resumeCheck = resumeMapper.selectByPrimaryKey(searchResumeDTO.getCompanyCode(), searchResumeDTO.getUserId(), searchResumeDTO.getResumeVerKbn());
        if (resumeCheck == null){
            return ServerResponse.createByErrorMessage("データは存在しません。");
        }
        InsertResumeDTO responseDto = new InsertResumeDTO();
        BeanCopier beanCopier = BeanCopier.create(Resume.class, InsertResumeDTO.class, false);
        beanCopier.copy(resumeCheck, responseDto, null);

        List<ResumeDetail> resumeDetail = resumeDetailMapper.selectResumeDetailList(searchResumeDTO.getCompanyCode(), searchResumeDTO.getUserId(), searchResumeDTO.getResumeVerKbn());

        responseDto.setResumeDetailList(resumeDetail);

        return ServerResponse.createBySuccess(responseDto);
    }

    @Override
    public ServerResponse<String> deleteResume(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {

        int deleteResume = resumeMapper.deleteByPrimaryKey(searchResumeDTO.getCompanyCode(), searchResumeDTO.getUserId(), searchResumeDTO.getResumeVerKbn());

        int deleteResumeDetail = resumeDetailMapper.deleteAll(searchResumeDTO.getCompanyCode(), searchResumeDTO.getUserId(), searchResumeDTO.getResumeVerKbn());
        if (deleteResume == ConstCode.NUM_1 && deleteResumeDetail == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("履歴経歴書テーブルのレコードを削除しました。");
        }

        return ServerResponse.createByErrorMessage("履歴経歴書テーブルのレコードの削除が失敗しました。");
    }

    @Override
    public Map<String, Object> exportResume(SearchResumeDTO searchResumeDTO){
        ExportResumeDTO exportResumeDTO = new ExportResumeDTO();

        // 会社コード
        String companyCode = searchResumeDTO.getCompanyCode();

        // ユーザーID
        String userId = searchResumeDTO.getUserId();

        // 経歴書バージョン区分
        String resumeVerKbn = searchResumeDTO.getResumeVerKbn();

        Resume resume = resumeMapper.selectByPrimaryKey(companyCode, userId, resumeVerKbn);

        BeanCopier beanCopier = BeanCopier.create(Resume.class, ExportResumeDTO.class, false);
        beanCopier.copy(resume, exportResumeDTO, null);

        List<ResumeDetail> resumeDetails = resumeDetailMapper.selectResumeDetailList(companyCode, userId, resumeVerKbn);
        List<ResumeDetailDTO> list = new ArrayList<ResumeDetailDTO>();
        SimpleDateFormat sdff =   new SimpleDateFormat( "yyyyMMdd" );
        SimpleDateFormat sdfl =   new SimpleDateFormat( "yyyy/MM" );
        for (ResumeDetail resumeDetail : resumeDetails){
            ResumeDetailDTO resumeDetailDTO = new ResumeDetailDTO();

            try {
                resumeDetailDTO.setDevelop_period(sdfl.format(sdff.parse(resumeDetail.getDevelopPeriodFrom()))
                        + "～" +sdfl.format(sdff.parse(resumeDetail.getDevelopPeriodTo())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            resumeDetailDTO.setDevelop_summary("【"+resumeDetail.getDevelopSummarySys() + "】<br>" +
                    "概要：" + resumeDetail.getDevelopSummarySry() + "<br>" +
                    "担当内容：" + resumeDetail.getDevelopSummaryCts() + "<br>" +
                    "メンバー数：" + resumeDetail.getDevelopSummaryMbs());
            String developLanguage = "";
            if (!StringUtils.isEmpty(resumeDetail.getDevelopLanguage1())){
                developLanguage += resumeDetail.getDevelopLanguage1() + "<br>";
            }
            if (!StringUtils.isEmpty(resumeDetail.getDevelopLanguage2())){
                developLanguage += resumeDetail.getDevelopLanguage2() + "<br>";
            }
            if (!StringUtils.isEmpty(resumeDetail.getDevelopLanguage3())){
                developLanguage += resumeDetail.getDevelopLanguage3();
            }
            String developOs = "";
            if (!StringUtils.isEmpty(resumeDetail.getDevelopOs1())){
                developOs += resumeDetail.getDevelopOs1() + "<br>";
            }
            if (!StringUtils.isEmpty(resumeDetail.getDevelopOs2())){
                developOs += resumeDetail.getDevelopOs2() + "<br>";
            }
            if (!StringUtils.isEmpty(resumeDetail.getDevelopOs3())){
                developOs += resumeDetail.getDevelopOs3();
            }

            String developDb = "";
            if (!StringUtils.isEmpty(resumeDetail.getDevelopDb1())){
                developDb += resumeDetail.getDevelopDb1() + "<br>";
            }
            if (!StringUtils.isEmpty(resumeDetail.getDevelopDb2())){
                developDb += resumeDetail.getDevelopDb2() + "<br>";
            }
            if (!StringUtils.isEmpty(resumeDetail.getDevelopDb3())){
                developDb += resumeDetail.getDevelopDb3();
            }
            resumeDetailDTO.setDevelop_language(developLanguage);
            resumeDetailDTO.setDevelop_os(developOs);
            resumeDetailDTO.setDevelop_db(developDb);

            resumeDetailDTO.setResponsible(categoryKbn(exportResumeDTO.getCompanyCode(),reKbn,resumeDetail.getResponsible()));

            resumeDetailDTO.setBasic_flg(!StringUtils.isEmpty(resumeDetail.getBasicFlg()) && resumeDetail.getBasicFlg().equals("0")?"●":"");
            resumeDetailDTO.setCoding_flg(!StringUtils.isEmpty(resumeDetail.getCodingFlg()) && resumeDetail.getCodingFlg().equals("0")?"●":"");
            resumeDetailDTO.setRequirements_flg(!StringUtils.isEmpty(resumeDetail.getRequirementsFlg()) && resumeDetail.getRequirementsFlg().equals("0")?"●":"");
            resumeDetailDTO.setSpecific_flg(!StringUtils.isEmpty(resumeDetail.getSpecificFlg()) && resumeDetail.getSpecificFlg().equals("0")?"●":"");
            resumeDetailDTO.setInterface_test_flg(!StringUtils.isEmpty(resumeDetail.getInterfaceTestFlg()) && resumeDetail.getInterfaceTestFlg().equals("0")?"●":"");
            resumeDetailDTO.setSystem_test_flg(!StringUtils.isEmpty(resumeDetail.getSystemTestFlg()) && resumeDetail.getSystemTestFlg().equals("0")?"●":"");
            resumeDetailDTO.setUnit_test_flg(!StringUtils.isEmpty(resumeDetail.getUnitTestFlg()) && resumeDetail.getUnitTestFlg().equals("0")?"●":"");
            list.add(resumeDetailDTO);

        }
        exportResumeDTO.setResumeDetailList(list);

        // ユーザー情報設定
        UserDetailInfo userDetailDTO = userMasterMapper.getUserDetailInfo(companyCode, userId);

        exportResumeDTO.setBirthdayYmd(userDetailDTO.getBirthday());
        exportResumeDTO.setNameKana(userDetailDTO.getFirstNameKana() +" " + userDetailDTO.getLastNameKana());
        exportResumeDTO.setNameKanji(userDetailDTO.getFirstName() +" " + userDetailDTO.getLastName());
        exportResumeDTO.setSexual(userDetailDTO.getSex());
        exportResumeDTO.setAddress(userDetailDTO.getAddressPrefecture() + userDetailDTO.getAddressMunicipality() + userDetailDTO.getAddressTown() + userDetailDTO.getAddressDetail() );
        exportResumeDTO.setAges(getAgeByDateString(userDetailDTO.getBirthday()) + "歳");
        String dateYmd = new SimpleDateFormat("yyyy年MM月dd日").format(resume.getUpdTime());
        exportResumeDTO.setNowDate(dateYmd);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy年MM月dd日" );
        SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy年MM月" );
        SimpleDateFormat sdf2 =   new SimpleDateFormat( "yyyyMM" );
        Map<String, Object> cMap = new HashMap();
        cMap.put("createDate", exportResumeDTO.getNowDate());
        cMap.put("nearest_station", exportResumeDTO.getNearestStation());
        cMap.put("name_kana", exportResumeDTO.getNameKana());
        cMap.put("name_kanji", exportResumeDTO.getNameKanji());
        if("1".equals(exportResumeDTO.getSexual())){
            cMap.put("sexual", "男");
        } else {
            cMap.put("sexual", "女");
        }

        try {
            cMap.put("birthday_ymd", sdf.format(sdff.parse(exportResumeDTO.getBirthdayYmd())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cMap.put("ages", exportResumeDTO.getAges());
        cMap.put("address", exportResumeDTO.getAddress());
        try {
            cMap.put("graduation_ym", sdf1.format(sdf2.parse(exportResumeDTO.getGraduationYm())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cMap.put("degree", exportResumeDTO.getDegree());
        cMap.put("school_name", exportResumeDTO.getSchoolName());
        cMap.put("major", exportResumeDTO.getMajor());
        cMap.put("certification1", exportResumeDTO.getCertification1());
        cMap.put("certification2", exportResumeDTO.getCertification2());
        cMap.put("certification3", exportResumeDTO.getCertification3());
        cMap.put("certification4", exportResumeDTO.getCertification4());
        cMap.put("certification5", exportResumeDTO.getCertification5());
        cMap.put("certification6", exportResumeDTO.getCertification6());
        cMap.put("experience_years", Float.toString(exportResumeDTO.getExperienceYears()));
        cMap.put("responsible", categoryKbn(exportResumeDTO.getCompanyCode(),reKbn,exportResumeDTO.getResponsible()));
        cMap.put("lg_jp_business_con", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgJpBusinessCon()));
        cMap.put("lg_jp_daily_con", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgJpDailyCon()));
        cMap.put("lg_jp_reading", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgJpReading()));
        cMap.put("lg_jp_listening", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgJpListening()));
        cMap.put("lg_jp_writting", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgJpWritting()));
        cMap.put("lg_en_business_con", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgEnBusinessCon()));
        cMap.put("lg_en_daily_con", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgEnDailyCon()));
        cMap.put("lg_en_reading", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgEnReading()));
        cMap.put("lg_en_listening", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgEnListening()));
        cMap.put("lg_en_writting", categoryKbn(exportResumeDTO.getCompanyCode(),lgKbn,exportResumeDTO.getLgEnWritting()));
        cMap.put("tc_os_windows", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsWindows()));
        cMap.put("tc_os_unix", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsUnix()));
        cMap.put("tc_os_solaris", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsSolaris()));
        cMap.put("tc_os_hpux", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsHpux()));
        cMap.put("tc_os_aix", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsAix()));
        cMap.put("tc_os_linux", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsLinux()));
        cMap.put("tc_os_dos", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsDos()));
        cMap.put("tc_os_mac", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcOsMac()));
        cMap.put("tc_pg_cobol", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgCobol()));
        cMap.put("tc_pg_java", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgJava()));
        cMap.put("tc_pg_jsp", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgJsp()));
        cMap.put("tc_pg_c", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgC()));
        cMap.put("tc_pg_vb", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgVb()));
        cMap.put("tc_pg_vc", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgVc()));
        cMap.put("tc_pg_sql", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgSql()));
        cMap.put("tc_pg_net", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgNet()));
        cMap.put("tc_pg_perl", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgPerl()));
        cMap.put("tc_pg_xml", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcPgXml()));
        cMap.put("tc_db_oracle", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcDbOracle()));
        cMap.put("tc_db_hirdb", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcDbHirdb()));
        cMap.put("tc_db_sqlserver", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcDbSqlserver()));
        cMap.put("tc_db_db2", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcDbDb2()));
        cMap.put("tc_db_mysql", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcDbMysql()));
        cMap.put("tc_db_postgresql", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcDbPostgresql()));
        cMap.put("tc_db_access", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcDbAccess()));
        cMap.put("tc_etc_weblogic", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcEtcWeblogic()));
        cMap.put("tc_etc_j2ee", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcEtcJ2ee()));
        cMap.put("tc_etc_websphere", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcEtcWebsphere()));
        cMap.put("tc_etc_tomcat", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcEtcTomcat()));
        cMap.put("tc_etc_jp1", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcEtcJp1()));
        cMap.put("tc_etc_cosminexus", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcEtcCosminexus()));
        cMap.put("tc_etc_xamarin", categoryKbn(exportResumeDTO.getCompanyCode(),tcKbn,exportResumeDTO.getTcEtcXamarin()));
        cMap.put("yobi_item", "");
        cMap.put("datasourceResumeDetail", exportResumeDTO.getResumeDetailList());

        return cMap;
    }

    public String categoryKbn(String companyCode ,String categoryClassNumber,String categoryKbn){
        if (!StringUtils.isEmpty(categoryKbn)){
            Category category = categoryMapper.selectByPrimaryKey(companyCode,categoryClassNumber,categoryKbn);
            return category.getCategoryKbnName();
        }
        return "";
    }

    public static int getAgeByDateString(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date birthday = simpleDateFormat.parse(dateStr);
            return getAgeByDate(birthday);
        } catch (ParseException e) {
            return -1;
        }
    }
    public static int getAgeByDate(Date birthday) {
        Calendar calendar = Calendar.getInstance();

        if (calendar.getTimeInMillis() - birthday.getTime() < 0L) {
            return -1;
        }

        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(birthday);

        int yearBirthday = calendar.get(Calendar.YEAR);
        int monthBirthday = calendar.get(Calendar.MONTH);
        int dayOfMonthBirthday = calendar.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirthday;

        if (monthNow <= monthBirthday) {
            if (monthNow == monthBirthday) {
                if (dayOfMonthNow < dayOfMonthBirthday) {
                    age--;
                }
            } else {
                age--;
            }
        }

        return age;
    }

    @Override
    public ServerResponse<List<GetResumeVerDTO>> getResumeVerList(SearchResumeDTO searchResumeDTO, HttpServletRequest request, HttpServletResponse response) {

        List<GetResumeVerDTO> resumeVerList = resumeMapper.searchResumeVer(searchResumeDTO.getCompanyCode(),searchResumeDTO.getUserId());
        return ServerResponse.createBySuccess(resumeVerList);
    }

    @Override
    public ServerResponse<List<GetUserDTO>> getUserList(SearchUserDTO searchUserDTO, HttpServletRequest request, HttpServletResponse response) {
        List<GetUserDTO> userList = new ArrayList<GetUserDTO>();
        if("0".equals(searchUserDTO.getResumeNullFlg())){
            userList = resumeMapper.searchResumeNull(searchUserDTO.getCompanyCode(),searchUserDTO.getAge()
                    ,searchUserDTO.getDepartmentCode());
        } else {

            userList = resumeMapper.searchUser(searchUserDTO.getCompanyCode(),searchUserDTO.getOs() ,searchUserDTO.getOsKbn()
                    ,searchUserDTO.getPg() ,searchUserDTO.getPgKbn()
                    ,searchUserDTO.getDb() ,searchUserDTO.getDbKbn()
                    ,searchUserDTO.getEtc(),searchUserDTO.getEtcKbn()
                    ,"1".equals(searchUserDTO.getRequirementsFlg())?"":searchUserDTO.getRequirementsFlg()
                    ,"1".equals(searchUserDTO.getBasicFlg())?"":searchUserDTO.getBasicFlg()
                    ,"1".equals(searchUserDTO.getSpecificFlg())?"":searchUserDTO.getSpecificFlg()
                    ,"1".equals(searchUserDTO.getCodingFlg())?"":searchUserDTO.getCodingFlg()
                    ,"1".equals(searchUserDTO.getUnitTestFlg())?"":searchUserDTO.getUnitTestFlg()
                    ,"1".equals(searchUserDTO.getInterfaceTestFlg())?"":searchUserDTO.getInterfaceTestFlg()
                    ,"1".equals(searchUserDTO.getSystemTestFlg())?"":searchUserDTO.getSystemTestFlg()
                    ,searchUserDTO.getAge()
                    ,searchUserDTO.getDepartmentCode()
                    ,searchUserDTO.getExperienceYears()
                    ,searchUserDTO.getResponsible()
                    ,searchUserDTO.getUserIdName());
        }

        return ServerResponse.createBySuccess(userList);
    }

}
