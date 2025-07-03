package com.demo.pojo.group;

import lombok.*;

import java.io.Serializable;

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
public class GroupMemberInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;
    private String companyCode;

    private String groupCode;

    private String groupUserId;

}
