package com.demo.entity.report;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

public class TransportationExpensesTransportPdf {
    // 交通機関
    private String compass_transport;
    // 適用期間
    private String compass_period;
    // 乗車区間
    private String compass_section;
    // 金額（明細部表示用）
    private String compass_amount;
    // 金額（集計用）
    private BigDecimal amount;
    // 添付画像
    private InputStream imgsrc;
    // 添付画像(有り無し判定用)
    private String strImg;
    // 精算区分
    private String compass_invoice_category;

    public TransportationExpensesTransportPdf(String mCompass_transport, String mCompass_period, String mCompass_section,
                                              String mCompass_amount, String mAmount, String mImage, String mCompass_invoice_category) {
        this.compass_transport = mCompass_transport;
        this.compass_period = mCompass_period;
        this.compass_amount = mCompass_amount;
        this.amount = new BigDecimal(mAmount);
        try {
            this.imgsrc = new ByteArrayInputStream(new byte[0]);
            if(!StringUtils.isEmpty(mImage)){
                this.imgsrc = new FileInputStream(mImage);
                this.strImg = mImage;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.compass_invoice_category = mCompass_invoice_category;
        this.compass_section = mCompass_section;
    }

    public String getCompass_transport() {
        return compass_transport;
    }
    public void setCompass_transport(String mCompass_transport) {
        this.compass_transport = mCompass_transport;
    }

    public String getCompass_period() {
        return compass_period;
    }
    public void setCompass_period(String mCompass_period) {
        this.compass_period = mCompass_period;
    }

    public String getCompass_amount() {
        return compass_amount;
    }
    public void setCompass_amount(String mCompass_amount) {
        this.compass_amount = mCompass_amount;
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

    public String getCompass_invoice_category() {
        return compass_invoice_category;
    }
    public void setCompass_invoice_category(String mCompass_invoice_category) {
        this.compass_invoice_category = mCompass_invoice_category;
    }

    public String getCompass_section() {
        return compass_section;
    }
    public void setCompass_section(String mCompass_section) {
        this.compass_section = mCompass_section;
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