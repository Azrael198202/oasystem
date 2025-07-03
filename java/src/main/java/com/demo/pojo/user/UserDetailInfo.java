package com.demo.pojo.user;

import lombok.Data;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 普通ユーザー情報
*/
@Data
public class UserDetailInfo {
    // 会社コード
    private String companyCode;
    // 会社名
    private String companyName;
    // 会社名略称
    private String companyNameSh;
    // 部門名
    private String departmentName;
    // 部門名略称
    private String departmentNameSh;
    // 職称コード
    private String positionCode;
    // 部門コード
    private String departmentCode;

    // ユーザーID
    private String userId;
    // メールアドレス
    private String email;
    // 利用者区分
    private String accountKbn;
    // 権限区分
    private String permissionKbn;
    // 雇用形態区分
    private String employmentKbn;
    // 姓
    private String firstName;
    // 名
    private String lastName;
    // 姓(カナ)
    private String firstNameKana;
    // 名(カナ)
    private String lastNameKana;
    // 生年月日
    private String birthday;
    // 性別
    private String sex;
    // 電話番号
    private String tel;
    // 郵便番号
    private String zip;
    // 住所（都道府県）
    private String addressPrefecture;
    // 住所（市区町村）
    private String addressMunicipality;
    // 住所（丁名・番地）
    private String addressTown;
    // 住所（部屋番号）
    private String addressDetail;
    // 入社年月
    private String joinDate;
    // 退社年月
    private String leaveDate;
}
