package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheCallback;
import com.spring.common.cacheDao.AdminRolePrivilegeCacheDao;
import com.spring.common.mybatis.AdminRolePrivilegeMapper;
import com.spring.common.po.AdminRolePrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 22:58:04
 */
@Component
public class AdminRolePrivilegeCacheDaoImpl extends AbstractCacheDao implements AdminRolePrivilegeCacheDao {
    @Autowired
    private AdminRolePrivilegeMapper adminRolePrivilegeMapper;

    @Override
    public int deleteByPrimaryKey(String uk) {
        int result = adminRolePrivilegeMapper.deleteByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insert(AdminRolePrivilege record) {
        int result = adminRolePrivilegeMapper.insert(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insertSelective(AdminRolePrivilege record) {
        int result = adminRolePrivilegeMapper.insertSelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public AdminRolePrivilege selectByPrimaryKey(String uk) {
        AdminRolePrivilege result = getCacheUtil().get(getNamespace(),
                "selectByPrimaryKey.uk." + uk, new CacheCallback<AdminRolePrivilege>() {
                    @Override
                    public AdminRolePrivilege getObject() throws Exception {
                        return adminRolePrivilegeMapper.selectByPrimaryKey(uk);
                    }

                    @Override
                    public List<AdminRolePrivilege> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public AdminRolePrivilege selectByRolePrivilege(String roleUk, String privilegeUk) {
        AdminRolePrivilege result = getCacheUtil().get(getNamespace()
                , "selectByRolePrivilege.roleUk." + roleUk + ".privilegeUk." + privilegeUk, new CacheCallback<AdminRolePrivilege>() {
                    @Override
                    public AdminRolePrivilege getObject() throws Exception {
                        return adminRolePrivilegeMapper.selectByRolePrivilege(roleUk, privilegeUk);
                    }

                    @Override
                    public List<AdminRolePrivilege> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public List<AdminRolePrivilege> listByRoleUk(String roleUk) {
        List<AdminRolePrivilege> result = getCacheUtil().getList(getNamespace(), "listByRoleUk.roleUk." + roleUk,
                new CacheCallback<AdminRolePrivilege>() {
                    @Override
                    public AdminRolePrivilege getObject() throws Exception {
                        return null;
                    }

                    @Override
                    public List<AdminRolePrivilege> getObjectList() throws Exception {
                        return adminRolePrivilegeMapper.listByRoleUk(roleUk);
                    }
                });
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(AdminRolePrivilege record) {
        int result = adminRolePrivilegeMapper.updateByPrimaryKeySelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKey(AdminRolePrivilege record) {
        int result = adminRolePrivilegeMapper.updateByPrimaryKey(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }
}