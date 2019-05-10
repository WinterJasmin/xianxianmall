package com.zhku.xianxianmall.service;

import com.zhku.xianxianmall.domain.Admin;

/**
 * @author: qwe
 * @date: 2019/4/29 14:29
 * @description:
 */
public interface AdminService {

    Admin findAdminByAidAndPwd(String aid, String pwd);

    void updatePwd(String aid, String newPwd);
}
