package com.zhku.xianxianmall.dao;

import com.zhku.xianxianmall.domain.OrderTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {

    @Insert("insert into ordertable(oid,uid,rstate,date) values (#{oid},#{uid},#{rstate},#{date})")
    void submitOrder(OrderTable order);

    @Select("select * from ordertable")
    List<OrderTable> findAllOrder();

    @Select("select * from ordertable where rid=#{rid}")
    List<OrderTable> findOrderByRid(int rid);

    @Select("select * from ordertable where rstate=0")
    List<OrderTable> findDeliveryOrder();

    @Select("select * from ordertable where rid=#{rid} and rstate=0")
    List<OrderTable> findDeliveryByRid(int rid);

    @Update("update ordertable set rstate=1 where rid=#{rid}")
    void delivery(int rid);

    @Select("select oid from ordertable where rid=#{rid}")
    List<Integer> findOidByRid(int rid);
}