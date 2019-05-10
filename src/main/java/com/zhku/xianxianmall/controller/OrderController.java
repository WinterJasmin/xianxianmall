package com.zhku.xianxianmall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhku.xianxianmall.domain.*;
import com.zhku.xianxianmall.service.OrderService;
import com.zhku.xianxianmall.service.OrderitemService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.*;

/**
 * @author: qwe
 * @date: 2019/4/17 10:21
 * @description:
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderitemService orderitemService;

    private String path ="C:/apache-tomcat-9.0.19/webapps/xianxianmall/WEB-INF/classes/static/images/";

    @RequestMapping("buyNow")
    public String buyNow(HttpServletRequest request, HttpSession session) {

        int pid = Integer.valueOf(request.getParameter("pid"));

        int uid = ((User) session.getAttribute("LOGIN_USER")).getUid();

        double oprice = ((Product) session.getAttribute("product")).getPprice();
        int total = Integer.valueOf(request.getParameter("total"));
        double account = oprice * total;
        String osize = request.getParameter("csize");
        String ccolor = request.getParameter("ccolor");
        String opic = ((Product) session.getAttribute("product")).getPicaddress();

        Orderitem orderitem = new Orderitem();
        orderitem.setUid(uid);
        orderitem.setPid(pid);
        orderitem.setOprice(oprice);
        orderitem.setTotal(total);
        orderitem.setAccount(account);
        orderitem.setOstate(0);
        orderitem.setOdate(new Date());
        orderitem.setOsize(osize);
        orderitem.setOcolor(ccolor);
        orderitem.setOpic(opic);
        orderitem.setPdescription(((Product) session.getAttribute("product")).getPdescription());

        System.out.println(orderitem);

        session.setAttribute("orderitem", orderitem);

        return "openBuy";
    }

    @PostMapping("submitOrder")
    @ResponseBody
    public String submitOrder(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

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
        String out_trade_no = request.getParameter("pid") + r.nextInt(100);
        //付款金额，从前台获取，必填
        String total_amount = request.getParameter("account");
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

        int uid = ((User) session.getAttribute("LOGIN_USER")).getUid();
        Date date = new Date();

        // 更新orderitem表
        Orderitem orderitem = new Orderitem();
        orderitem.setUid(uid);
        orderitem.setPid(Integer.valueOf(request.getParameter("pid")));
        orderitem.setOprice(Double.valueOf(request.getParameter("oprice")));
        orderitem.setTotal(Integer.valueOf(request.getParameter("total")));
        orderitem.setAccount(Double.valueOf(request.getParameter("account")));
        orderitem.setOstate(0);
        orderitem.setOdate(date);
        orderitem.setOsize(request.getParameter("osize"));
        orderitem.setOcolor(request.getParameter("ocolor"));
        orderitem.setOpic(((Orderitem) session.getAttribute("orderitem")).getOpic());
        orderitem.setPdescription(((Orderitem) session.getAttribute("orderitem")).getPdescription());
        System.out.println("submitOrder-->orderitem:" + orderitem.toString());

        orderitemService.submitOrderitem(orderitem);
        List<Integer> oidList = new ArrayList<>();
        oidList.add(orderitem.getOid());
        session.setAttribute("oidList", oidList);

        // 更新order表
        OrderTable order = new OrderTable();
        order.setOid(orderitem.getOid());
        order.setUid(uid);
        order.setRstate(0);
        order.setDate(date);
        order.toString();

        orderService.submitOrder(order);

        //输出
        return result;
    }

    @RequestMapping("notify_url")
    public String notify_url(HttpServletRequest request, HttpSession session) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            try {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


                if(trade_status.equals("TRADE_FINISHED")){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                }else if (trade_status.equals("TRADE_SUCCESS")){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知

                    System.out.println("********************** 支付成功(支付宝同步通知) **********************");
                    System.out.println("* 订单号: {" + out_trade_no + "}");
                    System.out.println("* 支付宝交易号: {" + trade_no + "}");
                    System.out.println("* 实付金额: {" + total_amount + "}");
                    System.out.println("***************************************************************");

                }

                System.out.println("支付成功。。。");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 更新orderitem中的支付状态ostate
            List<Integer> oidList = (List<Integer>) session.getAttribute("oidList");
            for (int oid : oidList) {
                orderitemService.paySuccess(oid);
            }

            session.setAttribute("message", "notify_url success!");
            return "message";

        }else {//验证失败

            System.out.println("支付失败。。。");

            session.setAttribute("message", "notify_url fail!");
            return "message";

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
    }

    @RequestMapping("return_url")
    public String return_url(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        System.out.println("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            String out_trade_no = null;
            String trade_no = null;
            String total_amount = null;
            try {
                //商户订单号
                out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //付款金额
                total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            System.out.println("********************** 支付成功(支付宝同步通知) **********************");
            System.out.println("* 订单号: {" + out_trade_no + "}");
            System.out.println("* 支付宝交易号: {" + trade_no + "}");
            System.out.println("* 实付金额: {" + total_amount + "}");
            System.out.println("***************************************************************");

            // 更新orderitem中的支付状态ostate
            List<Integer> oidList = (List<Integer>) session.getAttribute("oidList");
            for (int oid : oidList) {
                orderitemService.paySuccess(oid);
            }

            /*session.setAttribute("message", "trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>");*/
            session.setAttribute("message", "支付成功！");
            return "message";
        }else {
            System.out.println("支付, 验签失败...");
            session.setAttribute("message", "支付, 验签失败...");
            return "message";
        }
    }

    @RequestMapping("findUserOrder")
    public String findUserOrder(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, HttpSession session) {
        int uid = ((User) session.getAttribute("LOGIN_USER")).getUid();

        PageHelper.startPage(pageNo,pageSize);
        PageInfo<Orderitem> pageInfo = new PageInfo<>(orderitemService.findOrderitemByUid(uid));

        session.setAttribute("orderitemPage", pageInfo);
        return "orderitemList";
    }

    @RequestMapping("delOrderitem")
    public String delOrderitem(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, int oid, HttpSession session) {

        orderitemService.delOrderitemList(oid);

        return findUserOrder(pageNo, pageSize, session);
    }

    @RequestMapping("payOrderitem")
    @ResponseBody
    public String payOrderitem(int oid, HttpServletResponse response, HttpSession session) {
        Orderitem orderitem = orderitemService.findOrderitemByOid(oid);

        List<Integer> oidList = new ArrayList<>();
        oidList.add(oid);
        session.setAttribute("oidList", oidList);

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
        String out_trade_no = String.valueOf(orderitem.getPid() + r.nextInt(100));
        //付款金额，从前台获取，必填
        String total_amount = String.valueOf(orderitem.getAccount());
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


        //输出
        return result;
    }

    @RequestMapping("findAllOrder")
    public String findAllOrder(HttpSession session){
        List<OrderTable> orderTableList = orderService.findAllOrder();

        session.setAttribute("orderTableList", orderTableList);

        return "background/orderList";
    }

    @RequestMapping("findOrderByRid")
    public String findOrderByRid(HttpServletRequest request, HttpSession session) {

        if (request.getParameter("rid") == null || request.getParameter("rid") == "") {
            return findAllOrder(session);
        }

        int rid = Integer.valueOf(request.getParameter("rid"));

        List<OrderTable> orderTableList = orderService.findOrderByRid(rid);

        session.setAttribute("orderTableList", orderTableList);

        return "background/orderList";

    }

    @RequestMapping("findDeliveryOrder")
    public String findDeliveryOrder(HttpSession session) {
        List<OrderTable> deliveryList = orderService.findDeliveryOrder();

        session.setAttribute("deliveryList", deliveryList);

        return "background/deliveryList";
    }

    @RequestMapping("findDeliveryByRid")
    public String findDeliveryByRid(HttpServletRequest request, HttpSession session) {

        if (request.getParameter("rid") == null || request.getParameter("rid") == "") {
            return findDeliveryOrder(session);
        } else {
            int rid = Integer.valueOf(request.getParameter("rid"));

            List<OrderTable> deliveryList = orderService.findDeliveryByRid(rid);

            session.setAttribute("deliveryList", deliveryList);

            return "background/deliveryList";
        }
    }

    @RequestMapping("delivery")
    public String delivery(int rid, HttpSession session) {
        orderService.delivery(rid);

        // 查出所有oid，更改orderitem中的状态
        List<Integer> oidList = orderService.findOidByRid(rid);

        for (Integer i : oidList) {
            orderitemService.delivery(i);
        }

        List<OrderTable> deliveryList = orderService.findDeliveryOrder();

        session.setAttribute("deliveryList", deliveryList);
        session.setAttribute("deliveryMes", "发货成功！");

        return "background/deliveryList";
    }

    @RequestMapping("ensureDelivery")
    public String ensureDelivery(int oid, HttpSession session) {
        orderitemService.ensureDelivery(oid);

        return findUserOrder(1, 10, session);
    }

    @RequestMapping("marketing")
    public String marketing(HttpSession session) {
        // 从数据库得到数据集
        CategoryDataset ds = null;
        try {
            ds = orderitemService.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart3D(
                "汉服销量图", //图表标题
                "名称", //目录轴的显示标签
                "销量", //数值轴的显示标签
                ds, //数据集
                PlotOrientation.VERTICAL, //图表方向
                true, //是否显示图例，对于简单的柱状图必须为false
                false, //是否生成提示工具
                false);         //是否生成url链接

        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();

        CategoryAxis domainAxis = categoryplot.getDomainAxis();

        // 设置X轴坐标上的文字
        domainAxis.setTickLabelFont(new Font("黑体", Font.BOLD, 12));
        // 设置X轴的标题文字
        domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 12));
        // 设置标签文字旋转的角度
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6));

        // 设置Y轴坐标上的文字
        numberaxis.setTickLabelFont(new Font("黑体", Font.BOLD, 12));
        // 设置Y轴的标题文字
        numberaxis.setLabelFont(new Font("黑体", Font.BOLD, 12));

        // 解决底部汉字乱码的问题
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));
        // 解决标题汉字乱码的问题
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 12));

        BarRenderer renderer = (BarRenderer) categoryplot.getRenderer();
        // 是否可见
        renderer.setDrawBarOutline(true);
        // 外廓线粗细
        renderer.setBaseOutlineStroke(new BasicStroke(1.0f), true);
        // 颜色设置
        renderer.setBaseOutlinePaint(Color.BLACK);
        // 柱子之间间隙
        renderer.setItemMargin(0.0);
        // 在柱子上显示相应信息 是否显示
        renderer.setBaseItemLabelsVisible(true);
        // 在柱子上显示相应信息
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // 设置数值颜色，默认黑色
        renderer.setBaseItemLabelPaint(Color.BLACK);
        // 搭配ItemLabelAnchor TextAnchor 这两项能达到不同的效果，但是ItemLabelAnchor最好选OUTSIDE，因为INSIDE显示不出来
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT));
        // 可以进一步调整数值的位置，但是得根据ItemLabelAnchor选择情况
        renderer.setItemLabelAnchorOffset(15);

        FileOutputStream out = null;
        try {


            out = new FileOutputStream(path + "/marketing.jpg");

            //500，500为图片大小
            ChartUtilities.writeChartAsJPEG(out, 0.5f, chart, 1200, 500, null);
            return "background/marketing";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        session.setAttribute("message", "出错啦！");
        return "message";
    }

    @RequestMapping("findOrderitemByPdesc")
    public String findOrderitemByPdesc(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, HttpServletRequest request, HttpSession session) {
        String pdescription = request.getParameter("pdescription");

        int uid = ((User) session.getAttribute("LOGIN_USER")).getUid();

        PageHelper.startPage(pageNo,pageSize);
        PageInfo<Orderitem> pageInfo = new PageInfo<>(orderitemService.findOrderitemByPdesc(uid, pdescription));

        session.setAttribute("orderitemPage", pageInfo);
        return "orderitemList";
    }
}
