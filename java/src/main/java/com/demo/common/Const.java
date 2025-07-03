package com.demo.common;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
public class Const {

	/** 1:管理员 */
	public static final String ADMIN_USER_CODE = "001";
	/** 2:领导 */
	public static final String LEADWER_USER_CODE = "002";
	/** 3:普通 */
	public static final String NORMAL_USER_CODE = "003";

	/** 1:管理员 */
	public static final String ADMIN_USER = "admin";
	/** 2:leader */
	public static final String LEADER_USER = "leader";
	/** 3:普通 */
	public static final String NORMAL_USER = "user";

	/** 权限Level 001~005 */
	public static final String LEVEL_001_CODE = "001";
	public static final String LEVEL_002_CODE = "002";
	public static final String LEVEL_003_CODE = "003";
	public static final String LEVEL_004_CODE = "004";
	public static final String LEVEL_005_CODE = "005";
	public static final String LEVEL_001 = "LEVEL_001";
	public static final String LEVEL_002 = "LEVEL_002";
	public static final String LEVEL_003 = "LEVEL_003";
	public static final String LEVEL_004 = "LEVEL_004";
	public static final String LEVEL_005 = "LEVEL_005";

	/** 999999:系统 */
	public static final String SYS_USER = "sys_user";
	public static final String SYS_USER_CODE = "999999";

	/** 登录认证token签名的字符串 */
	public static final String TOKEN_SECRET = "demo";

	/** login之后的access Token 载荷中存放的信息 */

	/** userid */
	public static final String TOKEN_CLAIM_USERID = "userid";
	/** login 时间戳 */
	public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";
	/** shiro token id */
	public static final String JSESSIONID = "sessionID";
	/** 会社コード */
	public static final String COMPANY_CODE = "companyCode";
	/** 账号类型 */
	public static final String ACCOUNT_TPYE = "accountType";
	/** email */
	public static final String EMAIL = "email";

	/**
	 * login返回时 Reaponse
	 * token请求头名称
	 */
	public static final String TOKEN_HEADER_NAME = "Authorization";

	public static final String TOKEN_ACCESS_CONTROL = "Access-Control-Expose-Headers";

	/**
	 * 认证access token过期时间 3 小时
	 */
	public static final Long ACCESS_TOKEN_EXPIRE_TIME = 3 * 60 * 60 * 1000L;

	/**
	 * redis 刷新refresh token过期时间 1天
	 */
	public static final Long REFRESH_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;

	/**
	 * zhaohui token过期时间 1 小时
	 */
	public static final Long FIND_PWD_TOKEN_EXPIRE_TIME = 1 * 60 * 60 * 1000L;

	/** 密码找回邮件 件名 */
	public static final String FIND_PWD_SUBJECT = "findPwd.subject";
	/** 密码找回邮件 内容*/
	public static final String FIND_PWD_CONTENT = "findPwd.content";

	/** 申请 件名 */
	public static final String FIND_REQ_SUBJECT = "reqmail.subject";
	/** 申请 内容*/
	public static final String FIND_REQ_CONTENT = "reqmail.content";

	/** 差戻 件名 */
	public static final String FIND_REMAND_SUBJECT = "remandmail.subject";
	/** 差戻 内容*/
	public static final String FIND_REMAND_CONTENT = "remandmail.content";
	/** 承認完了 件名 */
	public static final String FIND_APPROVAL_SUBJECT = "approvalmail.subject";
	/** 承認完了 内容*/
	public static final String FIND_APPROVAL_CONTENT = "approvalmail.content";

	/** 区分 */
	public static final String CATEGORY_030_001 = "001";
	public static final String CATEGORY_030_003 = "003";
	public static final String CATEGORY_031_010 = "010";
	public static final String CATEGORY_030_010 = "010";
	public static final String CATEGORY_033_021 = "021";
	public static final String CATEGORY_030_999 = "999";
	public static final String CATEGORY_033_025 = "025";
	public static final String CATEGORY_033_900 = "900";
	public static String FIND_req_SUBJECT;
}
