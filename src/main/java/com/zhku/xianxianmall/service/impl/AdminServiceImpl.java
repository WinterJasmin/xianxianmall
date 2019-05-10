package com.zhku.xianxianmall.service.impl;

import com.zhku.xianxianmall.dao.AdminMapper;
import com.zhku.xianxianmall.domain.Admin;
import com.zhku.xianxianmall.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: qwe
 * @date: 2019/4/29 14:31
 * @description:
 */
@Service(value = "adminService")
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin findAdminByAidAndPwd(String aid, String pwd) {
        return adminMapper.findAdminByAidAndPwd(aid, pwd);
    }

    @Override
    public void updatePwd(String aid, String newPwd) {
        adminMapper.updatePwd(aid, newPwd);
    }
}
