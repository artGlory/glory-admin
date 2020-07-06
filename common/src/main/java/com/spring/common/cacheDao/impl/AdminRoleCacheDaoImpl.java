package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheCallback;
import com.spring.common.cacheDao.AdminRoleCacheDao;
import com.spring.common.mybatis.AdminRoleMapper;
import com.spring.common.po.AdminRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminRoleCacheDaoImpl extends AbstractCacheDao implements AdminRoleCacheDao {
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public int deleteByPrimaryKey(String uk) {
        int result = adminRoleMapper.deleteByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insert(AdminRole record) {
        int result = adminRoleMapper.insert(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insertSelective(AdminRole record) {
        int result = adminRoleMapper.insertSelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public AdminRole selectByPrimaryKey(String uk) {
        AdminRole result = getCacheUtil().get(getNamespace(), "selectByPrimaryKey.uk." + uk, new CacheCallback<AdminRole>() {
            @Override
            public AdminRole getObject() throws Exception {
                return adminRoleMapper.selectByPrimaryKey(uk);
            }

            @Override
            public List<AdminRole> getObjectList() throws Exception {
                return null;
            }
        });
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(AdminRole record) {
        int result = adminRoleMapper.updateByPrimaryKeySelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKey(AdminRole record) {
        int result = adminRoleMapper.updateByPrimaryKey(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public List<AdminRole> listAll() {
        List<AdminRole> result = getCacheUtil().getList(getNamespace(), "listAll", new CacheCallback<AdminRole>() {
            @Override
            public AdminRole getObject() throws Exception {
                return null;
            }

            @Override
            public List<AdminRole> getObjectList() throws Exception {
                return adminRoleMapper.listAll();
            }
        });
        return result;
    }

    @Override
    public List<AdminRole> listAllByParentUk(String parentUk) {
        List<AdminRole> result = getCacheUtil().getList(getNamespace(), "listAllByParentUk.parentUk." + parentUk,
                new CacheCallback<AdminRole>() {
                    @Override
                    public AdminRole getObject() throws Exception {
                        return null;
                    }

                    @Override
                    public List<AdminRole> getObjectList() throws Exception {
                        return adminRoleMapper.listAllByParentUk(parentUk);
                    }
                });
        return result;
    }

    @Override
    public Long countAll() {
        long result = getCacheUtil().get(getNamespace(), "countAll", new CacheCallback<Long>() {
            @Override
            public Long getObject() throws Exception {
                return adminRoleMapper.countAll();
            }

            @Override
            public List<Long> getObjectList() throws Exception {
                return null;
            }
        });
        return result;
    }

}