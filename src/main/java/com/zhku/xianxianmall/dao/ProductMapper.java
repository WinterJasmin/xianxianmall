package com.zhku.xianxianmall.dao;

import com.zhku.xianxianmall.domain.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper {

    @Insert("insert into product(pname,pdescription,pprice,pstate,pclass,pdate,size,color,picaddress) values (#{pname},#{pdescription},#{pprice},#{pstate},#{pclass},#{pdate},#{size},#{color},#{picaddress})")
    void insert(Product product);

    @Select("select * from product")
    List<Product> finaAll();

    @Select("select * from product where pname like concat('%',#{key},'%')")
    List<Product> findProductByLikePname(String key);

    @Delete("delete from product where pid=#{pid}")
    void delProductByPid(int pid);

    /**
     * @Author qwe
     * @Description 下架商品
     * @Date  2019/4/13
     * @Param [pid]
     * @return void
     **/
    @Update("update product set pstate=0 where pid=#{pid}")
    void updatePstate(int pid);

    @Select("select * from product where pid=#{pid}")
    Product findProductByPid(int pid);

    @Update("update product set pname=#{pname},pdescription=#{pdescription},pprice=#{pprice},pclass=#{pclass},pdate=#{pdate},size=#{size},color=#{color},picaddress=#{picaddress} where pid=#{pid}")
    void updateProduct(Product product);

    @Select("SELECT * FROM product ORDER BY pdate DESC LIMIT 0,4")
    List<Product> selectNew();

    @Select("select * from product where pclass=#{pclass}")
    List<Product> selectByPclass(int pclass);

    @Select("select * from product where pid=#{pid}")
    Product findDetails(int pid);

    @Select("SELECT pid FROM orderitem WHERE ostate > 0 GROUP BY pid ORDER BY COUNT(pid) DESC LIMIT 0,4")
    List<Integer> selectHotSales();
}