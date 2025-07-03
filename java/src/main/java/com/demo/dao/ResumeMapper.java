package com.demo.dao;

import com.demo.entity.Resume;
import com.demo.pojo.resume.GetResumeVerDTO;
import com.demo.pojo.resume.GetUserDTO;
import com.demo.pojo.resume.SearchResumeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ResumeMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("resumeVerKbn") String resumeVerKbn);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("resumeVerKbn") String resumeVerKbn);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);

    /*---------updateByPrimaryKey 截至为自动生成------------------*/

    List<SearchResumeDTO> searchResumeAll(@Param("companyCode") String companyCode, @Param("userId") String userId);

    List<GetUserDTO> searchUser(@Param("companyCode") String companyCode, @Param("os") String os, @Param("osKbn") String osKbn
                              , @Param("pg") String pg, @Param("pgKbn") String pgKbn
                              , @Param("db") String db, @Param("dbKbn") String dbKbn
                              , @Param("etc") String ect, @Param("etcKbn") String ectKbn
                              , @Param("requirementsFlg") String requirementsFlg, @Param("basicFlg") String basicFlg
                              , @Param("specificFlg") String specificFlg, @Param("codingFlg") String codingFlg
                              , @Param("unitTestFlg") String unitTestFlg, @Param("interfaceTestFlg") String interfaceTestFlg
                              , @Param("systemTestFlg") String systemTestFlg
                              , @Param("age") String age
                              , @Param("departmentCode") String departmentCode
                              , @Param("experienceYears") String experienceYears
                              , @Param("responsible") String responsible
                              , @Param("userIdName") String userIdName
    );

    List<GetResumeVerDTO> searchResumeVer(@Param("companyCode") String companyCode, @Param("userId") String userId);

    SearchResumeDTO searchResumeNew(@Param("companyCode") String companyCode, @Param("userId") String userId);

    List<GetUserDTO> searchResumeNull(@Param("companyCode") String companyCode, @Param("age") String age , @Param("departmentCode") String departmentCode);
}