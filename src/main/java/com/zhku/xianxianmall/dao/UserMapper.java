package com.zhku.xianxianmall.dao;

import com.zhku.xianxianmall.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Insert("insert into user(nickname,pwd,phone,address,sex,username) values (#{nickname},#{pwd},#{phone},#{address},#{sex},#{username})")
    int insert(User user);

    @Select("select * from user where nickname=#{nickname} and pwd=#{pwd}")
    User findUserByNicknameAndPwd(String nickname, String pwd);

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where nickname = #{nickname}")
    User findByNickname(String nickname);

    @Update("update user set nickname=#{nickname},pwd=#{pwd},phone=#{phone},address=#{address},sex=#{sex},username=#{username} where uid=#{uid}")
    void updateUser(User user);
}