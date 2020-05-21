package com.zfwhub.bill.dto;

import com.zfwhub.bill.po.Category;

public class CategoryListItemDto implements Dto {

    private static final long serialVersionUID = -2398502844866443397L;

    private String id;

    private Integer type; //分类的类型 与账单数据中的 type 字段一样：1 代表收入，0 代表支出
    private String name; // 分类的展示名称
    
    public CategoryListItemDto() { }
    
    public CategoryListItemDto(String id, Integer type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public static CategoryListItemDto instanceFromCategory(Category category) {
        CategoryListItemDto categoryListItemDto = new CategoryListItemDto(category.getId(), category.getType(), category.getName());
        return categoryListItemDto;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        CategoryListItemDto other = (CategoryListItemDto) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
