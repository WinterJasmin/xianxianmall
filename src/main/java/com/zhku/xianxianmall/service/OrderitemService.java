package com.zhku.xianxianmall.service;

import com.github.pagehelper.Page;
import com.zhku.xianxianmall.domain.Orderitem;
import org.jfree.data.category.CategoryDataset;

import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/22 20:21
 * @description:
 */
public interface OrderitemService {

    void submitOrderitem(Orderitem orderitem);

    void paySuccess(int oid);

    Page<Orderitem> findOrderitemByUid(int uid);

    void delOrderitemList(int oid);

    int countOrderitem(int uid);

    Orderitem findOrderitemByOid(int oid);

    void delivery(Integer i);

    void ensureDelivery(int oid);

    CategoryDataset getDataSet();

    Page<Orderitem> findOrderitemByPdesc(int uid, String pdescription);
}
