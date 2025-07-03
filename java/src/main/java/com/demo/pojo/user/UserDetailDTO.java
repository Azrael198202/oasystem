package com.demo.pojo.user;

import lombok.Data;

import java.util.Date;

/*
 * Author  : Li
   Time    : 2021/07/27
   Function: 普通ユーザー情報
*/
@Data
public class UserDetailDTO {
    // 会社コード
    private String companyCode;

    // 会社名
    private String companyName;

    // ユーザーID
    private String userId;

    // 姓名
    private String userName;

    // 入社年月
    private String joinDate;

    private String countYear;
    private String year;
    private String month;
}
