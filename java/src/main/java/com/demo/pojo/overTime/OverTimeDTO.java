package com.demo.pojo.overTime;

import lombok.Data;

import java.util.Date;

@Data
public class OverTimeDTO {
    public String companyCode;
    public String requestNumber;
    public String requestUserId;
    public String workTypeKbn;
    public String scheduledOvertimeDay;
    public Date startTime;
    public Date endTime;
    public Date estimatedWorkHours;
    public String overtimeReasonKbn;
    public String remark;
    public String registration_user;
    public Date registration_datetime;
    public String update_user;
    public Date update_datetime;
}
