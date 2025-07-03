package com.demo.pojo.atndStting;

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
public class AtndSttingDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	// 会社コード
	public String companyCode;

	// 用户ID
	public String userId;

	// 適用開始日
	public String applicationDate;
}
