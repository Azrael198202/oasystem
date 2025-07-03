package com.demo.pojo.requestAttendance;

import lombok.Data;

@Data
public class UpdateNumRequestAttendanceDTO {
    public String companyCode;
    public String userId;
    public String workDateYm;
    public String requestNumber;

}

