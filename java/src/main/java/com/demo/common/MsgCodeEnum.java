package com.demo.common;

/*
 * Author  : LI
   Time    : 2021/07/27
   Function: 
*/
public enum MsgCodeEnum {

	I_0001("I_0001", "出勤時間１を再入力しますか？"),

	I_0002("I_0002", "出勤時間２として登録しますか？"),

	I_0003("I_0003", "出勤時間2を再入力しますか？"),

	I_0004("I_0004", "出勤時間３として登録しますか？"),

	I_0005("I_0005", "出勤時間3を再入力しますか？"),
	
	I_0006("I_0006", "既に退勤時間が入力されています。"),

	I_0007("I_0007", "出勤が押下されていません。勤務入力より退勤時間を入力してください。"),

	I_0008("I_0008", "退勤時間3を再入力しますか？"),

	I_0009("I_0009", "退勤時間２を再入力しますか？"),

	I_0010("I_0010", "退勤時間１を再入力しますか？");

	private final String code;
	private final String message;

	MsgCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
