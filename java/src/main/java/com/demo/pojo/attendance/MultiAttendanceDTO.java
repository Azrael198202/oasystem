package com.demo.pojo.attendance;

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
public class MultiAttendanceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	// 会社コード
	public List<String> companyCodeList;

	// 用户ID
	public List<String> userIdList;

	// 年月(yyyyMM)
	public List<String> workDateYmList;

	// 勤怠申請状態
	public List<String> requestStatusKbnList;

}
