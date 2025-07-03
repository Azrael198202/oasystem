package com.demo.entity;

import java.util.Date;

public class TransportationExpenses {
    private String companyCode;

    private String applyYm;

    private String requestNumber;

    private Byte requestSubNumber;

    private String requestUserId;

    private String startDate;

    private String endDate;

    private String monthKbn;

    private Integer days;

    private String applyKbn;

    private String travelKbn;

    private String departure;

    private String arrival;

    private String wayKbn;

    private String destination;

    private Integer amount;

    private String recipientKbn;

    private String remark;

    private String image;

    private String registrationUser;

    private Date registrationDatetime;

    private String updateUser;

    private Date updateDatetime;

    public TransportationExpenses(String companyCode, String applyYm, String requestNumber, Byte requestSubNumber, String requestUserId, String startDate, String endDate, String monthKbn, Integer days, String applyKbn, String travelKbn, String departure, String arrival, String wayKbn, String destination, Integer amount, String recipientKbn, String remark, String image, String registrationUser, Date registrationDatetime, String updateUser, Date updateDatetime) {
        this.companyCode = companyCode;
        this.applyYm = applyYm;
        this.requestNumber = requestNumber;
        this.requestSubNumber = requestSubNumber;
        this.requestUserId = requestUserId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthKbn = monthKbn;
        this.days = days;
        this.applyKbn = applyKbn;
        this.travelKbn = travelKbn;
        this.departure = departure;
        this.arrival = arrival;
        this.wayKbn = wayKbn;
        this.destination = destination;
        this.amount = amount;
        this.recipientKbn = recipientKbn;
        this.remark = remark;
        this.image = image;
        this.registrationUser = registrationUser;
        this.registrationDatetime = registrationDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public TransportationExpenses() {
        super();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getApplyYm() {
        return applyYm;
    }

    public void setApplyYm(String applyYm) {
        this.applyYm = applyYm == null ? null : applyYm.trim();
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber == null ? null : requestNumber.trim();
    }

    public Byte getRequestSubNumber() {
        return requestSubNumber;
    }

    public void setRequestSubNumber(Byte requestSubNumber) {
        this.requestSubNumber = requestSubNumber;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId == null ? null : requestUserId.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getMonthKbn() {
        return monthKbn;
    }

    public void setMonthKbn(String monthKbn) {
        this.monthKbn = monthKbn == null ? null : monthKbn.trim();
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getApplyKbn() {
        return applyKbn;
    }

    public void setApplyKbn(String applyKbn) {
        this.applyKbn = applyKbn == null ? null : applyKbn.trim();
    }

    public String getTravelKbn() {
        return travelKbn;
    }

    public void setTravelKbn(String travelKbn) {
        this.travelKbn = travelKbn == null ? null : travelKbn.trim();
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure == null ? null : departure.trim();
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival == null ? null : arrival.trim();
    }

    public String getWayKbn() {
        return wayKbn;
    }

    public void setWayKbn(String wayKbn) {
        this.wayKbn = wayKbn == null ? null : wayKbn.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRecipientKbn() {
        return recipientKbn;
    }

    public void setRecipientKbn(String recipientKbn) {
        this.recipientKbn = recipientKbn == null ? null : recipientKbn.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getRegistrationUser() {
        return registrationUser;
    }

    public void setRegistrationUser(String registrationUser) {
        this.registrationUser = registrationUser == null ? null : registrationUser.trim();
    }

    public Date getRegistrationDatetime() {
        return registrationDatetime;
    }

    public void setRegistrationDatetime(Date registrationDatetime) {
        this.registrationDatetime = registrationDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}