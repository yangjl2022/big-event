package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Article;
import com.yangjl.bigevent.entity.Category;
import com.yangjl.bigevent.entity.Result;
import com.yangjl.bigevent.service.ArticleService;
import com.yangjl.bigevent.service.CategoryService;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

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
        articleService.add(article);
        return Result.success();
    }
}
