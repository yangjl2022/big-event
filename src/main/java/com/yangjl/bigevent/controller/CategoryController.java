package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Category;
import com.yangjl.bigevent.entity.Result;
import com.yangjl.bigevent.service.CategoryService;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        log.info("新增文章分类: {}", category);
        return Result.success();
    }

    /**
     * GET 查询当前用户所有的文章分类
     * @return
     */
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> list = categoryService.list();
        log.info("用户 {} 的所有的文章分类: {}", ThreadLocalUtil.getId(), list);
        return Result.success(list);
    }

    /**
     * GET 查询指定 id 的文章分类
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category category = categoryService.findById(id);
        log.info("查询到的文章分类: {}", category);
        return Result.success(category);
    }

    /**
     * PUT 更新文章分类
     * @param category
     *  id	主键ID
     *  categoryName
     *  categoryAlias
     * @return
     */
    @PutMapping
    public Result update(@RequestBody @Validated({Category.update.class}) Category category) {
        categoryService.update(category);
        log.info("更新文章分类: {}", category);
        return Result.success();
    }

    /**
     * DELETE 根据ID删除文章分类
     * @param id
     * @return
     */
    @DeleteMapping
    public Result delete(Integer id) {
        int num = categoryService.delete(id);
        if(num == 0) {
            return Result.error("文章不存在或用户无权限");
        }
        log.info("删除文章分类: {}", id);
        return Result.success();
    }
}
