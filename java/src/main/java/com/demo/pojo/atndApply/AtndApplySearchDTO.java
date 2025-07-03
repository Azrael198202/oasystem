package com.demo.pojo.atndApply;

import lombok.*;

import java.io.Serializable;

/*
 * Author  : libin
   Time    : 2021/07/13
   Function:
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AtndApplySearchDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // 会社コード
    public String companyCode;

    // 用户ID
    public String userId;

    // 申請年月
    private String applyYm;

    // 申請区分
    private String applyKbn;

    // 申請状態
    private String applyStatus;
}
