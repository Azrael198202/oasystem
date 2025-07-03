package com.demo.pojo.common;

import lombok.Data;

import java.util.List;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function:  用户信息
*/
@Data
public class LoginUserInfo {

	// companyCode
	private String companyCode;

	// id
	private String userId;

	// 用户名
	private String userName;

	// 账号类型
	private String accountKbn;

	// 权限类型
	private String permissionKbn;

	private String imageUrl;
}
