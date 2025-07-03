package com.demo.pojo.exchangedWorkDay;

import lombok.Data;

import java.util.Date;

@Data
public class ExchangedWorkDayDTO {
    public String companyCode;
    public String requestNumber;
    public String requestUserId;
    public String workTypeKbn;
    public String scheduledWorkDay;
    public String workTimeKbn;
    public String dayoffExchanged;
    public String dayoffTimeKbn;
    public String dayoffReasonKbn;
    public String remark;
    public String registrationUser;
    public Date registrationDatetime;
    public String updateUser;
    public Date updateDatetime;
}
