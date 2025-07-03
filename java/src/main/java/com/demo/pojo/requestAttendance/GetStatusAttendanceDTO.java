package com.demo.pojo.requestAttendance;

import lombok.Data;

@Data
public class GetStatusAttendanceDTO {
    public String companyCode;
    public String userId;
    public String workDateYm;
    public String requestNumber;
    public String requestStatusKbn;
    public String requestTypeKbn;
    public String requestOverview;
}
