package com.yangjl.bigevent.service;

import com.yangjl.bigevent.entity.Article;
import com.yangjl.bigevent.entity.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
