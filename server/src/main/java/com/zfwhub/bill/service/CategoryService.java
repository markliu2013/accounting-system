package com.zfwhub.bill.service;


import java.util.List;
import com.zfwhub.bill.dto.CategoryListItemDto;

public interface CategoryService {
    
    List<CategoryListItemDto> getAll();

}
