package com.demo.pojo.atndStting;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
public class AtndSttingRpnDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	private String companyCode;

	private String userId;

	private String applicationDate;

	private String workPrescribedTime;

	private Byte workEveryTime;

	private String workStartTime;

	private String workEndTime;

	private String workBreakStart1;

	private String workBreakEnd1;

	private String workBreakStart2;

	private String workBreakEnd2;

	private String workBreakStart3;

	private String workBreakEnd3;
}
