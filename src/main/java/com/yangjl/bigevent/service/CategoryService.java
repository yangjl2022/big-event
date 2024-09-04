package com.yangjl.bigevent.service;

import com.yangjl.bigevent.entity.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();
}
