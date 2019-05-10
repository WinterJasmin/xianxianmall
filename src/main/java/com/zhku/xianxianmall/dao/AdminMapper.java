package com.zhku.xianxianmall.dao;

import com.zhku.xianxianmall.domain.Admin;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminMapper {

    @Select("select * from admin where aid=#{aid} and pwd=#{pwd}")
    Admin findAdminByAidAndPwd(String aid, String pwd);

    @Update("update admin set pwd=#{newPwd} where aid=#{aid}")
    void updatePwd(String aid, String newPwd);
}