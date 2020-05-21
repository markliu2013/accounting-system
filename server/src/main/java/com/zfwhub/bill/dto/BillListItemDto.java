package com.zfwhub.bill.dto;

import com.zfwhub.bill.po.Bill;

public class BillListItemDto implements Dto {

    private static final long serialVersionUID = -6802579209502602783L;

    private String id;

    private Integer type; //分类的类型 与账单数据中的 type 字段一样：1 代表收入，0 代表支出

    private Long time;

    private String categoryId;

    private String categoryName;

    private Double amount;

    public BillListItemDto() { }

    public BillListItemDto(String id, Integer type, Long time, String categoryId, String categoryName, Double amount) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.amount = amount;
    }

    public static BillListItemDto instanceFromBill(Bill bill) {
        BillListItemDto billListItemDto = new BillListItemDto(bill.getId(), bill.getType(), bill.getTime().getTime(), 
                bill.getCategory().getId(), bill.getCategory().getName(), bill.getAmount());
        return billListItemDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BillListItemDto other = (BillListItemDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
