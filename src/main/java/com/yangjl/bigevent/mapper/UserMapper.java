package com.yangjl.bigevent.mapper;

import com.yangjl.bigevent.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select id,username,password,nickname,email,head_picture from user where username = #{username}")
    User findByUserName(String username);

    @Insert("insert into user(username, password) values(#{username}, #{password})")
    void add(String username, String password);
}
