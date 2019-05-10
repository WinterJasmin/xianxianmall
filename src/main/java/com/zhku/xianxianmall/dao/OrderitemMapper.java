package com.zhku.xianxianmall.dao;

import com.github.pagehelper.Page;
import com.zhku.xianxianmall.domain.Marketing;
import com.zhku.xianxianmall.domain.OrderTable;
import com.zhku.xianxianmall.domain.Orderitem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderitemMapper {
    @Insert("insert into orderitem(uid,pid,oprice,total,account,ostate,odate,osize,opic,pdescription) values (#{uid},#{pid},#{oprice},#{total},#{account},#{ostate},#{odate},#{osize},#{opic},#{pdescription})")
    @Options(useGeneratedKeys=true,keyProperty="oid")
    void submitOrderitem(Orderitem orderitem);

    @Update("update orderitem set ostate=1 where oid=#{oid}")
    void paySuccess(int oid);

    @Select("select * from orderitem where uid=#{uid} ORDER BY odate DESC")
    Page<Orderitem> findOrderitemByUid(int uid);

    @Delete("delete from orderitem where oid=#{oid}")
    void delOrderitemList(int oid);

    @Select("SELECT COUNT(*) FROM orderitem WHERE uid=#{uid}")
    int countOrderitem(int uid);

    @Select("select * from orderitem where oid=#{oid}")
    Orderitem findOrderitemByOid(int oid);

    @Update("update orderitem set ostate=2 where oid=#{oid}")
    void delivery(Integer oid);

    @Update("update orderitem set ostate=3 where oid=#{oid}")
    void ensureDelivery(int oid);

    @Select("SELECT o.pid,p.pname,MONTH(o.odate) AS DATE,SUM(o.total) AS COUNT FROM orderitem o, product p WHERE o.pid = p.pid AND o.ostate > 0 GROUP BY o.pid,DATE")
    List<Marketing> getDataSet();

    @Select("select * from orderitem where uid=#{uid} and pdescription like concat('%',#{pdescription},'%') ORDER BY odate DESC")
    Page<Orderitem> findOrderitemByPdesc(int uid, String pdescription);
}