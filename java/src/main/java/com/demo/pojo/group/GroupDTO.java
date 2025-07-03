package com.demo.pojo.group;

import lombok.*;

import java.io.Serializable;

/*
 * Author  : deng
   Time    : 2021/08/06
   Function:
*/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;
    // 会社コード
    public String companyCode;
    // グループコード
    public String groupCode;
    // グループネーム
    public String groupName;
    // グループユーザーコード
    public String groupUserId;
    // グループユーザー名前
    public String userName;
    // グループユーザー部門名
    public String departmentName;
}
