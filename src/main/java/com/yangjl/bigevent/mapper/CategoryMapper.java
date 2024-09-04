package com.yangjl.bigevent.mapper;

import com.yangjl.bigevent.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CategoryMapper {
    @Update("insert into category(category_name,category_alias,create_user_id) " +
            "values(#{categoryName},#{categoryAlias},#{createUserId})")
    void add(Category category);
}
