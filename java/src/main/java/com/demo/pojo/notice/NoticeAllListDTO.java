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
public class NoticeAllListDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // 会社コード
    public String companyCode;

    //UserID
    public String requestUserId;

    // お知らせ番号
    public String noticeNumber;

    //カテゴリー
    public String noticeCategory;

    // 掲載日時
    public String noticePublicationStartDate;

    // 終了日時
    public String noticePublicationEndDate;

    // タイトル
    public String noticeTitle;

    // 新規期間
    public Integer noticeNewPeriod;

    // 添付１
    public String noticeAttachment1;

    // 添付２
    public String noticeAttachment2;

    // 添付３
    public String noticeAttachment3;

    // 内容
    public String noticeContents;

    //更新日時
    public String updateDatetime;

    //登録者ID
    public String registrationUserId;

    //登録者名
    public String registrationUserName;

    //お知らせ状態
    public String noticeStatus;

    //社員情報（検索用）
    public String userIdOrName;

    // 添付１
    private String contentType1;

    // 添付２
    private String contentType2;

    // 添付３
    private String contentType3;

}
