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
public class GetUserDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // 会社コード
    private String companyCode;

    // 社員番号
    private String userId;

    // 所属
    private String departmentName;

    // 社員名
    private String userName;

    // 年齢
    private String age;

}
