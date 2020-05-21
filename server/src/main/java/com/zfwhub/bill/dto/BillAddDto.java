package com.zfwhub.bill.dto;

import java.util.Calendar;
import java.util.Date;

import com.zfwhub.bill.po.Bill;
import com.zfwhub.bill.po.Category;

public class BillAddDto implements Dto {

    private static final long serialVersionUID = 2581396834576867876L;
    
    private Integer type; //分类的类型 与账单数据中的 type 字段一样：1 代表收入，0 代表支出
    private Long time;//账单毫秒
    private String categoryId;
    private Double amount;
    
    public Bill convertToEntity() {
        // https://www.cnblogs.com/wang-yaz/p/11588927.html
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        c.set(Calendar.MILLISECOND,0);
        Bill bill = new Bill(type, c.getTime(), new Category(categoryId), amount);
        return bill;
    }
    
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
