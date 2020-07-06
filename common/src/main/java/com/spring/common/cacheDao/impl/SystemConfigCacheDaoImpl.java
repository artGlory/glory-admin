package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheCallback;
import com.spring.common.cacheDao.SystemConfigCacheDao;
import com.spring.common.mybatis.SystemConfigMapper;
import com.spring.common.po.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:34
 */
@Component
public class SystemConfigCacheDaoImpl extends AbstractCacheDao implements SystemConfigCacheDao {
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public int deleteByPrimaryKey(String uk) {
        int result = systemConfigMapper.deleteByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insert(SystemConfig record) {
        int result = systemConfigMapper.insert(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insertSelective(SystemConfig record) {
        int result = systemConfigMapper.insertSelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public SystemConfig selectByPrimaryKey(String uk) {
        SystemConfig result = getCacheUtil().get(getNamespace(), "selectByPrimaryKey." + uk,
                new CacheCallback<SystemConfig>() {
                    @Override
                    public SystemConfig getObject() throws Exception {
                        return systemConfigMapper.selectByPrimaryKey(uk);
                    }

                    @Override
                    public List<SystemConfig> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public SystemConfig selectByUK(Integer systemArea, String systemGroup, String systemKey) {
        SystemConfig result = getCacheUtil().get(getNamespace()
                , "selectByUK.systemArea." + systemArea + ".systemGroup." + systemGroup + ".systemKey." + systemKey,
                new CacheCallback<SystemConfig>() {
                    @Override
                    public SystemConfig getObject() throws Exception {
                        return systemConfigMapper.selectByUK(systemArea, systemGroup, systemKey);
                    }

                    @Override
                    public List<SystemConfig> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(SystemConfig record) {
        int result = systemConfigMapper.updateByPrimaryKeySelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKey(SystemConfig record) {
        int result = systemConfigMapper.updateByPrimaryKey(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }
}