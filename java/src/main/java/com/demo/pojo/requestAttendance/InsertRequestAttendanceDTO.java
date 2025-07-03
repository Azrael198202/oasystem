package com.demo.pojo.requestAttendance;

import lombok.Data;

import java.util.Date;

@Data
public class InsertRequestAttendanceDTO {
    // 会社コード
    public String companyCode;
    public String userId;
    public String workDateYm;
    public String requestNumber;

}
