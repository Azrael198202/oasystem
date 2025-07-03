package com.demo.pojo.requestAttendance;

import lombok.Data;

@Data
public class RequestAttendanceReqDTO {
    // 会社コード
    public String companyCode;
    public String userId;
    public String workDateYm;
    public String requestNumber;
    public String requestStatusKbn;
}
