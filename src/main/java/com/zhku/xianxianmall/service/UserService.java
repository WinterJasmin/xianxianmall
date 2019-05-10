package com.zhku.xianxianmall.service;

import com.zhku.xianxianmall.domain.User;

import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/11 10:07
 * @description:
 */
public interface UserService {
    public void insert(User user);

    public User findUserByNicknameAndPwd(String nickname, String pwd);

    public List<User> findAll();

    public User findByNickname(String nickname);

    public void updateUser(User user);
}
