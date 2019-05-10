package com.zhku.xianxianmall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zhku.xianxianmall.dao.CartMapper;
import com.zhku.xianxianmall.domain.*;
import com.zhku.xianxianmall.service.CartService;
import com.zhku.xianxianmall.service.OrderService;
import com.zhku.xianxianmall.service.OrderitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author: qwe
 * @date: 2019/4/15 21:04
 * @description:
 */
@RequestMapping("/cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderitemService orderitemService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("addCart")
    public String addCart(HttpServletRequest request, HttpSession session) {

        int pid = Integer.valueOf(request.getParameter("pid"));

        int uid = ((User) session.getAttribute("LOGIN_USER")).getUid();

        double cprice = ((Product) session.getAttribute("product")).getPprice();
        String pdescription = ((Product) session.getAttribute("product")).getPdescription();
        int total = Integer.valueOf(request.getParameter("total"));
        double account = cprice * total;
        String csize = request.getParameter("csize");
        String ccolor = request.getParameter("ccolor");
        String cpic = request.getParameter("cpic");

        Cart cart = new Cart();
        cart.setPid(pid);
        cart.setUid(uid);
        cart.setCprice(cprice);
        cart.setTotal(total);
        cart.setAccount(account);
        cart.setCdate(new Date());
        cart.setCsize(csize);
        cart.setCcolor(ccolor);
        cart.setCpic(cpic);
        cart.setPdescription(pdescription);

        cartService.addCart(cart);

        session.setAttribute("detailMes", "加入购物车成功！");

        return "detail";
    }

    @RequestMapping("findCart")
    public String findCart(HttpSession session) {

        int uid = ((User) session.getAttribute("LOGIN_USER")).getUid();

        List<Cart> cartList = cartService.findCart(uid);
        if (cartList.size() == 0) {
            session.setAttribute("nullCart", "购物车居然是空的！");
        } else {
            session.removeAttribute("nullCart");
            session.setAttribute("cartList", cartList);
        }

        return "cart";
    }

    @RequestMapping("findCartByPdesc")
    public String findCartByPdesc(HttpServletRequest request, HttpSession session) {

        String pdescription = request.getParameter("pdescription");

        int uid = ((User) session.getAttribute("LOGIN_USER")).getUid();

        List<Cart> cartList = cartService.findCartByPdesc(uid, pdescription);
        if (cartList.size() == 0) {
            session.removeAttribute("cartList");
            session.setAttribute("nullCart", "没有找到相关商品！");
        } else {
            session.removeAttribute("nullCart");
            session.setAttribute("cartList", cartList);
        }

        return "cart";

    }

    @RequestMapping("delCart")
    public String delCart(HttpServletRequest request, HttpSession session) {

        String cidValue = request.getParameter("cidValue");
        System.out.println("cidValue:"+cidValue);
        System.out.println("cidValue.indexOf(\",\"):"+cidValue.indexOf(","));

        if (cidValue.indexOf(",") == -1) {
            System.out.println("===========1=============");
            cartService.delCart(Integer.valueOf(cidValue));
        } else {
            System.out.println("===========2=============");
            List<Integer> cidlist = new ArrayList<>();

            String[] cid = cidValue.split(",");

            for (int i = 0; i < cid.length; i++) {
                cidlist.add(Integer.valueOf(cid[i]));
            }

            for (int id: cidlist) {
                cartService.delCart(id);
            }
        }

        session.setAttribute("message", "删除成功！");

        return "message";
    }

    @RequestMapping("account")
    @ResponseBody
    public String account(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        double account = 0;
        List<Integer> oidlist = new ArrayList<>();

        String cidValue = request.getParameter("cidValue");
        System.out.println("cidValue:"+cidValue);
        System.out.println("cidValue.indexOf(\",\"):"+cidValue.indexOf(","));

        if (cidValue.indexOf(",") == -1) {
            System.out.println("===========1=============");
            Cart cart = cartService.findCartByCid(Integer.valueOf(cidValue));
            Date date = new Date();

            // 增加orderitem
            Orderitem orderitem = new Orderitem();
            orderitem.setUid(cart.getUid());
            orderitem.setPid(cart.getPid());
            orderitem.setOprice(cart.getCprice());
            orderitem.setTotal(cart.getTotal());
            orderitem.setAccount(cart.getAccount());
            orderitem.setOstate(0);
            orderitem.setOdate(date);
            orderitem.setOsize(cart.getCsize());
            orderitem.setPdescription(cart.getPdescription());
            orderitem.setOpic(cart.getCpic());
            orderitemService.submitOrderitem(orderitem);

            account = cart.getAccount();

            //oidlist.add(orderitem.getOid());

            // 增加orderTable
            OrderTable orderTable = new OrderTable();
            orderTable.setOid(orderitem.getOid());
            orderTable.setUid(orderitem.getUid());
            orderTable.setRstate(0);
            orderTable.setDate(date);
            orderService.submitOrder(orderTable);

            cartService.delCart(Integer.valueOf(cidValue));
        } else {
            System.out.println("===========2=============");
            List<Integer> cidlist = new ArrayList<>();

            String[] cid = cidValue.split(",");

            for (int i = 0; i < cid.length; i++) {
                cidlist.add(Integer.valueOf(cid[i]));
            }

            for (int id: cidlist) {
                Cart cart = cartService.findCartByCid(id);
                Date date = new Date();

                // 增加orderitem
                Orderitem orderitem = new Orderitem();
                orderitem.setUid(cart.getUid());
                orderitem.setPid(cart.getPid());
                orderitem.setOprice(cart.getCprice());
                orderitem.setTotal(cart.getTotal());
                orderitem.setAccount(cart.getAccount());
                orderitem.setOstate(0);
                orderitem.setOdate(date);
                orderitem.setOsize(cart.getCsize());
                orderitem.setOpic(cart.getCpic());
                orderitem.setPdescription(cart.getPdescription());
                orderitemService.submitOrderitem(orderitem);

                account += cart.getAccount();

                oidlist.add(orderitem.getOid());

                // 增加orderTable
                OrderTable orderTable = new OrderTable();
                orderTable.setOid(orderitem.getOid());
                orderTable.setUid(orderitem.getUid());
                orderTable.setRstate(0);
                orderTable.setDate(date);
                orderService.submitOrder(orderTable);

                cartService.delCart(id);
            }
        }


        // 跳转支付
        //设置编码
        response.setContentType("text/html;charset=utf-8");

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(AlipayConfig.return_url);
        aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填
        Random r = new Random();
        String out_trade_no = String.valueOf(r.nextInt(10000));
        //付款金额，从前台获取，必填
        String total_amount = String.valueOf(account);
        //订单名称，必填
        String subject = "纤纤汉服商城";
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(aliPayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        session.setAttribute("oidList", oidlist);

        return result;
    }
}
