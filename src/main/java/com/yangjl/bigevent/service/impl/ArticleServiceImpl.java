package com.yangjl.bigevent.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yangjl.bigevent.entity.Article;
import com.yangjl.bigevent.entity.PageBean;
import com.yangjl.bigevent.mapper.ArticleMapper;
import com.yangjl.bigevent.service.ArticleService;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Article>  as =  articleMapper.list(ThreadLocalUtil.getId(), categoryId, state);
        Page<Article> pages = (Page<Article>) as;
        // 构建结果
        PageBean<Article> pageBean = new PageBean<>();
        pageBean.setTotal(pageBean.getTotal());
        pageBean.setItems(pages.getResult());
        return pageBean;
    }

    @Override
    public Article detail(Integer id) {
        return articleMapper.detail(id, ThreadLocalUtil.getId());
    }

    @Override
    public int update(Article article) {
        article.setUserId(ThreadLocalUtil.getId());
        return articleMapper.update(article);
    }

    @Override
    public int delete(Integer id) {
        return articleMapper.delete(id, ThreadLocalUtil.getId());
    }
}
