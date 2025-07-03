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

// 定期券以外PDF出力データ
public class TransportationExpensesOtherTransportPdf {
    // 交通機関
    private String other_transport;
    // 日付
    private String other_period;
    //　始点～終点（往復／片道）
    private String other_section;
    // 金額（明細表示用）
    private String other_amount;
    //　金額（集計用）
    private BigDecimal amount;
    // 添付画像
    private InputStream imgsrc;
    // 添付画像(有り無し判定用)
    private String strImg;
    // 請求区分
    private String other_recipient_kbn;

    public TransportationExpensesOtherTransportPdf(String mOther_transport, String mOther_period, String mOther_section,
                                              String mOther_amount, String mAmount, String mImage, String mOther_recipient_kbn) {
        this.other_transport = mOther_transport;
        this.other_period = mOther_period;
        this.other_amount = mOther_amount;
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
        this.other_recipient_kbn = mOther_recipient_kbn;
        this.other_section = mOther_section;
    }

    public String getOther_transport() {
        return other_transport;
    }
    public void setOther_transport(String mOther_transport) {
        this.other_transport = mOther_transport;
    }

    public String getOther_period() {
        return other_period;
    }
    public void setOther_period(String mOther_period) {
        this.other_period = mOther_period;
    }

    public String getOther_amount() {
        return other_amount;
    }
    public void setOther_amount(String mOther_amount) {
        this.other_amount = mOther_amount;
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

    public String getOther_recipient_kbn() {
        return other_recipient_kbn;
    }
    public void setOther_recipient_kbn(String mOther_recipient_kbn) {
        this.other_recipient_kbn = mOther_recipient_kbn;
    }

    public String getOther_section() {
        return other_section;
    }
    public void setOther_section(String mOther_section) {
        this.other_section = mOther_section;
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