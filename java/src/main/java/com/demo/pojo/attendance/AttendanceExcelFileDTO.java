package com.demo.pojo.attendance;

import lombok.*;

import java.io.ByteArrayOutputStream;
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
public class AttendanceExcelFileDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2258512194666525660L;

	public ByteArrayOutputStream fileContent;
	// フィアル名
	public String fileName;
}
