package com.yangjl.bigevent.controller;

import com.yangjl.bigevent.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article")
public class ArticleController {
    @GetMapping("/list")
    public Result list() {
        return Result.success("所有的文章数据...");
    }
}
