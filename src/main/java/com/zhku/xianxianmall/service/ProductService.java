package com.zhku.xianxianmall.service;

import com.zhku.xianxianmall.domain.Product;

import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/12 15:51
 * @description:
 */
public interface ProductService {
    public void insert(Product product);

    public List<Product> finaAll();

    public List<Product> findProductByLikePname(String key);

    public void delProductByPid(int pid);

    public void updatePstate(int pid);

    public Product findProductByPid(int pid);

    public void updateProduct(Product product);

    public List<Product> selectNew();

    public List<Product> selectByPclass(int pclass);

    public Product findDetails(int pid);

    public List<Integer> selectHotSales();
}
