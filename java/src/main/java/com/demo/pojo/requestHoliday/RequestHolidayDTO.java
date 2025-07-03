package com.demo.pojo.requestHoliday;

import lombok.Data;

@Data
public class RequestHolidayDTO {

    public String companyCode;

    public String requestNumber;

    public Byte requestSubNumber;

    public String requestUserId;

    public String holidayTypeKbn;

    public String holidayPlanDay;

    public String holidayTimeKbn;

    public Float holidayDays;

    public String holidayReasonKbn;

    public String remark;
    // 休日出勤申請番号
    public String workDayoffRequestNumber;
    // 休日出勤日
    public String scheduledWorkDay;

}
