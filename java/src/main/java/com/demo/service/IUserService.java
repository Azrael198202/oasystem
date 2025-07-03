package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.entity.UserMaster;
import com.demo.pojo.common.UserAccessInfo;
import com.demo.pojo.common.LoginUserInfo;
import com.demo.pojo.user.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
public interface IUserService {

	/**
	 * 普通用户注册
	 *
	 * @param userDto
	 * @return
	 */
	ServerResponse<String> userRegister(UserDTO userDto);

	/**
	 * 获取用户
	 * 
	 *  @param companyCode
	 *  @param userId
	 * @return
	 */
	UserMaster getUserByKey(String companyCode, String userId);

	/**
	 * 获取用户类别set
	 *
	 * @param companyCode
	 * @param userId
	 * @return
	 */
	UserAccessInfo getUserAccessInfo(String companyCode,String userId);

	/**
	 * 根据邮箱获取用户
	 *
	 *  @param email
	 * @return
	 */
	UserMaster getUserByMail(String email);

	/**
	 * パスワード忘れたメール送信
	 *
	 *  @param userMaster
	 * @return
	 */
	ServerResponse findPwdMail(UserMaster userMaster);


	/**
	 * 申請メール　20211011
	 *
	 *  @param
	 * @return
	 */
	ServerResponse reqMail(UsermailDTO usermailDTO);



	/**
	 * パスワード更新
	 *
	 * @param userid
	 * @param companyCode
	 * @param resetPwdDTO
	 * @return
	 */
	ServerResponse updatePwd(String userid, String companyCode, ResetPwdDTO resetPwdDTO);

	/**
	 * 获取用户详细信息
	 *
	 * @param companyCode
	 * @param userid
	 * @return
	 */
	ServerResponse getUserDetailInfo(String companyCode, String userid);

	/**
	 * 获取用户详细信息
	 *
	 * @param companyCode
	 * @param userId
	 * @return
	 */
	ServerResponse getUserDetail(String companyCode, String userId);

	/**
	 *
	 *
	 * @param loginUserInfo
	 * @param userDetailInfo
	 * @return
	 */
	ServerResponse userUpdate(LoginUserInfo loginUserInfo, UserDetailInfo userDetailInfo);

	/**
	 *
	 *
	 * @param userid
	 * @param companyCode
	 * @param imageUrl
	 * @return
	 */
	ServerResponse setImageUrl(String userid, String companyCode, String imageUrl);

	/**
	 * 根据用户获取邮箱
	 *
	 *  @param userId
	 * @return
	 */
	UserMaster getMailByUser(String userId, String companyCode);

	/**
	 * 残有給数取得
	 *
	 *  @param userRemainHolidayDTO
	 *  @param request
	 *  @param response
	 * @return
	 */

	ServerResponse<List<UserRemainHolidayDTO>> getUserRemainHolidayList(UserRemainHolidayDTO userRemainHolidayDTO, HttpServletRequest request, HttpServletResponse response);
	//    権限付与画面：検索押下時の処理
	ServerResponse<List<UserDTO>> research(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response);
	//    権限付与画面：更新押下時の処理(利用者マスタ（基本情報）のデータを更新)
	ServerResponse<String> authorityCreate(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response);

}
