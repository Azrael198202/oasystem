package com.demo.pojo.login;

import java.io.Serializable;

import com.demo.pojo.common.LoginUserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
@Data
@AllArgsConstructor
public class SimpleUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3907955873574622437L;

	public String token;

	public LoginUserInfo loginUserInfo;
}
