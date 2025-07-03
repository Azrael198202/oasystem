package com.demo.pojo.transportationExpenses;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MultiTransportDTO {

    /**
     *
     */
    private static final long serialVersionUID = 2258512194666525660L;

    // 会社コード
    public List<String> companyCodeList;
    // 社員ID
    public List<String> userIdList;
    // 年月(yyyyMM)
    public List<String> applyYmList;
    // 申請番号
    public List<String> requestNumberIdList;
}
