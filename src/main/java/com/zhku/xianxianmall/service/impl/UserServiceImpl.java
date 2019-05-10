package com.zhku.xianxianmall.service.impl;

import com.zhku.xianxianmall.dao.UserMapper;
import com.zhku.xianxianmall.domain.User;
import com.zhku.xianxianmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/11 10:07
 * @description:
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public User findUserByNicknameAndPwd(String nickname, String pwd) {
        return userMapper.findUserByNicknameAndPwd(nickname, pwd);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findByNickname(String nickname) {
        return userMapper.findByNickname(nickname);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
