package com.demo.pojo.requestAllowance;
import lombok.Data;

import java.util.Date;

@Data
public class GetApplyYmDTO {
    //    会社コード
    public String companyCode;
    //    年月
    public String applyYm;
    //    申請者
    public String requestUserId;
    //    申請種類区分
    public String requestTypeKbn;
}
