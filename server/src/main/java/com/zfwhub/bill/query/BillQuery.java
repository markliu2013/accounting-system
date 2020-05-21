package com.zfwhub.bill.query;

public class BillQuery {

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer type;
    private String categoryId;
    private Double minAmount;
    private Double maxAmount;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public String toString() {
        return "BillQuery [year=" + year + ", month=" + month + ", day=" + day + ", type=" + type + ", categoryId=" + categoryId + ", minAmount=" + minAmount + ", maxAmount=" + maxAmount + "]";
    }

}
