package com.yangjl.bigevent.service;

import com.yangjl.bigevent.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 添加一条文章分类
     * @param category
     */
    void add(Category category);

    /**
     * 查询当前用户的所有文章分类
     * @return
     */
    List<Category> list();

    /**
     * 根据 id 查询单条分类信息
     * @param id
     * @return
     */
    Category findById(Integer id);

    void update(Category category);

    int delete(Integer id);
}
