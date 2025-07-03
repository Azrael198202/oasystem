package com.demo.pojo.wf;

import lombok.Data;

@Data
public class SelectWfDTO {
    public String companyCode;
    public String registrationDatetime;
    public String requestNumber;
    public String reqAppOrder;
    public String reqAppUserId;
    public String reqAppKbn;
    public String wfStatusKbn;
    public String requestTypeKbn;
    public String requestUserId;
    public String requestDate;
    public String requestOverview;
    public String requestKbnName;
    public String recognizeKbnName;
    public String userName;
    public String workDateYm;
    public String holidayTypeKbn;
    public String holidayDays;
    public String departmentCode;
    public String departmentName;
    public String requestStatusKbn;
    public String statusKbnName;
    // 選択期間
    public String startDate;
    public String endDate;
    // 社員コード
    public String userId;
}
