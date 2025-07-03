package com.demo.pojo.resume;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/*
 * Author  : libin
   Time    : 2022/07/11
   Function:
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchResumeDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // 会社コード
    private String companyCode;

    // ユーザーID
    private String userId;

    // ユーザー名
    private String userName;

    // 経歴書バージョン区分
    private String resumeVerKbn;

    // 経歴書バージョン区分名称
    private String resumeVerName;

    // 備考
    private String remarks;

    // 更新日時
    private String updTime;

}
