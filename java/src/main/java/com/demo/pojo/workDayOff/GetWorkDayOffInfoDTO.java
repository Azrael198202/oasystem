package com.demo.pojo.workDayOff;

import lombok.Data;

import java.util.Date;

@Data
public class GetWorkDayOffInfoDTO {
    public String companyCode;
    public String requestUserId;
    public String requestNumber;
    public String requestStatusKbn;
    public String requestTypeKbn;
    public String scheduledWorkDay;
    public String workTimeKbn;
}
