//    　月末締一括出力用DTO
package com.demo.pojo.attendance;

import lombok.Data;

@Data
public class BulkExportDTO {

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
    //状態区分コード(勤怠表)
    public String requestStatusKbn;
    //状態区分名(勤怠表)
    public String categoryKbnName;
    //申請番号(月末精算)
    public String transRequestNumber;
    //状態区分コード(月末精算)
    public String transportationCode;
    //状態区分名(月末精算)
    public String transportationName;
    // 交通費更新日時
    public String UpdateDatetime;
    // 勤怠申請対象年月(yyyyMM)
    public String workDateYm;
    // 交通費申請対象年月(yyyyMM)
    public String applyYm;
    // 勤怠表申請更新日時
    public String updTime;
    // 入力画面（2:一括出力画面より　3:勤怠入力画面より　）
    public String accessMode;
    //申請番号(勤怠表)
    public String requestNumber;
}
