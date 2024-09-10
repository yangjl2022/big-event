package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Article;
import com.yangjl.bigevent.entity.Category;
import com.yangjl.bigevent.entity.PageBean;
import com.yangjl.bigevent.entity.Result;
import com.yangjl.bigevent.service.ArticleService;
import com.yangjl.bigevent.service.CategoryService;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;
    /**
     * POST 新增文章(发布文章)
     * @param article:
     * title	文章标题	string	是	1~10个非空字符
     * content	文章正文	string	是
     * coverImg	封面图像地址	string	是	必须是url地址
     * state	发布状态	string	是	已发布 | 草稿
     * categoryId	文章分类ID	number	是
     * @return
     */
    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        Category category = categoryService.findById(article.getCategoryId());
        if(category == null || !category.getCreateUserId().equals(ThreadLocalUtil.getId())) {
            return Result.error("当前用户不存在此文章分类");
        }
        articleService.add(article);
        return Result.success();
    }

    /**
     * GET 根据条件查询文章,带分页
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param state
     * @return
     */
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,
                                          Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId,
                                          @RequestParam(required = false) String state) {
        PageBean<Article> pageBean = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pageBean);
    }

    /**
     * GET 根据ID获取文章详细信息
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article article = articleService.detail(id);
        if (article == null) {
            return Result.error("当前用户下不存在此文章");
        }
        return Result.success(article);
    }

    /**
     * PUT 更新文章信息
     * @param article
     * @return
     */
    @PutMapping
    public Result update(@RequestBody @Validated({Article.update.class}) Article article) {
        int num = articleService.update(article);
        if (num == 0) {
            return Result.error("当前用户下不存在此文章");
        }
        return Result.success();
    }


    /**
     * DELETE 根据ID删除文章
     * @param id
     * @return
     */
    @DeleteMapping
    public Result delete(Integer id) {
        int num = articleService.delete(id);
        if (num == 0) {
            return Result.error("当前用户下不存在此文章");
        }
        return Result.success();
    }

}
