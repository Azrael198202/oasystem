package com.demo.pojo.group;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/*
 * Author  : li
   Time    : 2021/08/06
   Function:
*/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;
    // 会社コード
    public String companyCode;
    // 用户ID
    public String userId;
    // グループコード
    public String groupCode;
    // グループネーム
    public String groupName;

    public List<GroupMemberInfo> groupMember;


}
