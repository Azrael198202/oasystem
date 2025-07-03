package com.demo.common;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
public class ConstCode {

	/** 字符串：0. */
	public static final String STRING_ZERO = "0";

	/** 字符串：1. */
	public static final String STRING_ONE = "1";

	/** 字符串：2. */
	public static final String STRING_TWO = "2";

	/** 字符串：3. */
	public static final String STRING_THREE = "3";

	/** 字符串：4. */
	public static final String STRING_FOUR = "4";

	/** 字符串：5. */
	public static final String STRING_FIVE = "5";

	/** 字符串：6. */
	public static final String STRING_SIX = "6";

	/** 字符串：7. */
	public static final String STRING_SEVEN = "7";

	/** 字符串：8. */
	public static final String STRING_EIGHT = "8";

	/** 字符串：9. */
	public static final String STRING_NINE = "9";

	/** 字符串：10. */
	public static final String STRING_TEN = "10";
	
	/** 数字：0.5 */
	public static final float NUM_TEN_5 = 0.5f;

	/** 数字：0. */
	public static final int NUM_0 = 0;

	/** 数字：1. */
	public static final int NUM_1 = 1;

	/** 数字：2. */
	public static final int NUM_2 = 2;

	/** 数字：3. */
	public static final int NUM_3 = 3;

	/** 数字：4. */
	public static final int NUM_4 = 4;

	/** 数字：5. */
	public static final int NUM_5 = 5;

	/** 数字：6. */
	public static final int NUM_6 = 6;

	/** 数字：7. */
	public static final int NUM_7 = 7;

	/** 数字：8. */
	public static final int NUM_8 = 8;

	/** 数字：9. */
	public static final int NUM_9 = 9;

	/** 数字：10. */
	public static final int NUM_10 = 10;

	/** 数字：11. */
	public static final int NUM_11 = 11;

	/** 数字：12. */
	public static final int NUM_12 = 12;

	/** 数字：13. */
	public static final int NUM_13 = 13;

	/** 数字：14. */
	public static final int NUM_14 = 14;

	/** 数字：15. */
	public static final int NUM_15 = 15;

	/** 数字：16. */
	public static final int NUM_16 = 16;

	/** 数字：17. */
	public static final int NUM_17 = 17;

	/** 数字：18. */
	public static final int NUM_18 = 18;

	/** 数字：19. */
	public static final int NUM_19 = 19;

	/** 数字：20. */
	public static final int NUM_20 = 20;

	/** 数字：30. */
	public static final int NUM_30 = 30;

	/** 数字：60. */
	public static final int NUM_60 = 60;

	/** 数字：100. */
	public static final int NUM_100 = 100;

	/** 数字：1000. */
	public static final int NUM_1000 = 1000;

	/** 字符串：空. */
	public static final String STRING_EMPTY = "";
	/** 字符串：/. */
	public static final String STRING_SLASH = "/";
	/** 字符串：\\. */
	public static final String STRING_DOUBLE_BACK_SLASH = "\\";
	/** 字符串：,. */
	public static final String STRING_COMMA = ",";
	/** 字符串：,: */
	public static final String STRING_COLON = ":";
	/** 字符串：-. */
	public static final String STRING_DASH = "-";
	/** 字符串：% */
	public static final String STRING_PERCENT = "%";
	/** 字符串：null. */
	public static final String STRING_NULL = null;
	/** SJIS编码. */
	public static final String SJIS = "SJIS";
	/** UTF8编码. */
	public static final String UTF8 = "UTF-8";
	/** 符号：✓. */
	public static final String SIGN_CHECK = "✓";

	/** 申請種類区分*/
	public class RequestTypeKbn {
		/**
		 * 出張手当
		 */
		public static final String ALLOWANCE＿BUSINESS_TRIP = "043";
		/**
		 * リーダー手当
		 */
		public static final String ALLOWANCE＿PROJRCT = "042";
		/**
		 * 資格手当
		 */
		public static final String ALLOWANCE＿QUALIFICATION = "041";
	}

	public class IMAGE_RELATION
	{

		//---以下Windows環境（個人ローカルのイメージ画像パス関係）-------
		public static final String ACCESS_MODE = "LOCAL";
		//  ローカル用カレント（各自環境に合わせてください）
		public static final String CURRENT_DIRECTORY = "D:/workspace/vue.js";
		//  ローカル用ドメイン
		public static final String DOMAIN_URL = "http://localhost:8989";
		//---以上Windows環境（個人ローカルのイメージ画像パス関係）-------
		//---以上Server環境（テスト環境、本番環境のイメージ画像パス関係）-------

/*
		//---以下Server環境（テスト環境、本番環境のイメージ画像パス関係）-------
		public static final String ACCESS_MODE = "SERVER";
		//　カレントディレクトリ
		public static final String CURRENT_DIRECTORY = "/home/leadingwin_oa";
		//　ドメイン（各自変更してください：テスト環境の場合は「https://oatest.leadingwin.net」、本番は「https://oa.leadingwin.net」、ローカルは「http://localhost:8989」）
		public static final String DOMAIN_URL = "https://oatest.leadingwin.net";
*/


		public static final String ACCESS_MODE_SERVER = "SERVER";
		public static final String ACCESS_MODE_LOCAL = "LOCAL";
		//  ローカルやサーバー用月末精算Imgパス(static配下しないと参照できないため)
		public static final String IMG_DIRECTORY_TRANSPORT = "/static/user/lw/upload/transportationexpenses/";
		//  ローカルやサーバー一時用月末精算Imgパス(static配下しないと参照できないため)
		public static final String IMG_TEMPORARY_TRANSPORT = "static/user/lw/upload/temporary/";
		//  ローカルやサーバー用お知らせImgパス(static配下しないと参照できないため)
		public static final String IMG_DIRECTORY_NOTICE = "/static/user/lw/upload/notice/";
		//  ローカルやサーバー用資格Imgパス(static配下しないと参照できないため)
		public static final String IMG_DIRECTORY_QUALIFICATION = "/static/user/lw/upload/qualification/";
		//  ローカルやサーバー用資格Imgパス(static配下しないと参照できないため)
		public static final String IMG_CONGRATULATORY_OR_CONDOLENCEPAYMENTS = "/static/user/lw/upload/CongratulatoryOrCondolencePayments/";

	}

	/**
	 * 申請状態区分（030）
	 */
	public class REQUEST_STATUS_KBN
	{
		/*保存*/
		public static final String SAVE = "001";
		/*回覧中*/
		public static final String CIRCULATING = "003";
		/*差戻*/
		public static final String SENDBACK = "010";
		/*完了*/
		public static final String COMPLETE = "999";
	}

	/**
	 * 申請種類区分（031）
	 */
	public class REQUEST_KIND_KBN
	{
		/*勤怠申請*/
		public static final String ATTENDANCEAPPLICATION = "010";
		/*休暇申請*/
		public static final String LEAVEAPPLICATION = "020";
		/*休日出勤申請*/
		public static final String APPLICATIONFORHOLIDAYWORK = "021";
		/*深夜残業申請*/
		public static final String LATENIGHTOVERTIMEAPPLICATION = "022";
		/*振休出勤申請*/
		public static final String APPLICATIONFORWORKHOLIDAY = "023";
		/*精算申請*/
		public static final String SETTLEMENTAPPLICATION = "030";
		/*月末精算申請*/
		public static final String MONTHENDSETTLEMENTAPPLICATION = "031";
		/*旅費精算申請*/
		public static final String ENDOFMONTHTRAVELEXPENSESETTLEMENTAPPLICATION = "032";
		/*慶弔舞金申請*/
		public static final String CONGRATULATORYANDCONDOLENCEMONEYAPPLICATIONSETTLEMENTAPPLICATION = "040";
		/*資格手当申請*/
		public static final String APPLICATIONFORQUALIFICATIONALLOWANCE = "041";
		/*リーダー手当申請*/
		public static final String APPLICATIONFORLEADERALLOWANCE = "042";
		/*出張手当申請*/
		public static final String APPLICATIONFORTRAVELALLOWANCE = "043";
		/*その他申請*/
		public static final String OTHERAPPLICATION = "090";

	}
	/**
	 * 申請承認区分（032）
	 */
	public class REQUEST_APPROVE_KBN
	{
		/*申請*/
		public static final String APPLY = "001";
		/*承認*/
		public static final String APPROVE = "002";
	}

	/**
	 * ワークフロー状態区分（033）
	 */
	public class WKFL_STATUS_KBN
	{
		/*取戻*/
		public static final String TAKEBACK = "011";
		/*申請済み*/
		public static final String APPLIED = "012";
		/*未承認*/
		public static final String UNAPPROVED = "020";
		/*差戻*/
		public static final String SENDBACK = "021";
		/*承認済*/
		public static final String APPROVED = "025";
		/*承認完了*/
		public static final String APPROVEDCOMPLETE = "900";
	}

}
