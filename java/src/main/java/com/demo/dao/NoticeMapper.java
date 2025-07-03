package com.demo.dao;

import com.demo.entity.Notice;
import com.demo.pojo.notice.NoticeRpnDTO;
import com.demo.pojo.notice.NoticeAllListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("noticeNumber") String noticeNumber);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("noticeNumber") String noticeNumber);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKeyWithBLOBs(Notice record);

    int updateByPrimaryKey(Notice record);

    List<NoticeRpnDTO> selectAll(@Param("companyCode") String companyCode);

    NoticeRpnDTO selectByNoticeNumber(@Param("companyCode") String companyCode, @Param("noticeNumber") String noticeNumber);
    
    List<NoticeAllListDTO> searchNotice(@Param("companyCode") String companyCode
            , @Param("noticeStatus")String noticeStatus
            , @Param("noticeCategory")String noticeCategory
            , @Param("userIdOrName")String userIdOrName);

    int deleteNotice(@Param("companyCode") String companyCode, @Param("noticeNumber")String noticeNumber);

    int insertNotice(Notice record);

    String checkNoticeNumber();
}