package com.demo.pojo.wf;

import lombok.Data;

import java.util.Date;

@Data
public class InsertNewWfDTO {
    //    会社コード
    public String companyCode;
    //    申請番号
    public String requestNumber;
    //    申請者ID
    public String reqRequestUserId;
    //    承認者ID
    public String reqApproveUserId;
    // コメント欄
    public String reqAppComment;
    //    既存の申請データ件数
    public String total;

}
