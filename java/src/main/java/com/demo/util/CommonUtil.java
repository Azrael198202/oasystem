package com.demo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.demo.pojo.common.LoginUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

import com.demo.common.Const;
import com.demo.common.ConstCode;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
@Component
public class CommonUtil {

	/**
	 * UUID生成
	 * 
	 * @return
	 */
	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取文件名后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public String getFileNameSuffix(String fileName) {

		if (!StringUtils.isAllBlank(fileName)) {
			String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
			return fileNameSuffix;
		}
		return ConstCode.STRING_EMPTY;
	}

	/**
	 * date 是否是30分钟之前的时间
	 * 
	 * @param date
	 * @return
	 */
	public boolean checkHalfHourDate(Date date) {

		Calendar dateOne = Calendar.getInstance();
		Calendar dateTwo = Calendar.getInstance();

		// 设置为当前系统时间
		dateOne.setTime(new Date());

		// 获取数据库中的时间
		dateTwo.setTime(date);

		long timeOne = dateOne.getTimeInMillis();
		long timeTwo = dateTwo.getTimeInMillis();

		long minute = (timeOne - timeTwo) / (ConstCode.NUM_1000 * ConstCode.NUM_60);// L转化minute

		if (minute > ConstCode.NUM_30) {
			return true;
		}
		return false;
	}

	/**
	 * request获取header中的token，取得companyCode userid
	 * 
	 * @param request
	 * @return
	 */
	public LoginUserInfo getUserInfoByToken(HttpServletRequest request) {

		try {
			// header 中的token
			String token = request.getHeader(Const.TOKEN_HEADER_NAME);
			// token中的info
			String companyCode = JwtUtil.getClaim(token, Const.COMPANY_CODE);
			String userId = JwtUtil.getClaim(token, Const.TOKEN_CLAIM_USERID);

			LoginUserInfo loginUserInfo = new LoginUserInfo();

			loginUserInfo.setCompanyCode(companyCode);
			loginUserInfo.setUserId(userId);

			return loginUserInfo;

		} catch (Exception e) {

			// TODO: handle exception
			throw new AuthenticationException();
		}
	}


}
