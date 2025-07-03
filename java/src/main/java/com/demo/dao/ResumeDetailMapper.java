package com.demo.dao;

import com.demo.entity.ResumeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResumeDetailMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("resumeVerKbn") String resumeVerKbn, @Param("resumeSeqNo") String resumeSeqNo);

    int insert(ResumeDetail record);

    int insertSelective(ResumeDetail record);

    ResumeDetail selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("resumeVerKbn") String resumeVerKbn, @Param("resumeSeqNo") String resumeSeqNo);

    int updateByPrimaryKeySelective(ResumeDetail record);

    int updateByPrimaryKey(ResumeDetail record);

    /*---------updateByPrimaryKey 截至为自动生成------------------*/
    List<ResumeDetail> selectResumeDetailList(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("resumeVerKbn") String resumeVerKbn);

    int deleteAll(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("resumeVerKbn") String resumeVerKbn);

}