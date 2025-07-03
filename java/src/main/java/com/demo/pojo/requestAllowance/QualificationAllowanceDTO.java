package com.demo.pojo.requestAllowance;
import lombok.Data;

import java.util.Date;

@Data
public class QualificationAllowanceDTO {
    //    会社コード
    public String companyCode;
    //    年月
    public String applyYm;
    //    申請番号
    public String requestNumber;
    //    申請種類区分
    public String requestTypeKbn;
    //    申請者
    public String requestUserId;
    //    資格ID
    public String qualificationId;
    //    資格受検日
    public String qualificationExamDate;
    //    添付画像
    public String image;
    public String contentType;

    //    手当金額
    public Integer allowance;
    //    手当合計
    public Integer total;
    //    登録者
    public String regAccount;
    //    登録日時
    public Date regTime;
    //    更新者
    public String updAccount;
    //    更新日時
    public Date updTime;

}
