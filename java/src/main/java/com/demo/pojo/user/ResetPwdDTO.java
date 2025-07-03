package com.demo.pojo.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPwdDTO implements Serializable {

    private static final long serialVersionUID = 4405150925487216730L;

    // パスワード
    private String oldPwd;

    // Access_Token
    private String token;

    // 新パスワード
    private String newPwd;

    // パスワード確認
    private String confirmPwd;

}
