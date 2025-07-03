package com.demo.pojo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 普通ユーザー登録
*/
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -4371193837464516163L;

    // 会社コード
    private String companyCode;
    // ユーザーID
    private String userId;
    // 名前
    private String userName;
    // 適用開始日
    private String startDate;
    // パスワード
    private String passWord;
    // メールアドレス
    private String email;
    // 利用者区分
    private String accountKbn;
    // 権限区分
    private String permissionKbn;
    // 雇用形態区分
    private String employmentKbn;
    // 更新者
    private String updAccount;
    // 更新時間
    private Date updTime;
}
