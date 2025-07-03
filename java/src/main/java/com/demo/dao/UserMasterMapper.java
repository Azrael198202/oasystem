package com.demo.dao;

import com.demo.entity.Avatar;
import com.demo.entity.UserMaster;
import com.demo.pojo.common.LoginUserInfo;

import com.demo.pojo.user.UserDTO;
import com.demo.pojo.user.UserDetailDTO;
import com.demo.pojo.user.UserDetailInfo;
import com.demo.pojo.user.UserRemainHolidayDTO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMasterMapper {
    int deleteByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId);

    int insert(UserMaster record);

    int insertSelective(UserMaster record);

    UserMaster selectByPrimaryKey(@Param("companyCode") String companyCode, @Param("userId") String userId);

    int updateByPrimaryKeySelective(UserMaster record);

    int updateByPrimaryKey(UserMaster record);

    int updateImageByPrimaryKey(Avatar record);

    /*---------updateByPrimaryKey 截至为自动生成------------------*/

    /**
     * 根据用户名查找用户
     *
     * @param companyCode
     * @param userId
     * @return
     */
    UserMaster selectByKey(@Param("companyCode") String companyCode, @Param("userId") String userId);

    /**
     * 根据メール查找用户
     *
     * @param email
     * @return
     */
    UserMaster selectByMail(@Param("email") String email);

    /**
     * 获取用户信息
     *
     * @param companyCode
     * @param userId
     * @return
     */
    LoginUserInfo getUserInfo(@Param("companyCode") String companyCode, @Param("userId") String userId);

    /**
     * 获取用户详细信息
     *
     * @param companyCode
     * @param userId
     * @return
     */
    UserDetailInfo getUserDetailInfo(@Param("companyCode") String companyCode, @Param("userId") String userId);

    List<UserDetailInfo> getUserDetail(@Param("companyCode") String companyCode, @Param("userId") String userId);

    UserDetailDTO getUser(@Param("companyCode") String companyCode, @Param("userId") String userId);

    List<UserMaster> getAllUserByCompanyCode(@Param("companyCode") String companyCode);

    void updatePassward(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("passWord") String passWord, @Param("updTime") Date updTime, @Param("updAccount") String updAccount);

    //休日一覧
    List<UserRemainHolidayDTO> getUserRemainHolidayList(@Param("companyCode") String companyCode,@Param("departmentCode")String departmentCode);
    //    権限付与：検索押下時の処理
    List<UserDTO> research(@Param("companyCode") String companyCode, @Param("userId") String userId, @Param("userName") String userName);


}