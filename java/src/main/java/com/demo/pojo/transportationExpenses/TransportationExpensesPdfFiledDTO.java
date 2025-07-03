package com.demo.pojo.transportationExpenses;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransportationExpensesPdfFiledDTO {

    public String apply_month;           // 申請月

    public String apply_date;           // 申請日

    public String department_name;      // 所属

    public String employee_no;          // 社員番号

    public String name;                 // 社員名

    public String address;              // 住所

    public BigDecimal total_amount;     // 総計

    public String request_number;       // 申請番号

    public String request_status_kbn;       // 申請状態
}
