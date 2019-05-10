package com.zhku.xianxianmall.service.impl;

import com.github.pagehelper.Page;
import com.zhku.xianxianmall.dao.OrderitemMapper;
import com.zhku.xianxianmall.domain.Marketing;
import com.zhku.xianxianmall.domain.OrderTable;
import com.zhku.xianxianmall.domain.Orderitem;
import com.zhku.xianxianmall.service.OrderitemService;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/22 20:21
 * @description:
 */
@Service(value = "orderitemService")
public class OrderitemServicImpl implements OrderitemService {

    @Resource
    private OrderitemMapper orderitemMapper;


    @Override
    public void submitOrderitem(Orderitem orderitem) {
        orderitemMapper.submitOrderitem(orderitem);
    }

    @Override
    public void paySuccess(int rid) {
        orderitemMapper.paySuccess(rid);
    }

    @Override
    public Page<Orderitem> findOrderitemByUid(int uid) {
        return orderitemMapper.findOrderitemByUid(uid);
    }

    @Override
    public void delOrderitemList(int oid) {
        orderitemMapper.delOrderitemList(oid);
    }

    @Override
    public int countOrderitem(int uid) {
        return orderitemMapper.countOrderitem(uid);
    }

    @Override
    public Orderitem findOrderitemByOid(int oid) {
        return orderitemMapper.findOrderitemByOid(oid);
    }

    @Override
    public void delivery(Integer i) {
        orderitemMapper.delivery(i);
    }

    @Override
    public void ensureDelivery(int oid) {
        orderitemMapper.ensureDelivery(oid);
    }

    @Override
    public CategoryDataset getDataSet() {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();

        List<Marketing> list = orderitemMapper.getDataSet();
        for (Marketing marketing : list) {
            ds.addValue(marketing.getCount(), marketing.getDate()+"月", marketing.getPname());
        }

        return ds;
    }

    @Override
    public Page<Orderitem> findOrderitemByPdesc(int uid, String pdescription) {
        return orderitemMapper.findOrderitemByPdesc(uid, pdescription);
    }
}
