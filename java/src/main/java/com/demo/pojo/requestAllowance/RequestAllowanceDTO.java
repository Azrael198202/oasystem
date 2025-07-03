package com.demo.pojo.requestAllowance;
import lombok.Data;

import java.util.Date;

@Data
public class RequestAllowanceDTO {
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
    //    手当金額
    public Integer allowance;
    //    手当口数
    public Integer quantity;
    //    手当合計
    public Integer total;
    //    作番ID
    public String projectId;
    //    作番名
    public String projectName;
    //    資格ID
    public String qualificationId;
    //    資格受検日
    public String qualificationExamDate;
    //    添付画像
    public String image;
    public String contentType;
    //    備考１
    public String remark1;
    //    備考２
    public String remark2;
    //    備考３
    public String remark3;
    //    備考４
    public String remark4;
    //    備考５
    public String remark5;
    //    登録者
    public String regAccount;
    //    登録日時
    public Date regTime;
    //    更新者
    public String updAccount;
    //    更新日時
    public Date updTime;

}
