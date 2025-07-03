package com.demo.pojo.user;


import lombok.Data;

//ユーザー残有給数情報
@Data
public class UserRemainHolidayDTO {
    // 会社コード
    private String companyCode;
    // ユーザーID
    private String userId;
    // 名前
    private String userName;
    //部門コード
    private String departmentCode;
    //締め月
    private String closeMonth;
    //残有給数
    private String paidHolidayRemaining;
    //有給開始年
    private String paidHolidayStartYear;
    //勤務歴（年）
    private String workHistoryYear;
    //有給開始月
    private String paidHolidayStartMonth;
    //次有給付与月
    private String nextAssignmentMonth;

}
