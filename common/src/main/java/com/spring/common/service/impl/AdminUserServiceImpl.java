package com.spring.common.service.impl;

import com.spring.common.mybatis.AdminUserMapper;
import com.spring.common.po.AdminUser;
import com.spring.common.service.AdminUserService;
import com.spring.common.utils.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Transactional
    @Override
    public int addAdminUser(AdminUser adminUser) {
        adminUser.setCreateTime(new Moment().toDate());
        adminUser.setUpdateTime(adminUser.getCreateTime());
        return adminUserMapper.insert(adminUser);
    }

    @Transactional
    @Override
    public int deleteAdminUser(AdminUser adminUser) {
        return adminUserMapper.deleteByPrimaryKey(adminUser.getUk());
    }

    @Transactional
    @Override
    public int updateAdminToken(AdminUser adminUser) {
        AdminUser targetAdminUser = AdminUser.builder()
                .uk(adminUser.getUk())
                .nowToken(adminUser.getNowToken())
                .updateTime(new Moment().toDate())
                .build();
        return adminUserMapper.updateByPrimaryKeySelective(targetAdminUser);
    }

    @Transactional
    @Override
    public int updateAdminPassword(AdminUser adminUser) {
        AdminUser targetAdminUser = AdminUser.builder()
                .uk(adminUser.getUk())
                .password(adminUser.getPassword())
                .updateTime(new Moment().toDate())
                .build();
        return adminUserMapper.updateByPrimaryKeySelective(targetAdminUser);
    }

    @Transactional
    @Override
    public int updateAdminRole(AdminUser adminUser) {
        AdminUser targetAdminUser = AdminUser.builder()
                .uk(adminUser.getUk())
                .roleUk(adminUser.getRoleUk())
                .updateTime(new Moment().toDate())
                .build();
        return adminUserMapper.updateByPrimaryKeySelective(targetAdminUser);
    }

    @Transactional
    @Override
    public int updateAdminGoogleKey(AdminUser adminUser) {
        AdminUser targetAdminUser = AdminUser.builder()
                .uk(adminUser.getUk())
                .googleKey(adminUser.getGoogleKey())
                .updateTime(new Moment().toDate())
                .build();
        return adminUserMapper.updateByPrimaryKeySelective(targetAdminUser);
    }

    @Transactional
    @Override
    public int updateAdminLoginWithGoogleStatus(AdminUser adminUser) {
        AdminUser targetAdminUser = AdminUser.builder()
                .uk(adminUser.getUk())
                .googleLogin(adminUser.getGoogleLogin())
                .updateTime(new Moment().toDate())
                .build();
        return adminUserMapper.updateByPrimaryKeySelective(targetAdminUser);
    }

    @Transactional
    @Override
    public int updateAdminLoginStatue(AdminUser adminUser) {
        AdminUser targetAdminUser = AdminUser.builder()
                .uk(adminUser.getUk())
                .forbidLogin(adminUser.getForbidLogin())
                .updateTime(new Moment().toDate())
                .build();
        return adminUserMapper.updateByPrimaryKeySelective(targetAdminUser);
    }

    @Transactional
    @Override
    public int deleteAdminGoogleKey(AdminUser adminUser) {
        AdminUser targetAdminUser = AdminUser.builder()
                .uk(adminUser.getUk())
                .googleLogin(AdminUser.google_login_not_master)
                .updateTime(new Moment().toDate())
                .build();
        int result =adminUserMapper.updateByPrimaryKeySelective(targetAdminUser);
        if (result!=1) throw new IllegalArgumentException("删除Google登陆失败");
            result=adminUserMapper.deleteGoogleKeyByPrimaryKey(adminUser.getUk());
        return result;
    }

}
