package com.demo.pojo.subHoliday;

import lombok.Data;

import java.util.Date;

@Data
public class SubHolidayDTO {

    public String companyCode;

    public String userId;

    public String holidayWorkDay;

    public Float subHolidayDays;

    public String requestNumber;

    public String registrationUser;

    public Date registrationDatetime;

    public String updateUser;

    public Date updateDatetime;
    //    休日出勤時刻区分
    public String workTimeKbn;
    //    今日の日付
    public String setDate;
    //     休日出勤申請内容テーブル用（新規：1　更新：2）
    public String createOrUpdateKbn;
}
