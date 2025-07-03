package com.demo.pojo.attendance;

import lombok.*;

import java.io.Serializable;

/*
 * Author  : libin
   Time    : 2021/07/12
   Function: 
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	// 会社コード
	public String companyCode;

	// 用户ID
	public String userId;

	// 年月(yyyyMM)
	public String workDateYm;
}
