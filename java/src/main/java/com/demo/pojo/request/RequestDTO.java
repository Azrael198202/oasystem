package com.demo.pojo.request;

import lombok.Data;

@Data
public class RequestDTO {

    public String companyCode;

    public String requestNumber;

    public String requestUserId;

    public String requestStatusKbn;

    public String requestDate;

    public String requestTypeKbn;

    public String requestOverview;
//申請種類の名称
    public String requestTypeName;
//状態の名称
    public String statusKbnName;
//    年月(yyyyMM)
    public String workDateYm;
    //休暇申請内容テーブルの休暇種類区分
    public String holidayTypeKbn;

    public String userName;

    public String departmentCode;

    public String departmentName;

}
