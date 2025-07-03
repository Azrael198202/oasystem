package com.demo.pojo.attendance;

import lombok.Data;

@Data
public class ApplyAttendanceDTO  {
	public String companyCode;
	public String userId;
	private String workDate;
	public String workDateYm;
	private String workKbn;
	private String remark;
	private String requestStatusKbn;
}
