package com.zfwhub.bill.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfwhub.bill.dto.BaseResponse;
import com.zfwhub.bill.dto.CategoryListItemDto;
import com.zfwhub.bill.dto.ResponseSuccess;
import com.zfwhub.bill.service.CategoryService;

import io.swagger.annotations.*;

@Api(tags={"category"})
@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    
    @ApiOperation(value = "账单类别列表", notes = "查询所有账单类别")
    @GetMapping("/")
    public BaseResponse handleGetAll() {
        ResponseSuccess<List<CategoryListItemDto>> response = new ResponseSuccess<>();
        response.setData(categoryService.getAll());
        return response;
    }

}
