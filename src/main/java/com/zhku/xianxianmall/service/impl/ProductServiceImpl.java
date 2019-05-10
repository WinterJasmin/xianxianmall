package com.zhku.xianxianmall.service.impl;

import com.zhku.xianxianmall.dao.ProductMapper;
import com.zhku.xianxianmall.domain.Product;
import com.zhku.xianxianmall.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/12 15:51
 * @description:
 */
@Service(value = "productService")
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public void insert(Product product) {
        productMapper.insert(product);
    }

    @Override
    public List<Product> finaAll() {
        return productMapper.finaAll();
    }

    @Override
    public List<Product> findProductByLikePname(String key) {
        return productMapper.findProductByLikePname(key);
    }

    @Override
    public void delProductByPid(int pid) {
        productMapper.delProductByPid(pid);
    }

    @Override
    public void updatePstate(int pid) {
        productMapper.updatePstate(pid);
    }

    @Override
    public Product findProductByPid(int pid) {
        return productMapper.findProductByPid(pid);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public List<Product> selectNew() {
        return productMapper.selectNew();
    }

    @Override
    public List<Product> selectByPclass(int pclass) {
        return productMapper.selectByPclass(pclass);
    }

    @Override
    public Product findDetails(int pid) {
        return productMapper.findDetails(pid);
    }

    @Override
    public List<Integer> selectHotSales() {
        return productMapper.selectHotSales();
    }


}
