package com.demo.pojo.monthCloseHistory;

import lombok.Data;

@Data
public class MonthCloseHistoryDTO {

    //    会社コード
    public String companyCode;
    //    ユーザID
    public String userId;
    //    対象年月
    public String closeMonth;
    //    締処理日
    public String closeDay;
    //    処理ID
    public String processId;
    //    結果区分
    public String processResult;

}
