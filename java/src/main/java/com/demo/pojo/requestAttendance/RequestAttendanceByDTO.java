package com.demo.pojo.requestAttendance;

import lombok.Data;

import java.util.Date;

@Data
public class RequestAttendanceByDTO {

    public String companyCode;
    public String userId;
    public String workDateYm;

}
