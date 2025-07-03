package com.demo.pojo.monthCloseHistory;

import lombok.Data;

@Data
public class PaidHolidayHistoryMonthDTO {
    //    前月
    public String lastMonth;
    //    今月
    public String thisMonth;
    //    翌月
    public String nextMonth;
    //    翌々月
    public String monthAfterNext;

}
