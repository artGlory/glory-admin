package com.spring.common.service.impl;

import com.spring.common.cacheDao.AdminRoleCacheDao;
import com.spring.common.cacheDao.AdminUserCacheDao;
import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminUser;
import com.spring.common.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminRoleCacheDao adminRoleCacheDao;
    @Autowired
    private AdminUserCacheDao adminUserCacheDao;

    @Transactional
    @Override
    public int addAdminRole(AdminRole adminRole) {
        return adminRoleCacheDao.insert(adminRole);
    }

    @Transactional
    @Override
    public int updateAdminRole(AdminRole adminRole) {
        return adminRoleCacheDao.updateByPrimaryKey(adminRole);
    }

    @Transactional
    @Override
    public int deleteAdminRole(AdminRole adminRole) {
        return adminRoleCacheDao.deleteByPrimaryKey(adminRole.getUk());
    }

    @Transactional
    @Override
    public int addAdminUser(AdminUser adminUser) {
        return adminUserCacheDao.insert(adminUser);
    }

    @Transactional
    @Override
    public int updateAdminUser(AdminUser adminUser) {
        return adminUserCacheDao.updateByPrimaryKey(adminUser);
    }

    @Transactional
    @Override
    public int deleteAdminUser(AdminUser adminUser) {
        return adminUserCacheDao.deleteByPrimaryKey(adminUser.getUk());
    }

}
