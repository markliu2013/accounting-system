package com.zfwhub.bill.dto;

public class BillCSVDto implements Dto {

    private static final long serialVersionUID = -7628075822354218668L;

    private String type; //分类的类型 与账单数据中的 type 字段一样：1 代表收入，0 代表支出
    private String time;
    private String category;
    private String amount;
    
    public BillCSVDto(String type, String time, String category, String amount) {
        this.type = type;
        this.time = time;
        this.category = category;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
