package com.zhku.xianxianmall.dao;

import com.zhku.xianxianmall.domain.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: qwe
 * @date: 2019/4/15 21:06
 * @description:
 */
public interface CartMapper {
    @Insert("insert into cart(pid,uid,cprice,total,account,cdate,csize,ccolor,cpic,pdescription) values (#{pid},#{uid},#{cprice}," +
            "#{total},#{account},#{cdate},#{csize},#{ccolor},#{cpic},#{pdescription})")
    void addCart(Cart cart);

    @Select("select * from cart where uid=#{uid} order by cdate desc")
    List<Cart> findCart(int uid);

    @Delete("delete from cart where cid=#{cid}")
    void delCart(int cid);

    @Select("select * from cart where cid=#{cid}")
    Cart findCartByCid(int cid);

    @Select("select * from cart where uid=#{uid} and pdescription like concat('%',#{pdescription},'%') order by cdate desc")
    List<Cart> findCartByPdesc(int uid, String pdescription);
}
