package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheCallback;
import com.spring.common.cacheDao.AdminPrivilegeCacheDao;
import com.spring.common.mybatis.AdminPrivilegeMapper;
import com.spring.common.po.AdminPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminPrivilegeCacheDaoImpl extends AbstractCacheDao implements AdminPrivilegeCacheDao {
    @Autowired
    private AdminPrivilegeMapper adminPrivilegeMapper;

    @Override
    public int deleteByPrimaryKey(String uk) {
        int result = adminPrivilegeMapper.deleteByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insert(AdminPrivilege record) {
        int result = adminPrivilegeMapper.insert(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insertSelective(AdminPrivilege record) {
        int result = adminPrivilegeMapper.insertSelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public AdminPrivilege selectByPrimaryKey(String uk) {
        AdminPrivilege result = adminPrivilegeMapper.selectByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public AdminPrivilege selectByPath(String privilegePath) {
        AdminPrivilege result = getCacheUtil().get(getNamespace(), "selectByPath.privilegePath." + privilegePath,
                new CacheCallback<AdminPrivilege>() {
                    @Override
                    public AdminPrivilege getObject() throws Exception {
                        return adminPrivilegeMapper.selectByPath(privilegePath);
                    }

                    @Override
                    public List<AdminPrivilege> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public List<AdminPrivilege> listAll() {
        List<AdminPrivilege> result = getCacheUtil().getList(getNamespace(), "selectAll",
                new CacheCallback<AdminPrivilege>() {
                    @Override
                    public AdminPrivilege getObject() throws Exception {
                        return null;
                    }

                    @Override
                    public List<AdminPrivilege> getObjectList() throws Exception {
                        return adminPrivilegeMapper.listAll();
                    }
                });
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(AdminPrivilege record) {
        int result = adminPrivilegeMapper.updateByPrimaryKeySelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKey(AdminPrivilege record) {
        int result = adminPrivilegeMapper.updateByPrimaryKey(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }
}