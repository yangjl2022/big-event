package com.yangjl.bigevent.mapper;

import com.yangjl.bigevent.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,user_id)" +
            " values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{userId})")
    void add(Article article);
}
