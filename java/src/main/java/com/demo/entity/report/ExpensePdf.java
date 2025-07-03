package com.demo.entity.report;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

public class ExpensePdf {
    // 経費区分
    private String expense;
    // 日付
    private String expense_period;
    //　内容
    private String expense_section;
    // 金額（明細表示用）
    private String expense_amount;
    //　金額（集計用）
    private BigDecimal amount;
    // 添付画像
    private InputStream imgsrc;
    // 添付画像(有り無し判定用)
    private String strImg;
    // 請求区分
    private String expense_recipient_kbn;

    public ExpensePdf(String mExpense, String mExpense_period, String mExpense_section,
                                                   String mExpense_amount, String mAmount, String mImage, String mExpense_recipient_kbn) {
        this.expense = mExpense;
        this.expense_period = mExpense_period;
        this.expense_amount = mExpense_amount;
        this.amount = new BigDecimal(mAmount);
        try {
            this.imgsrc = new ByteArrayInputStream(new byte[0]);
            if(!StringUtils.isEmpty(mImage)) {
                this.imgsrc = new FileInputStream(mImage);
                this.strImg = mImage;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.expense_recipient_kbn = mExpense_recipient_kbn;
        this.expense_section = mExpense_section;
    }

    public String getExpense() {
        return expense;
    }
    public void setExpense(String mExpense) {
        this.expense = mExpense;
    }

    public String getExpense_period() {
        return expense_period;
    }
    public void setExpense_period(String mExpense_period) {
        this.expense_period = mExpense_period;
    }

    public String getExpense_amount() {
        return expense_amount;
    }
    public void setExpense_amount(String mExpense_amount) {
        this.expense_amount = mExpense_amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal mAmount) {
        this.amount = mAmount;
    }

    public InputStream getImgsrc() {
        return imgsrc;
    }
    public void setImgsrc(InputStream mImage) {
        this.imgsrc = mImage;
    }

    public String getStrImg() {
        return strImg;
    }
    public void setStrImg(String mImage) {
        this.strImg = mImage;
    }

    public String getExpense_recipient_kbn() {
        return expense_recipient_kbn;
    }
    public void setExpense_recipient_kbn(String mExpense_recipient_kbn) {this.expense_recipient_kbn = mExpense_recipient_kbn;
    }

    public String getExpense_section() {
        return expense_section;
    }
    public void setExpense_section(String mExpense_section) {
        this.expense_section = mExpense_section;
    }

    /**
     * メソッド名：toString.<br>
     * 機能概要  ：
     *
     * @return 戻り値
     */
    @Override
    public String toString() {

        return ToStringBuilder.
                reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
