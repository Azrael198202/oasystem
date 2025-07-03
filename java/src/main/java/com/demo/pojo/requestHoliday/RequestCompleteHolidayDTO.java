package com.demo.pojo.requestHoliday;


import lombok.Data;

//休日申請完了済みクラス
@Data
public class RequestCompleteHolidayDTO {
    //会社コード
    private String companyCode;
    //ユーザーID
    private String userId;
    //ID＋社員名
    private String userIdName;
    //部門コード
    private String departmentCode;
    //最終締め年月
    private String closeMonth;
    //有休残日数
    private String paidHolidayRemaining;
    //次回有休付与日数
    private String categoryKbnOption2;
    //次回有休付与日
    private String nextPaidFullDay;
    //有休承認済み日数
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
      private String planYear;

}
