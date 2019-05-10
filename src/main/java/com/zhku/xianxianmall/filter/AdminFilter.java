package com.zhku.xianxianmall.filter;

import com.zhku.xianxianmall.domain.Admin;
import com.zhku.xianxianmall.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/background/*")
public class AdminFilter implements Filter {
    @Override
    public void destroy() {
        System.out.println("adminFilter下班。。。。。。。");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Admin admin = (Admin) request.getSession().getAttribute("LOGIN_ADMIN");
        if (admin == null) {
            request.getSession().setAttribute("message", "请登录！");
            response.sendRedirect("../admin/login");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("adminFilter开始干活。。。。。。。");
    }

}
