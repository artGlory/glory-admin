package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheCallback;
import com.spring.common.cacheDao.LogAdminLoginCacheDao;
import com.spring.common.domain.condition.AdminLoginLogCondition;
import com.spring.common.mybatis.LogAdminLoginMapper;
import com.spring.common.po.LogAdminLogin;
import com.spring.common.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-07-02 00:43:22
 */
@Component
public class LogAdminLoginCacheDaoImpl extends AbstractCacheDao implements LogAdminLoginCacheDao {
    @Autowired
    private LogAdminLoginMapper adminLoginMapper;

    @Override
    public int deleteByPrimaryKey(String uk) {
        int result = adminLoginMapper.deleteByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insert(LogAdminLogin record) {
        int result = adminLoginMapper.insert(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insertSelective(LogAdminLogin record) {
        int result = adminLoginMapper.insertSelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public LogAdminLogin selectByPrimaryKey(String uk) {
        LogAdminLogin result = getCacheUtil().get(getNamespace(), "selectByPrimaryKey.uk." + uk,
                new CacheCallback<LogAdminLogin>() {
                    @Override
                    public LogAdminLogin getObject() throws Exception {
                        return adminLoginMapper.selectByPrimaryKey(uk);
                    }

                    @Override
                    public List<LogAdminLogin> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(LogAdminLogin record) {
        int result = adminLoginMapper.updateByPrimaryKeySelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(LogAdminLogin record) {
        int result = adminLoginMapper.updateByPrimaryKeyWithBLOBs(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKey(LogAdminLogin record) {
        int result = adminLoginMapper.updateByPrimaryKey(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public List<LogAdminLogin> listByCondition(AdminLoginLogCondition adminLoginLogCondition) {
        List<LogAdminLogin> result = getCacheUtil().getList(getNamespace(),
                "listByCondition." + adminLoginLogCondition.toString(),
                new CacheCallback<LogAdminLogin>() {
                    @Override
                    public LogAdminLogin getObject() throws Exception {
                        return null;
                    }

                    @Override
                    public List<LogAdminLogin> getObjectList() throws Exception {
                        return adminLoginMapper.listByCondition(adminLoginLogCondition);
                    }
                });
        return result;
    }

    @Override
    public long countByCondition(AdminLoginLogCondition adminLoginLogCondition) {
        Object result = getCacheUtil().get(getNamespace(),
                "countByCondition." + adminLoginLogCondition.toString(),
                new CacheCallback<Long>() {
                    @Override
                    public Long getObject() throws Exception {
                        return adminLoginMapper.countByCondition(adminLoginLogCondition);
                    }

                    @Override
                    public List<Long> getObjectList() throws Exception {
                        return null;
                    }
                });
        return ObjectUtils.toLong(result);
    }
}