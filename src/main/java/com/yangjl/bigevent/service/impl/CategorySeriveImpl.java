package com.yangjl.bigevent.service.impl;

import com.yangjl.bigevent.entity.Category;
import com.yangjl.bigevent.mapper.CategoryMapper;
import com.yangjl.bigevent.service.CategoryService;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
