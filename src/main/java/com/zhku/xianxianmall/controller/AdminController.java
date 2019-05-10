package com.zhku.xianxianmall.controller;

import com.zhku.xianxianmall.domain.Admin;
import com.zhku.xianxianmall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: qwe
 * @date: 2019/4/29 14:27
 * @description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("login")
    public String login(HttpSession session, HttpServletRequest request) {
        String aid = request.getParameter("aid");
        String pwd = request.getParameter("pwd");

        Admin admin = adminService.findAdminByAidAndPwd(aid, pwd);
        if (admin == null) {
            session.setAttribute("message", "用户名或密码错误！");
            return "background/adminLogin";
        }

        session.setAttribute("LOGIN_ADMIN", admin);

        return "background/admin";
    }

    @PostMapping("updatePwd")
    public String updatePwd(HttpServletRequest request, HttpSession session) {
        String aid = ((Admin) session.getAttribute("LOGIN_ADMIN")).getAid();
        String newPwd = request.getParameter("password");

        adminService.updatePwd(aid, newPwd);

        session.setAttribute("message", "修改成功！");

        return "message";
    }
}
