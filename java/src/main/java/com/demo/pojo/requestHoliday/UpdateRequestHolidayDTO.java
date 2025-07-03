package com.demo.pojo.requestHoliday;

        import lombok.Data;

@Data
public class UpdateRequestHolidayDTO {
    // 会社コード
    public String companyCode;
    // 申請番号
    public String requestNumber;
    // 枝番
    public Byte requestSubNumber;
    // ユーザーID
    public String requestUserId;
    // 休暇申請種類
    public String holidayTypeKbn;
    // 休暇予定日
    public String holidayPlanDay;
    // 休暇時刻区分
    public String holidayTimeKbn;
    // 休暇日数
    public Float holidayDays;
    // 休暇理由区分
    public String holidayReasonKbn;
    // 備考
    public String remark;
    // 休日出勤申請番号
    public String workDayoffRequestNumber;
}
