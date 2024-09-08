package com.yangjl.bigevent.service.impl;

import com.yangjl.bigevent.entity.Category;
import com.yangjl.bigevent.mapper.CategoryMapper;
import com.yangjl.bigevent.service.CategoryService;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategorySeriveImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        Map<String, Object> map = ThreadLocalUtil.get();
        category.setCreateUserId((Integer) map.get("id"));
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return categoryMapper.list(id);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public int delete(Integer id) {
        Integer userId = ThreadLocalUtil.getId();
        return categoryMapper.delete(id, userId);
    }
}
