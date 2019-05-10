package com.zhku.xianxianmall.filter;

import com.zhku.xianxianmall.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UserFilter", urlPatterns = {"/cart/*", "/order/*"})
public class UserFilter implements Filter {
    @Override
    public void destroy() {
        System.out.println("userFilter下班。。。。。。。");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute("LOGIN_USER");
        if (user == null) {
            request.getSession().setAttribute("message", "请登录！");
            response.sendRedirect("../login.html");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("userFilter开始干活。。。。。。。");
    }

}
