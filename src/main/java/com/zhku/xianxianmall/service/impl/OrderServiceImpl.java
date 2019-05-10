package com.zhku.xianxianmall.service.impl;

import com.zhku.xianxianmall.dao.OrderMapper;
import com.zhku.xianxianmall.domain.OrderTable;
import com.zhku.xianxianmall.domain.Orderitem;
import com.zhku.xianxianmall.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/21 21:19
 * @description:
 */
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public void submitOrder(OrderTable order) {
        orderMapper.submitOrder(order);
    }

    @Override
    public List<OrderTable> findAllOrder() {
        return orderMapper.findAllOrder();
    }

    @Override
    public List<OrderTable> findOrderByRid(int rid) {
        return orderMapper.findOrderByRid(rid);
    }

    @Override
    public List<OrderTable> findDeliveryOrder() {
        return orderMapper.findDeliveryOrder();
    }

    @Override
    public List<OrderTable> findDeliveryByRid(int rid) {
        return orderMapper.findDeliveryByRid(rid);
    }

    @Override
    public void delivery(int rid) {
        orderMapper.delivery(rid);
    }

    @Override
    public List<Integer> findOidByRid(int rid) {
        return orderMapper.findOidByRid(rid);
    }

}
