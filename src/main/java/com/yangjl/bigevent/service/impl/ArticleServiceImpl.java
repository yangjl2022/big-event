package com.yangjl.bigevent.service.impl;

import com.yangjl.bigevent.entity.Article;
import com.yangjl.bigevent.mapper.ArticleMapper;
import com.yangjl.bigevent.service.ArticleService;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Integer id = ThreadLocalUtil.getId();
        article.setUserId(id);
        articleMapper.add(article);
    }
}
