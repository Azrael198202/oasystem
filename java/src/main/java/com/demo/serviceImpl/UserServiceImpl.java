package com.demo.serviceImpl;

import com.demo.common.Const;
import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.config.PropertiesListenerConfig;
import com.demo.dao.MenuMapper;
import com.demo.dao.RequestMapper;
import com.demo.dao.UserDetailMapper;
import com.demo.dao.UserMasterMapper;
import com.demo.entity.Avatar;
import com.demo.entity.UserDetail;
import com.demo.entity.UserMaster;
import com.demo.pojo.common.LoginUserInfo;
import com.demo.pojo.common.UserAccessInfo;
import com.demo.pojo.request.InsertByRequestDTO;

import com.demo.pojo.user.UserRemainHolidayDTO;
import com.demo.pojo.user.*;

import com.demo.service.IUserService;
import com.demo.service.common.IMailService;
import com.demo.util.JwtUtil;
import com.demo.util.ShiroMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private UserDetailMapper userDetailMapper;

	@Autowired
	private IMailService iMailService;

	@Autowired
	MenuMapper menuMapper;

	@Autowired
	RequestMapper requestMapper;

	/**
	 * 用户注册
	 * @param userDto
	 * @return
	 */
	@Transactional
	public ServerResponse<String> userRegister(UserDTO userDto) {

		//
		String companyCode = userDto.getCompanyCode();
		// 用户名
		String userId = userDto.getUserId();
		// 用户密码
		String passWord = userDto.getPassWord();

		String userKey = companyCode + userId;

		// MD5密码加密 salt为userId
		String md5PassWord = ShiroMD5Util.encryptPassword(passWord, userKey);

		// 用户主表entity
		UserMaster userMaster = new UserMaster();

		// 属性copy
		BeanCopier copier = BeanCopier.create(UserDTO.class, UserMaster.class, false);
		copier.copy(userDto, userMaster, null);

		// 加密后的密码
		userMaster.setPassWord(md5PassWord);

		// 删除FLG : 0
		userMaster.setDelFlg(ConstCode.STRING_ZERO);

		Date date = new Date();
		userMaster.setRegAccount(userId);
		userMaster.setRegTime(date);
		userMaster.setUpdAccount(userId);
		userMaster.setUpdTime(date);

		userMasterMapper.insert(userMaster);

		// 用户注册成功
		return ServerResponse.createBySuccessMessage("用户注册成功");
	}

	/**
	 * 通过用户名 获取用户 delflg != '1'
	 *
	 * @param userId
	 * @return
	 */
	public UserMaster getUserByKey(String companyCode, String userId) {

		return userMasterMapper.selectByKey(companyCode,userId);
	}

	/**
	 * 通过邮箱 获取用户 delflg != '1'
	 *
	 * @param email
	 * @return
	 */
	public UserMaster getUserByMail(String email) {

		return userMasterMapper.selectByMail(email);
	}

	/**
	 * メール送信　TOKENを付けます。
	 *
	 * @param userMaster
	 * @return
	 */
	public ServerResponse findPwdMail(UserMaster userMaster){

		String subject = PropertiesListenerConfig.getProperty(Const.FIND_PWD_SUBJECT);
		String content = PropertiesListenerConfig.getProperty(Const.FIND_PWD_CONTENT);

		//姓名添加
		content = content.replace("%param0%", userMaster.getUserName());

		// パスワード再設定TOKEN
		String token = JwtUtil.findPwdSign(userMaster.getUserId(),userMaster.getCompanyCode(),userMaster.getEmail());

		// Token
		content = content.replace("%param1%", token);

		// 审核结果邮件发送
		iMailService.sendSimpleMail(userMaster.getEmail(), subject, content);

		return ServerResponse.createBySuccessMessage("メールを送信しました。");
	}
	/**
	 * 20211011申請メール
	 *
	 * @param usermailDTO
	 * @return
	 */

	public ServerResponse reqMail(UsermailDTO usermailDTO){
           //appMailKbn=1 申請メール
        if ("1".equals(usermailDTO.appMailKbn)) {
			UserMaster userMaster = this.getMailByUser(usermailDTO.companyCode,usermailDTO.userAppId);
			if(userMaster == null) {
				// ユーザー存在していないの場合、
				return ServerResponse.createByBizError("入力された社員番号に該当するユーザーは存在しませんでした。");
			}
			String subject = PropertiesListenerConfig.getProperty(Const.FIND_REQ_SUBJECT);
			String content = PropertiesListenerConfig.getProperty(Const.FIND_REQ_CONTENT);

			//承認者
			content = content.replace("%param0%", usermailDTO.appName);
			//申請者
			content = content.replace("%param1%", usermailDTO.userName);
			content = content.replace("%param2%", usermailDTO.requestOverview);
			content = content.replace("%param3%", usermailDTO.reqMailKbn);
			subject = subject.replace("%param3%", usermailDTO.reqMailKbn);

			iMailService.sendSimpleMail(userMaster.getEmail(), subject, content);

			return ServerResponse.createBySuccessMessage("メールを送信しました。");
			//appMailKbn=2 差戻メール
		} else if  ("2".equals(usermailDTO.appMailKbn)) {
			String subject = PropertiesListenerConfig.getProperty(Const.FIND_REMAND_SUBJECT);
			String content = PropertiesListenerConfig.getProperty(Const.FIND_REMAND_CONTENT);
			InsertByRequestDTO insertByRequestDTO = requestMapper.selectByResNum(usermailDTO.companyCode, usermailDTO.requestNumber);
			//承認者
			content = content.replace("%param0%", usermailDTO.appName);
			//申請者
			content = content.replace("%param1%", insertByRequestDTO.userName);
			content = content.replace("%param2%", insertByRequestDTO.requestOverview);
			content = content.replace("%param3%", usermailDTO.reqMailKbn);
			subject = subject.replace("%param3%", usermailDTO.reqMailKbn);
			content = content.replace("%param4%", usermailDTO.reqAppComment);

			UserMaster userMaster = this.getMailByUser(usermailDTO.companyCode, insertByRequestDTO.userId);
			if(userMaster == null) {
				// ユーザー存在していないの場合、
				return ServerResponse.createByBizError("入力された社員番号に該当するユーザーは存在しませんでした。");
			}
			iMailService.sendSimpleMail(userMaster.getEmail(), subject, content);
			return ServerResponse.createBySuccessMessage("メールを送信しました。");
          //appMailKbn=3 承認完了メール
		} else {
			InsertByRequestDTO insertByRequestDTO = requestMapper.selectByResNum(usermailDTO.companyCode, usermailDTO.requestNumber);
			UserMaster userMaster = this.getMailByUser(usermailDTO.companyCode,insertByRequestDTO.userId);
			if(userMaster == null) {
				// ユーザー存在していないの場合、
				return ServerResponse.createByBizError("入力された社員番号に該当するユーザーは存在しませんでした。");
			}
			String subject = PropertiesListenerConfig.getProperty(Const.FIND_APPROVAL_SUBJECT);
			String content = PropertiesListenerConfig.getProperty(Const.FIND_APPROVAL_CONTENT);
			//申請者
			content = content.replace("%param1%", insertByRequestDTO.userName);
			content = content.replace("%param2%", insertByRequestDTO.requestOverview);
			content = content.replace("%param3%", usermailDTO.reqMailKbn);
			subject = subject.replace("%param3%", usermailDTO.reqMailKbn);

			iMailService.sendSimpleMail(userMaster.getEmail(), subject, content);

			return ServerResponse.createBySuccessMessage("メールを送信しました。");
		}
	}

	/**
	 * パスワード変更処理
	 *
	 * @param userId
	 * @param companyCode
	 * @param resetPwdDTO
	 * @return
	 */
	@Transactional
	public ServerResponse updatePwd(String userId, String companyCode, ResetPwdDTO resetPwdDTO){

		// パスワード　と　パスワード確認 一致チェック
		String newPwd = resetPwdDTO.getNewPwd();
		String confirmPwd = resetPwdDTO.getConfirmPwd();

		if (newPwd != null && newPwd.equals(confirmPwd)) {

			String userKey = companyCode + userId;

			// MD5密码加密 salt为userId
			String md5PassWord = ShiroMD5Util.encryptPassword(newPwd, userKey);

			UserMaster userMaster = new UserMaster();
			userMaster.setUserId(userId);
			userMaster.setCompanyCode(companyCode);
			userMaster.setPassWord(md5PassWord);
			userMaster.setUpdAccount(userId);
			userMaster.setUpdTime(new Date());

			int result = userMasterMapper.updateByPrimaryKeySelective(userMaster);

			if(result == 1) {
				return ServerResponse.createBySuccessMessage("パスワードを更新しました。");
			} else {
				return ServerResponse.createBySuccessMessage("パスワード更新失敗しました。");
			}
		} else {

			return ServerResponse.createBySuccessMessage("パスワードとパスワード確認が一致していません。");
		}
	}


	/**
	 * ユーザー情報更新
	 *
	 * @param loginUserInfo
	 * @param userDetailInfo
	 * @return
	 */
	public ServerResponse userUpdate(LoginUserInfo loginUserInfo, UserDetailInfo userDetailInfo) {

		// ログインユーザー
		String userKey = loginUserInfo.getCompanyCode() + loginUserInfo.getUserId();

		UserMaster userMaster = new UserMaster();

		BeanCopier copier = BeanCopier.create(UserDetailInfo.class, UserMaster.class, false);
		copier.copy(userDetailInfo,userMaster,null);

		userMaster.setRegAccount(null);
		userMaster.setRegTime(null);
		userMaster.setUpdAccount(userKey);

		// ユーザーマスタテーブルの更新
		int result = userMasterMapper.updateByPrimaryKeySelective(userMaster);

		UserDetail userDetail = new UserDetail();

		BeanCopier copierTwo = BeanCopier.create(UserDetailInfo.class, UserDetail.class, false);
		copierTwo.copy(userDetailInfo,userDetail,null);

		userMaster.setRegAccount(null);
		userMaster.setRegTime(null);
		userDetail.setUpdAccount(userKey);

		// ユーザー詳細情報マスタテーブルの更新
		int resultTwo = userDetailMapper.updateByPrimaryKeySelective(userDetail);

		if(result != 0 && resultTwo != 0) {
			return ServerResponse.createBySuccessMessage("データ更新しました。");
		}

		return  ServerResponse.createByErrorMessage("データ更新失敗しました。");
	}

	@Override
	public UserMaster getMailByUser(String userId,String companyCode) {
		return userMasterMapper.selectByKey(userId, companyCode);
	}

	/**
	 * ユーザー詳細情報の取得
	 *
	 * @param companyCode
	 * @param userId
	 * @return
	 */
	public ServerResponse getUserDetailInfo(String companyCode, String userId){

		UserDetailInfo userDetailInfo = userMasterMapper.getUserDetailInfo(companyCode,userId);

		if(userDetailInfo == null) {
			return ServerResponse.createByErrorMessage("該当ユーザー存在していない。");
		}

		return ServerResponse.createBySuccess(userDetailInfo);
	}

	@Override
	public ServerResponse getUserDetail(String companyCode, String userId) {
		List<UserDetailInfo> userDetailInfo = userMasterMapper.getUserDetail(companyCode,userId);

		if(userDetailInfo == null) {
			return ServerResponse.createByErrorMessage("該当ユーザー存在していない。");
		}

		return ServerResponse.createBySuccess(userDetailInfo);
	}

	/**
	 * 获取用户类别set
	 *
	 * @param userId
	 * @return
	 */
	public UserAccessInfo getUserAccessInfo(String companyCode, String userId) {

		UserAccessInfo userAccessInfo = new UserAccessInfo();

		// 用户角色set
		Set<String> roleSet = new HashSet<String>();

		// 用户权限set
		Set<String> permissionSet = new HashSet<String>();

		UserMaster userMaster = userMasterMapper.selectByKey(companyCode,userId);

		// 账号类型
		String accountType = userMaster.getAccountKbn();

		// 管理员 accountType = 1
		if (Const.ADMIN_USER_CODE.equals(accountType)) {

			// admin
			roleSet.add(Const.ADMIN_USER);

			// Leader accountType = 2
		} else if (Const.LEADWER_USER_CODE.equals(accountType)) {

			// Leader
			roleSet.add(Const.LEADER_USER);

			// 普通用户 accountType = 3
		} else if (Const.NORMAL_USER_CODE.equals(accountType)) {

			// user
			roleSet.add(Const.NORMAL_USER);
		}

		// 用户权限类型
		String permissionType = userMaster.getPermissionKbn();

		if (Const.LEVEL_001_CODE.equals(permissionType)) {
			permissionSet.add(Const.LEVEL_001);

		} else if (Const.LEVEL_002_CODE.equals(permissionType)) {
			permissionSet.add(Const.LEVEL_002);

		} else if (Const.LEVEL_003_CODE.equals(permissionType)) {
			permissionSet.add(Const.LEVEL_003);

		} else if (Const.LEVEL_004_CODE.equals(permissionType)) {
			permissionSet.add(Const.LEVEL_004);

		} else if (Const.LEVEL_005_CODE.equals(permissionType)) {
			permissionSet.add(Const.LEVEL_005);
		}

		userAccessInfo.setRoleSet(roleSet);

		userAccessInfo.setPermissionSet(permissionSet);

		return userAccessInfo;
	}

	@Override

	public ServerResponse<List<UserRemainHolidayDTO>>getUserRemainHolidayList(@RequestBody UserRemainHolidayDTO userRemainHolidayDTO, HttpServletRequest request, HttpServletResponse response) {
		List<UserRemainHolidayDTO> UserRemainHolidayDTOList = userMasterMapper.getUserRemainHolidayList(userRemainHolidayDTO.getCompanyCode(), userRemainHolidayDTO.getDepartmentCode());
		return ServerResponse.createBySuccess(UserRemainHolidayDTOList);
	}
	//    権限付与画面：検索押下時の処理
	@Override
	public ServerResponse<List<UserDTO>> research(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {

		List<UserDTO> PrivilegeManagementList = userMasterMapper.research(userDTO.getCompanyCode(), ('%'+ userDTO.getUserId() +'%'), ('%'+ userDTO.getUserName() +'%'));
		return ServerResponse.createBySuccess(PrivilegeManagementList);
	}
	//    権限付与画面：更新押下時の処理(利用者マスタ（基本情報）のデータを更新)
	@Override
	public ServerResponse<String> authorityCreate(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response){
		// 利用者マスタ（基本情報）の更新
		UserMaster userMaster = new UserMaster();
		Date date = new Date();

			userMaster.setCompanyCode(userDTO.getCompanyCode());// 会社コード
			userMaster.setUserId(userDTO.getUserId());// ユーザーID
			userMaster.setPermissionKbn(userDTO.getPermissionKbn());// 権限区分
			userMaster.setUpdAccount(userDTO.getUpdAccount());// 更新者
			userMaster.setUpdTime(date);// 更新日時

			int result = userMasterMapper.updateByPrimaryKeySelective(userMaster);

		if(result != 0 ){
			return ServerResponse.createBySuccessMessage("データ更新しました。");
		}
		return ServerResponse.createByErrorMessage("データ更新失敗しました。");
	}

	@Override
	public ServerResponse setImageUrl(String userid, String companyCode, String imageUrl) {
		Avatar avator = new Avatar();
		avator.setUserId(userid);
		avator.setCompanyCode(companyCode);
		avator.setImageUrl(imageUrl);
		int result = userMasterMapper.updateImageByPrimaryKey(avator);

		if(result != 0 ){
			return ServerResponse.createBySuccessMessage("データ更新しました。");
		}
		return ServerResponse.createByErrorMessage("データ更新失敗しました。");
	}
}
