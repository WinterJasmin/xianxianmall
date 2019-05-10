package com.zhku.xianxianmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhku.xianxianmall.domain.Orderitem;
import com.zhku.xianxianmall.domain.Product;
import com.zhku.xianxianmall.domain.ProductExample;
import com.zhku.xianxianmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/12 14:10
 * @description:
 */
@RequestMapping("/product")
@Controller
@PropertySource({"classpath:application.properties"})
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 指定图片位置
     **/
    @Value("${web.images-path}")
    private String imgPath;

    @PostMapping("addProduct")
    public String addProduct(HttpServletRequest request, HttpSession session) {
        String pname = request.getParameter("pname");
        String pdescription = request.getParameter("pdiscr");
        Double pprice = Double.valueOf(request.getParameter("pprice"));
        int pclass = Integer.valueOf(request.getParameter("pclass"));
        int pstate = 1;
        Date date = new Date();
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        String picaddress = "";

        // 得到所有上传的文件
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pic");
        MultipartFile file = null;
        // 遍历上传的文件集，保存文件
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                // 文件名为：pname_i.jpg
                File dest = new File(imgPath +pname + "_" + i + fileName.substring(fileName.lastIndexOf(".")));

                try {
                    // 用于文件保存（效率和操作比原先用FileOutStream方便和高效）
                    file.transferTo(dest);
                } catch (IOException e) {
                    session.setAttribute("message","第 " + i + " 个文件上传失败，原因为=>" + e.getMessage());
                    return "message";
                }

                // 将文件名拼接到picaddress中
                if (picaddress == "") {
                    picaddress += (pname + "_" + i + fileName.substring(fileName.lastIndexOf(".")));
                } else {
                    picaddress += ("," + pname + "_" + i + fileName.substring(fileName.lastIndexOf(".")));
                }

            } else {
                session.setAttribute("message","第 " + (i + 1) + " 个文件上传失败，因为它是空的");
                return "message";
            }
        }

        Product product = new Product();
        product.setColor(color);
        product.setPclass(pclass);
        product.setPdate(date);
        product.setPdescription(pdescription);
        product.setPicaddress(picaddress);
        product.setPname(pname);
        product.setPprice(pprice);
        product.setPstate(pstate);
        product.setSize(size);
        System.out.println(product);

        // 将product插入数据库
        productService.insert(product);

        session.setAttribute("message","上架商品成功!");
        return "message";

    }

    /**
     * @Author qwe
     * @Description 后台管理系统的查看所有商品
     * @Date  2019/4/14
     * @Param [session]
     * @return java.lang.String
     **/
    @RequestMapping("findAll")
    public String findAll(HttpSession session) {
        List<Product> productList = productService.finaAll();
        session.setAttribute("productList", productList);
        return "background/productList";
    }

    /**
     * @Author qwe
     * @Description 普通用户和一般浏览者的查看所有商品
     * @Date  2019/4/14
     * @Param [session]
     * @return java.lang.String
     **/
    @RequestMapping("selectAll")
    public String selectAll(HttpSession session) {
        List<Product> pclasslist = new ArrayList<>();

        Iterator<Product> it = productService.finaAll().iterator();
        while (it.hasNext()) {
            Product p = it.next();

            String pic = p.getPicaddress();
            if (pic.indexOf(",") != -1) {
                p.setPicaddress(pic.split(",")[0]);
            }

            pclasslist.add(p);
        }

        session.setAttribute("pclasslist", pclasslist);

        return "search";
    }

    /**
     * @Author qwe
     * @Description 后台管理系统的搜索商品
     * @Date  2019/4/14
     * @Param [pname, session]
     * @return java.lang.String
     **/
    @GetMapping("findProductByLikePname")
    public String findProductByLikePname(String pname, HttpSession session) {
        if (pname == null || pname == "") {
            return findAll(session);
        }
        List<Product> productList = productService.findProductByLikePname(pname);
        session.setAttribute("productList", productList);
        return "background/productList";
    }

    /**
     * @Author qwe
     * @Description 普通用户和一般浏览者的搜索商品
     * @Date  2019/4/14
     * @Param [pname, session]
     * @return java.lang.String
     **/
    @GetMapping("findByLikePname")
    public String findByLikePname(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, String pname, HttpSession session) {
        List<Product> pclasslist = new ArrayList<>();

        PageHelper.startPage(pageNo,pageSize);

        List<Product> list = productService.findProductByLikePname(pname);
        System.out.println("list.size:" + list.size());

        if (list.size() == 0) {
            session.setAttribute("nullProduct", "没找到相关商品！");
            session.removeAttribute("pclasslist");
            return "search";
        }

        Iterator<Product> it = list.iterator();
        while (it.hasNext()) {
            Product p = it.next();

            String pic = p.getPicaddress();
            if (pic.indexOf(",") != -1) {
                p.setPicaddress(pic.split(",")[0]);
            }

            pclasslist.add(p);
        }

        session.removeAttribute("nullProduct");
        session.setAttribute("pclasslist", pclasslist);

        return "search";
    }

    @RequestMapping("delProductByPid")
    public String delProductByPid(int pid, HttpSession session) {
        productService.delProductByPid(pid);
        session.setAttribute("message", "删除成功！");
        return "message";
    }

    /**
     * @Author qwe
     * @Description 下架商品
     * @Date  2019/4/13
     * @Param [pid]
     * @return java.lang.String
     **/
    @RequestMapping("updatePstate")
    public String updatePstate(int pid, HttpSession session) {
        productService.updatePstate(pid);
        session.setAttribute("message", "下架成功！");
        return "message";
    }

    @RequestMapping("openUpdateProductByPid")
    public String openUpdateProductByPid(int pid, HttpSession session) {
        Product product = productService.findProductByPid(pid);
        session.setAttribute("product", product);
        return "background/openUpdateProduct";
    }

    @PostMapping("updateProduct")
    public String updateProduct(HttpServletRequest request, HttpSession session) {
        String pname = request.getParameter("pname");
        String pid = request.getParameter("pid");
        String pdescription = request.getParameter("pdiscr");
        Double pprice = Double.valueOf(request.getParameter("pprice"));
        int pclass = Integer.valueOf(request.getParameter("pclass"));
        int pstate = 1;
        Date date = new Date();
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        String picaddress = "";

        // 得到所有上传的文件
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pic");
        MultipartFile file = null;
        // 遍历上传的文件集，保存文件
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                // 文件名为：pname_i.jpg
                File dest = new File(imgPath +pname + "_" + i + fileName.substring(fileName.lastIndexOf(".")));

                try {
                    // 用于文件保存（效率和操作比原先用FileOutStream方便和高效）
                    file.transferTo(dest);
                } catch (IOException e) {
                    session.setAttribute("message","第 " + i + " 个文件上传失败，原因为=>" + e.getMessage());
                    return "message";
                }

                // 将文件名拼接到picaddress中
                if (picaddress == "") {
                    picaddress += (pname + "_" + i + fileName.substring(fileName.lastIndexOf(".")));
                } else {
                    picaddress += ("," + pname + "_" + i + fileName.substring(fileName.lastIndexOf(".")));
                }

            } else {
                session.setAttribute("message","第 " + (i + 1) + " 个文件上传失败，因为它是空的");
                return "message";
            }
        }

        Product product = new Product();
        product.setPid(Integer.valueOf(pid));
        product.setColor(color);
        product.setPclass(pclass);
        product.setPdate(date);
        product.setPdescription(pdescription);
        product.setPicaddress(picaddress);
        product.setPname(pname);
        product.setPprice(pprice);
        product.setPstate(pstate);
        product.setSize(size);
        System.out.println(product);

        productService.updateProduct(product);

        session.setAttribute("message","商品信息更新成功!");
        return "message";
    }

    @RequestMapping("selectByPclass")
    public String selectByPclass(int pclass, HttpSession session) {

        List<Product> pclasslist = new ArrayList<>();

        Iterator<Product> it = productService.selectByPclass(pclass).iterator();
        while (it.hasNext()) {
            Product p = it.next();

            String pic = p.getPicaddress();
            if (pic.indexOf(",") != -1) {
                p.setPicaddress(pic.split(",")[0]);
            }

            System.out.println(p.getPicaddress());

            pclasslist.add(p);
        }

        session.setAttribute("pclasslist", pclasslist);

        return "search";
    }

    @RequestMapping("findDetails")
    public String findDetails(int pid, HttpSession session) {
        Product p = productService.findDetails(pid);
        String pic = p.getPicaddress();
        if (pic.indexOf(",") != -1) {
            p.setPicaddress(pic.split(",")[0]);
        }

        ProductExample pe = new ProductExample(p.getPid(), p.getPname(), p.getPdescription(), p.getPprice(), p.getPstate(), p.getPclass(), p.getPdate(), p.getSize(), p.getColor(), p.getPicaddress(), p.getSize().split(","), p.getColor().split(","));

        session.removeAttribute("detailMes");
        session.setAttribute("product", pe);
        return "detail";
    }
}
