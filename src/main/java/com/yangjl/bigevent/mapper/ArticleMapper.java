package com.yangjl.bigevent.mapper;

import com.yangjl.bigevent.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,user_id)" +
            " values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{userId})")
    void add(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);

    @Select("select * from article where id = #{id} and user_id = ${userId}")
    Article detail(Integer id, Integer userId);

    @Update("update article set title = #{title}, content = #{content}, cover_img = #{coverImg}, state = #{state}," +
            " category_id = #{categoryId} where id = #{id} and user_id = #{userId}")
    int update(Article article);
}
