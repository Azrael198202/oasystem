package com.demo.pojo.workDayOff;
import lombok.Data;

import java.util.Date;

@Data
public class WorkDayOffSearchDTO {
    public String companyCode;
    public String userId;
    public String scheduledWorkDay;
}
