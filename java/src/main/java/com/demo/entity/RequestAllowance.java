package com.demo.entity;

import java.util.Date;

public class RequestAllowance {
    //    会社コード
    private String companyCode;
    //    年月
    private String applyYm;
    //    申請番号
    private String requestNumber;
    //    申請種類区分
    private String requestTypeKbn;
    //    申請者
    private String requestUserId;
    //    手当金額
    private Integer allowance;
    //    手当口数
    private Integer quantity;
    //    手当合計
    private Integer total;
    //    作番ID
    private String projectId;
    //    作番名
    private String projectName;
    //    資格ID
    private String qualificationId;
    //    資格受検日
    private String qualificationExamDate;
    //    添付画像
    private String image;
    //    備考１
    private String remark1;
    //    備考２
    private String remark2;
    //    備考３
    private String remark3;
    //    備考４
    private String remark4;
    //    備考５
    private String remark5;
    //    登録者
    private String regAccount;
    //    登録日時
    private Date regTime;
    //    更新者
    private String updAccount;
    //    更新日時
    private Date updTime;

    public RequestAllowance(String companyCode, String applyYm, String requestNumber, String requestTypeKbn, String requestUserId, Integer allowance, Integer quantity, Integer total, String projectId, String projectName, String qualificationId, String qualificationExamDate, String image, String remark1, String remark2, String remark3, String remark4, String remark5, String regAccount, Date regTime, String updAccount, Date updTime) {
        this.companyCode = companyCode;
        this.applyYm = applyYm;
        this.requestNumber = requestNumber;
        this.requestTypeKbn = requestTypeKbn;
        this.requestUserId = requestUserId;
        this.allowance = allowance;
        this.quantity = quantity;
        this.total = total;
        this.projectId = projectId;
        this.projectName = projectName;
        this.qualificationId = qualificationId;
        this.qualificationExamDate = qualificationExamDate;
        this.image = image;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.remark3 = remark3;
        this.remark4 = remark4;
        this.remark5 = remark5;
        this.regAccount = regAccount;
        this.regTime = regTime;
        this.updAccount = updAccount;
        this.updTime = updTime;
    }
    public RequestAllowance() {
        super();
    }
    // 会社コード
    public String getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }
    // 年月
    public String getApplyYm() {
        return applyYm;
    }
    public void setApplyYm(String applyYm) {
        this.applyYm = applyYm == null ? null : applyYm.trim();
    }
    // 申請番号
    public String getRequestNumber() {
        return requestNumber;
    }
    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber == null ? null : requestNumber.trim();
    }
    // 申請種類区分
    public String getRequestTypeKbn() {
        return requestTypeKbn;
    }
    public void setRequestTypeKbn(String requestTypeKbn) {
        this.requestTypeKbn = requestTypeKbn == null ? null : requestTypeKbn.trim();
    }
    // 申請者
    public String getRequestUserId() {
        return requestUserId;
    }
    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId == null ? null : requestUserId.trim();
    }
    // 手当金額
    public Integer getAllowance() {
        return allowance;
    }
    public void setAllowance(Integer allowance) {
        this.allowance = allowance;
    }
    // 手当口数
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    // 手当合計
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    // 作番ID
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }
    // 作番名
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }
    // 資格ID
    public String getQualificationId() {
        return qualificationId;
    }
    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId == null ? null : qualificationId.trim();
    }
    // 資格受検日
    public String getQualificationExamDate() {
        return qualificationExamDate;
    }
    public void setQualificationExamDate(String qualificationExamDate) {
        this.qualificationExamDate = qualificationExamDate == null ? null : qualificationExamDate.trim();
    }
    // 添付画像
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
    // 備考１
    public String getRemark1() {
        return remark1;
    }
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }
    // 備考２
    public String getRemark2() {
        return remark2;
    }
    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }
    // 備考３
    public String getRemark3() {
        return remark3;
    }
    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }
    // 備考４
    public String getRemark4() {
        return remark4;
    }
    public void setRemark4(String remark4) {
        this.remark4 = remark4 == null ? null : remark4.trim();
    }
    // 備考５
    public String getRemark5() {
        return remark5;
    }
    public void setRemark5(String remark5) {
        this.remark5 = remark5 == null ? null : remark5.trim();
    }
    // 登録者
    public String getRegAccount() {
        return regAccount;
    }
    public void setRegAccount(String regAccount) {
        this.regAccount = regAccount == null ? null : regAccount.trim();
    }
    // 登録日時
    public Date getRegTime() {
        return regTime;
    }
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
    // 更新者
    public String getUpdAccount() {
        return updAccount;
    }
    public void setUpdAccount(String updAccount) {
        this.updAccount = updAccount == null ? null : updAccount.trim();
    }
    // 更新日時
    public Date getUpdTime() {
        return updTime;
    }
    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }
}
