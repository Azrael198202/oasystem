package com.demo.pojo.paidHolidayCorrect;
import lombok.Data;

import java.util.Date;

@Data
public class DefaultPaidHolidayCorrectDTO {
    private String companyCode;

    private String userId;

    private String changeDate;

    private String subNumber;

    private String correctDate;

    private String correctKbn;

    private Float correctDays;

    private String correctReason;

    private String correctRemark;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    private String dateYm;

    private String assignmentYear;

    private Float updateDays;

    private String status;


}
