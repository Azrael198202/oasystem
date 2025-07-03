package com.demo.pojo.paidHoliday;
import lombok.Data;
import java.util.Date;
@Data
public class PaidHolidayMaintenanceDTO {
    //会社コード
    private String companyCode;
    //ユーザーID
    private String userId;
    //社員名
    private String userName;
    //部門コード
    private String departmentCode;
    //部門名
    private String departmentName;
    //入社日
    private String joinDate;
    //有休付与開始日
    private String paidHolidayStartDay;
    //入社年数
    private String nextPaidHolidayFlag;
    //現在有休数
    private String paidHolidayRemaining;
    //有休付与日
    private String nextPaidFullday;
    //有休付与日数
    private float categoryKbnOption2;
    //有休日数
    private String JanCrt;
    private String FebCrt;
    private String MarCrt;
    private String AplCrt;
    private String MayCrt;
    private String JunCrt;
    private String JulCrt;
    private String AugCrt;
    private String SepCrt;
    private String OctCrt;
    private String NovCrt;
    private String DecCrt;
    private String JanNext;
    private String FebNext;
    private String MarNext;
    private String AplNext;
    private String MayNext;
    private String JunNext;
    private String JulNext;
    private String AugNext;
    private String SepNext;
    private String OctNext;
    private String NovNext;
    private String DecNext;
    private String dateY;
}
