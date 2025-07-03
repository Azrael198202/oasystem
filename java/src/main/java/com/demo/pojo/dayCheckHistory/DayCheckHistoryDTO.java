package com.demo.pojo.dayCheckHistory;

import lombok.Data;

@Data
public class DayCheckHistoryDTO {

    public String companyCode;

    public String userId;

    public String checkDay;

    public String branchNumber;

    public String errCd;

    public String errMessage;

    public String registrationUser;

    public String registrationDatetime;

    public String updateUser;

    public String updateDatetime;
    //    社員名
    public String userName;

}
