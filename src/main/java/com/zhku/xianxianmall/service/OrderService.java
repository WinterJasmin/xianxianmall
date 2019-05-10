package com.zhku.xianxianmall.service;

import com.zhku.xianxianmall.domain.OrderTable;

import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/21 21:19
 * @description:
 */
public interface OrderService {
    void submitOrder(OrderTable order);

    List<OrderTable> findAllOrder();

    List<OrderTable> findOrderByRid(int rid);

    List<OrderTable> findDeliveryOrder();

    List<OrderTable> findDeliveryByRid(int rid);

    void delivery(int rid);

    List<Integer> findOidByRid(int rid);
}
