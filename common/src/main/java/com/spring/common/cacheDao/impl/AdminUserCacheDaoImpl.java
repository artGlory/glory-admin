package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheCallback;
import com.spring.common.cacheDao.AdminUserCacheDao;
import com.spring.common.domain.condition.AdminUserCondition;
import com.spring.common.mybatis.AdminUserMapper;
import com.spring.common.po.AdminUser;
import com.spring.common.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:21
 */
@Component
public class AdminUserCacheDaoImpl extends AbstractCacheDao implements AdminUserCacheDao {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public int deleteByPrimaryKey(String uk) {
        int result = adminUserMapper.deleteByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insert(AdminUser record) {
        int result = adminUserMapper.insert(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insertSelective(AdminUser record) {
        int result = adminUserMapper.insertSelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public AdminUser selectByPrimaryKey(String uk) {
        AdminUser result = getCacheUtil().get(getNamespace(), "selectByPrimaryKey.uk." + uk, new CacheCallback<AdminUser>() {
            @Override
            public AdminUser getObject() throws Exception {
                return adminUserMapper.selectByPrimaryKey(uk);
            }

            @Override
            public List<AdminUser> getObjectList() throws Exception {
                return null;
            }
        });
        return result;
    }

    @Override
    public AdminUser selectByUsername(String username) {
        AdminUser result = getCacheUtil().get(getNamespace(), "selectByUsername.username." + username,
                new CacheCallback<AdminUser>() {
                    @Override
                    public AdminUser getObject() throws Exception {
                        return adminUserMapper.selectByUsername(username);
                    }

                    @Override
                    public List<AdminUser> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(AdminUser record) {
        int result = adminUserMapper.updateByPrimaryKeySelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKey(AdminUser record) {
        int result = adminUserMapper.updateByPrimaryKey(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public List<AdminUser> listByCondition(AdminUserCondition adminUserCondition) {
        List<AdminUser> result = getCacheUtil().getList(getNamespace(),
                "listByCondition." + adminUserCondition.toString(),
                new CacheCallback<AdminUser>() {
                    @Override
                    public AdminUser getObject() throws Exception {
                        return null;
                    }

                    @Override
                    public List<AdminUser> getObjectList() throws Exception {
                        return adminUserMapper.listByCondition(adminUserCondition);
                    }
                });
        return result;
    }

    @Override
    public Long countByCondition(AdminUserCondition adminUserCondition) {
        Object result = getCacheUtil().get(getNamespace(), "countByCondition." + adminUserCondition.toString(),
                new CacheCallback<Long>() {
                    @Override
                    public Long getObject() throws Exception {
                        return adminUserMapper.countByCondition(adminUserCondition);
                    }

                    @Override
                    public List<Long> getObjectList() throws Exception {
                        return null;
                    }
                });
        return ObjectUtils.toLong(result);
    }
}