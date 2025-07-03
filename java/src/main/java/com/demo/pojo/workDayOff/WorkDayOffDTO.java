package com.demo.pojo.workDayOff;

import lombok.Data;

import java.util.Date;

@Data
public class WorkDayOffDTO {
    public String companyCode;
    public String requestNumber;
    public String requestUserId;
    public String workTypeKbn;
    public String scheduledWorkDay;
    public String workTimeKbn;
    public String workReasonKbn;
    public String remark;
    public String registrationUser;
    public Date registrationDatetime;
    public String updateUser;
    public Date updateDatetime;
}
