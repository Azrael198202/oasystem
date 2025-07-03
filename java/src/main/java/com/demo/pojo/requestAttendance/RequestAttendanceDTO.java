package com.demo.pojo.requestAttendance;

import lombok.Data;

import java.util.Date;

@Data
public class RequestAttendanceDTO {

    private String companyCode;
    private String userId;
    private String workDateYm;
    private String requestNumber;
    private String regAccount;
    private Date reqTime;
    private String updAccount;
    private Date updTime;
}
