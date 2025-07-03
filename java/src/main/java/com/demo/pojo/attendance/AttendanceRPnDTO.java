package com.demo.pojo.attendance;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/*
 * Author  : libin
   Time    : 2021/07/13
   Function: 
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceRPnDTO implements Serializable {

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

	// 日付(yyyyMMdd)
	private String workDate;

	// 曜日
	private String workDateWeek;

	// 勤怠区分
	private String workKbn;

	// 労働時間
	private String workTime;

	// 備考
	private String remark;

	// 日報入力フラグ
	private String sendReportFlg;

	// 部門名
	private String departmentName;

	// 名前
	private String userName;

	private String workStart1;

	private String workEnd1;

	private String workStart2;

	private String workEnd2;

	private String workStart3;

	private String workEnd3;

	private String workBreakTime1;

	private String workBreakTime2;

	private String workBreakTime3;

	private String workOverTime;

	private String workMidnightTime;

	private String workHoliTime;

	private String workMidnightHoliTime;

	private String workSumDate;

	private String workSumTime;

	private String WorkSumHoliTime;

	private String holidayFlg;

	private String accountKbn;

	private String permissionKbn;

	private String employmentKbn;

}
