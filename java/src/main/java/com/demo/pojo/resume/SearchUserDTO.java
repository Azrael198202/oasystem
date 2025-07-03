package com.demo.pojo.resume;

import lombok.*;

import java.io.Serializable;

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
public class SearchUserDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // 会社コード
    private String companyCode;

    // os
    private String os;

    // os区分
    private String osKbn;

    // pg
    private String pg;

    // 区分区分
    private String pgKbn;

    // db
    private String db;

    // db区分
    private String dbKbn;

    // ect
    private String etc;

    // ect区分
    private String etcKbn;

    private String requirementsFlg;

    private String basicFlg;

    private String specificFlg;

    private String codingFlg;

    private String unitTestFlg;

    private String interfaceTestFlg;

    private String systemTestFlg;

    // 履歴書無し
    private String resumeNullFlg;

    private String age;

    private String experienceYears;

    private String responsible;

    private String userIdName;

    private String departmentCode;

}
