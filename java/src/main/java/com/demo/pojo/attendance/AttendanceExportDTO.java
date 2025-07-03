//    　勤怠表エクスポート
package com.demo.pojo.attendance;

import lombok.Data;

@Data
public class AttendanceExportDTO {

    // 会社コード
    public String companyCode;
    // 所属コード
    public String departmentCode;
    // 所属名
    public String departmentName;
    // 社員コード
    public String userId;
    // 社員名
    public String userName;
    //状態区分コード
    public String requestStatusKbn;
    //状態区分名
    public String categoryKbnName;
    // 年月(yyyyMM)
    public String workDateYm;
    // 勤怠表申請更新日時
    public String updTime;

}
