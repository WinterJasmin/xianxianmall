package com.zhku.xianxianmall.service.impl;

import com.zhku.xianxianmall.dao.CartMapper;
import com.zhku.xianxianmall.domain.Cart;
import com.zhku.xianxianmall.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/15 21:08
 * @description:
 */
@Service(value = "cartService")
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Override
    public void addCart(Cart cart) {
        cartMapper.addCart(cart);
    }

    @Override
    public List<Cart> findCart(int uid) {
        return cartMapper.findCart(uid);
    }

    @Override
    public void delCart(int cid) {
        cartMapper.delCart(cid);
    }

    @Override
    public Cart findCartByCid(int cid) {
        return cartMapper.findCartByCid(cid);
    }

    @Override
    public List<Cart> findCartByPdesc(int uid, String pdescription) {
        return cartMapper.findCartByPdesc(uid, pdescription);
    }
}
