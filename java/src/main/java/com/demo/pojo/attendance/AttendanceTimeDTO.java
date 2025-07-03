package com.demo.pojo.attendance;

import lombok.*;

import java.io.Serializable;

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
public class AttendanceTimeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	private String workStart1;

	private String workEnd1;

	private String workStart2;

	private String workEnd2;

	private String workStart3;

	private String workEnd3;

}
