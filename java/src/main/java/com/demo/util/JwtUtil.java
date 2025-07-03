package com.demo.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.common.Const;

import lombok.extern.slf4j.Slf4j;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
/**
 * 需要注意的是 这边校验的方式并非非对称加密 可以改造为非对称加密: 公钥加密，私钥解密 私钥生成签名，公钥验证签名
 */
@Slf4j
public class JwtUtil {

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * 
	 * @return token中包含的用户名
	 */
	public static String getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(Const.TOKEN_CLAIM_USERID).asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 校验token是否正确
	 *
	 * @param token  密钥
	 * @param secret 用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String secret) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim(Const.TOKEN_CLAIM_USERID, getUserId(token))
					.build();
			// 效验TOKEN
			verifier.verify(token);
			return true;
		} catch (JWTVerificationException exception) {
			return false;
		}
	}

	/**
	 * 登录成功认证token 过期时间三小时
	 *
	 * @param userid          用户ID
	 * @param companyCode     会社コード
	 * @param currentTimeMillis 系统时间
	 * @param sessionID         shiro session ID
	 * @param secret            制作此token的签名依据
	 * @return 加密的token
	 */
	public static String loginSign(String userid, String companyCode, String currentTimeMillis, String sessionID,
			String secret) {

		Date date = new Date(SysTimeUtil.getTime() + Const.ACCESS_TOKEN_EXPIRE_TIME);

		Algorithm algorithm = Algorithm.HMAC256(secret);

		// userid currentTimeMillis
		return JWT.create()
				.withClaim(Const.TOKEN_CLAIM_USERID, userid)
				.withClaim(Const.COMPANY_CODE, companyCode)
				.withClaim(Const.CURRENT_TIME_MILLIS, currentTimeMillis)
				.withClaim(Const.JSESSIONID, sessionID)
				.withExpiresAt(date).sign(algorithm);
	}

	/**
	 * 获得Token中的信息无需secret解密也能获得
	 *
	 * @param token
	 * @param claim
	 */
	public static String getClaim(String token, String claim) {
		try {

			DecodedJWT jwt = JWT.decode(token);

			// 只能输出String类型，如果是其他类型返回null
			return jwt.getClaim(claim).asString();

		} catch (JWTDecodeException e) {

			log.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * 登录成功认证token 过期时间一小时
	 *
	 * @param userid          用户ID
	 * @param companyCode     会社コード
	 * @param email           メール
	 * @return 加密的token
	 */
	public static String findPwdSign(String userid, String companyCode, String email) {

		// 过期时间一小时
		Date date = new Date(SysTimeUtil.getTime() + Const.FIND_PWD_TOKEN_EXPIRE_TIME);

		// 盐值
		Algorithm algorithm = Algorithm.HMAC256(Const.TOKEN_SECRET);

		// 制作此token的签名依据
		return JWT.create()
				.withClaim(Const.TOKEN_CLAIM_USERID, userid)
				.withClaim(Const.COMPANY_CODE, companyCode)
				.withClaim(Const.EMAIL, email)
				.withExpiresAt(date).sign(algorithm);
	}
}
