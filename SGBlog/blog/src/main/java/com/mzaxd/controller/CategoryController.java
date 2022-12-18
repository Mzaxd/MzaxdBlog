package com.mzaxd.controller;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.vo.CategoryVo;
import com.mzaxd.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Mzaxd
 * @since 2022-11-22 16:15
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult<List<CategoryVo>> getCategoryList(){
        return categoryService.getCategoryList();
    }
}
