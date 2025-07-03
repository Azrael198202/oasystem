package com.demo.shiro.realm;

import com.demo.entity.UserMaster;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import com.demo.common.Const;
import com.demo.common.ResponseCode;
import com.demo.service.IUserService;
import com.demo.shiro.JwtToken;
import com.demo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
@Slf4j
public class JwtRealm extends AuthorizingRealm {

	@Autowired
	@Lazy
	private IUserService iUserService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		log.info("JwtRealm 不做赋权处理");

		// 不做赋权处理
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		
		// log.info("jwtRealm中关于身份验证的方法执行...传递的token是:{}", authenticationToken);
		String token = (String) authenticationToken.getCredentials();
		
		// 解密获得userid，用于和数据库进行对比
		String userid = JwtUtil.getClaim(token, Const.TOKEN_CLAIM_USERID);
		String companyCode = JwtUtil.getClaim(token, Const.COMPANY_CODE);
		
		if (userid == null) {
			throw new AuthenticationException(ResponseCode.TOKEN_EXCEPTION.getDesc());
		}
		
		UserMaster user = iUserService.getUserByKey(companyCode,userid);
		// L校验用户是否存在
		if (user == null) {
			throw new AuthenticationException(ResponseCode.ACCOUNT_UNUSUAL.getDesc());
		}
		// L操作时校验的是非对称加密是否成立.
		// if (!JwtUtil.verify(token, Const.TOKEN_SECRET)) {
		if (!JwtUtil.verify(token, Const.TOKEN_SECRET)) {
			// log.info("token校验无效...");
			throw new AuthenticationException(ResponseCode.TOKEN_EXCEPTION.getDesc());
		}

		try {
			// log.info("进行身份验证时,用户提供的token有效");
			return new SimpleAuthenticationInfo(user, token, getName());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw new AuthenticationException(ResponseCode.ACCOUNT_UNUSUAL.getDesc());
		}

	}
}
