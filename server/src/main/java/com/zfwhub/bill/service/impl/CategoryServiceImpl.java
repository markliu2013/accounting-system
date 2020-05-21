package com.zfwhub.bill.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfwhub.bill.dto.CategoryListItemDto;
import com.zfwhub.bill.po.Category;
import com.zfwhub.bill.repository.CategoryRepository;
import com.zfwhub.bill.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryListItemDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        //https://stackoverflow.com/questions/47929674/modelmapper-mapping-list-of-entites-to-list-of-dto-objects
        List<CategoryListItemDto> categoryListItemDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryListItemDtos.add(CategoryListItemDto.instanceFromCategory(category));
        }
        return categoryListItemDtos;
    }

}
