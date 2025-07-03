package com.demo.pojo.attendance;

import lombok.*;

import java.io.ByteArrayOutputStream;
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
public class AttendancePdfFileDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	public byte[] byteData;
	// フィアル名
	public String fileName;
}
