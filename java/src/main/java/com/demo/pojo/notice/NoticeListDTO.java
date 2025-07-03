package com.demo.pojo.notice;

import lombok.*;

import java.io.Serializable;

/*
 * Author  : libin
   Time    : 2021/07/15
   Function:
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeListDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // 会社コード
    private String companyCode;

    // お知らせ番号
    private String noticeNumber;

    // 掲載日時
    private String noticePublicationStartDate;

    // タイトル
    private String noticeTitle;

}
