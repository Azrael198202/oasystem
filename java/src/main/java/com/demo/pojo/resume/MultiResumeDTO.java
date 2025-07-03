package com.demo.pojo.resume;

import lombok.*;

import java.io.Serializable;
import java.util.List;

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
public class MultiResumeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	// 会社コード
	public String companyCode;

	// 用户ID
	public List<String> userIdList;


}
