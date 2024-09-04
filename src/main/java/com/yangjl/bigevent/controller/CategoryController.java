package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Category;
import com.yangjl.bigevent.entity.Result;
import com.yangjl.bigevent.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * POST 新增文章分类
     * @param category: categoryName,categoryAlias
     * @return
     */
    @PostMapping
    public Result add(@RequestBody @Validated Category category) {
        categoryService.add(category);
        return Result.success();
    }

    /**
     * GET 查询当前用户所有的文章分类
     * @return
     */
    @GetMapping
    public Result<List<Category>> list() {
        return Result.success(categoryService.list());
    }
}
