package com.demo.pojo.notice;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
public class NoticeRpnDTO implements Serializable {

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

    // 終了日時
    private String noticePublicationEndDate;

    // タイトル
    private String noticeTitle;

    // 新規期間
    private int noticeNewPeriod;

    // 添付１
    private String noticeAttachment1;

    // 添付２
    private String noticeAttachment2;

    // 添付３
    private String noticeAttachment3;

    // 添付１
    private String contentType1;

    // 添付２
    private String contentType2;

    // 添付３
    private String contentType3;
    // 内容
    private String noticeContents;
    //カテゴリー
    public String noticeCategory;
}
