package com.demo.pojo.requestHoliday;

        import lombok.Data;

@Data
public class PaidHolidayHistoryDTO {
    // 会社コード
    public String companyCode;
    // ユーザーID
    public String requestUserId;
    // 申請番号
    public String requestNumber;
    // 休暇予定日
    public String holidayPlanDay;
    // 有給休暇区分
    public String paidHolidayKbn;
    // 区分名
    public String categoryKbnName;
    // 休暇日数
    public float holidayDays;
    // 今日の日付
    public String setDate;
}
