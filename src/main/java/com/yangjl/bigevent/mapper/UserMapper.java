package com.yangjl.bigevent.mapper;

import com.yangjl.bigevent.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select id,username,password,nickname,email,head_picture as userPic from user where username = #{username}")
    User findByUserName(String username);

    @Insert("insert into user(username, password) values(#{username}, #{password})")
    void add(String username, String password);

    @Update("update user set nickname = #{nickname}, email = #{email} where username = #{username}")
    void update(User user);

    @Update("update user set head_picture = #{avatarUrl} where id = #{id}")
    void updateAvatar(Integer id, String avatarUrl);

    @Update("update user set password = #{newPwd} where id = #{id}")
    void updatePwd(Integer id, String newPwd);
}
