package com.yangjl.bigevent.mapper;

import com.yangjl.bigevent.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Update("insert into category(category_name,category_alias,create_user_id) " +
            "values(#{categoryName},#{categoryAlias},#{createUserId})")
    void add(Category category);

    @Select("select id,category_name,category_alias,create_time,update_time from category" +
            " where create_user_id = #{id} limit 1000")
    List<Category> list(Integer id);
}
