package com.demo.pojo.login;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Author  : baiye
   Time    : 2021/04/27 
   Function: 
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	// 会社コード
	public String companyCode;

	// 用户ID
	public String userId;

	// 密码
	public String passWord;

	// 记住我
	public boolean rememberMe;

	public boolean getRememberMe() {
		return rememberMe;
	}
}
