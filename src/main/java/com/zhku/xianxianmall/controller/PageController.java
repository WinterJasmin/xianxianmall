package com.zhku.xianxianmall.controller;

import com.zhku.xianxianmall.domain.Product;
import com.zhku.xianxianmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/14 10:18
 * @description:访问首页前先来到这里，查出“每周上新”、“热销专区”的内容，再跳到index页面
 */
@Controller
public class PageController {

    @Autowired
    private ProductService productService;

    @RequestMapping("index")
    public String index(HttpSession session) {

        // 新品
        List<Product> newlist = new ArrayList<>();

        Iterator<Product> it = productService.selectNew().iterator();
        while (it.hasNext()) {
            Product p = it.next();

            String pic = p.getPicaddress();
            if (pic.indexOf(",") != -1) {
                p.setPicaddress(pic.split(",")[0]);
            }

            newlist.add(p);
        }

        session.setAttribute("newList", newlist);

        // 热销商品
        List<Integer> hotListId = new ArrayList<>();
        List<Product> hotList = new ArrayList<>();

        hotListId = productService.selectHotSales();

        for (Integer id : hotListId) {
            Product p = productService.findProductByPid(id);

            String pic = p.getPicaddress();
            if (pic.indexOf(",") != -1) {
                p.setPicaddress(pic.split(",")[0]);
            }

            hotList.add(p);

        }

        session.setAttribute("hotList", hotList);

        return "index";
    }

}
