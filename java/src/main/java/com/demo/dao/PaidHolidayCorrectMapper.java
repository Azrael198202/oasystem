package com.demo.dao;

import com.demo.entity.PaidHolidayCorrect;
import com.demo.pojo.paidHolidayCorrect.DefaultPaidHolidayCorrectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface PaidHolidayCorrectMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("changeDate") String changeDate, @Param("subNumber") String subNumber);

    int insert(PaidHolidayCorrect record);

    int insertSelective(PaidHolidayCorrect record);

    PaidHolidayCorrect selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("changeDate") String changeDate, @Param("subNumber") String subNumber);

    int updateByPrimaryKeySelective(PaidHolidayCorrect record);

    int updateByPrimaryKey(PaidHolidayCorrect record);
    List<PaidHolidayCorrect> getPaidHolidayCorrect(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("changeDate") String changeDate);

    List<DefaultPaidHolidayCorrectDTO>getSelectPaidHolidayCorrect(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("dateYm")String dateYm);

}