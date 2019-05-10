package com.zhku.xianxianmall.controller;

import com.zhku.xianxianmall.domain.User;
import com.zhku.xianxianmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/11 10:09
 * @description:
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        User user = userService.findUserByNicknameAndPwd(nickname, password);
        if (user == null) {
            session.setAttribute("message", "用户名或密码错误！");
            return "login";
        } else {
            /*if (remember.equals("true")) {
                String loginInfo = nickname+","+password;
                Cookie userCookie=new Cookie("loginInfo",loginInfo);
                System.out.println("cookid:" + userCookie.getValue());

                //存活期为一个月 30*24*60*60
                userCookie.setMaxAge(30*24*60*60);
                userCookie.setPath("/");
                response.addCookie(userCookie);
            }*/
        }

        session.setAttribute("LOGIN_USER", user);
        return "font";
    }

    @PostMapping("reg")
    public String reg(HttpServletRequest request) {
        User user = new User();
        user.setNickname(request.getParameter("nickname"));
        user.setUsername(request.getParameter("username"));
        user.setPwd(request.getParameter("password"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("uaddress"));
        user.setSex(Integer.valueOf(request.getParameter("sex")));
        System.out.println(user);

        userService.insert(user);

        return "login";
    }

    @RequestMapping("logOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("LOGIN_USER");
        session.removeAttribute("message");

        return "login";
    }

    @GetMapping("findAll")
    public String findAll(HttpSession session) {
        List<User> userList = userService.findAll();
        session.setAttribute("userlist", userList);
        return "background/userList";
    }

    @GetMapping("findByNickname")
    public String findByNickname(String nickname, HttpSession session) {
        if (nickname == null || nickname == "") {
            return findAll(session);
        }
        User user = userService.findByNickname(nickname);
        session.setAttribute("userlist", user);
        return "background/userList";
    }

    @RequestMapping("OpenUpdateInfo")
    public String OpenUpdateInfo(String nickname, HttpSession session) {
        User user = userService.findByNickname(nickname);
        session.setAttribute("user", user);
        return "openUpdateInfo";
    }

    @PostMapping("updateUser")
    public String updateUser(HttpServletRequest request, HttpSession session) {
        User user = new User();
        user.setUid(Integer.valueOf(request.getParameter("uid")));
        user.setNickname(request.getParameter("nickname"));
        user.setUsername(request.getParameter("username"));
        user.setPwd(request.getParameter("password"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("uaddress"));
        user.setSex(Integer.valueOf(request.getParameter("sex")));
        System.out.println(user);

        userService.updateUser(user);

        session.setAttribute("message", "个人信息修改成功！");

        return "message";
    }
}
