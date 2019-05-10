package com.zhku.xianxianmall.service;

import com.zhku.xianxianmall.domain.Cart;

import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/15 21:08
 * @description:
 */
public interface CartService {
    void addCart(Cart cart);

    List<Cart> findCart(int uid);

    void delCart(int cid);

    Cart findCartByCid(int cid);

    List<Cart> findCartByPdesc(int uid, String pdescription);
}
