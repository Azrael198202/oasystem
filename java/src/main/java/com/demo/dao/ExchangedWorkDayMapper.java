package com.demo.dao;

import com.demo.entity.ExchangedWorkDay;
import com.demo.pojo.exchangedWorkDay.ExchangedWorkDayDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExchangedWorkDayMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    int insert(ExchangedWorkDay record);

    int insertSelective(ExchangedWorkDay record);

    ExchangedWorkDay selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber, @Param("requestUserId") String requestUserId);

    int updateByPrimaryKeySelective(ExchangedWorkDay record);

    int updateByPrimaryKey(ExchangedWorkDay record);

    List<ExchangedWorkDayDTO> selectExchangedWorkDay(@Param("companyCode") String companyCode, @Param("requestNumber") String requestNumber);

    int countExchangedWorkDay(@Param("companyCode") String companyCode,@Param("scheduledWorkDay") String scheduledWorkDay, @Param("requestUserId") String requestUserId);
}