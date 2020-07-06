package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheCallback;
import com.spring.common.cacheDao.LogOperateCacheDao;
import com.spring.common.domain.condition.AdminOperateLogCondition;
import com.spring.common.mybatis.LogOperateMapper;
import com.spring.common.po.LogOperate;
import com.spring.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-07-01 17:33:03
 */
@Component
@Slf4j
public class LogOperateCacheDaoImpl extends AbstractCacheDao implements LogOperateCacheDao {

    @Autowired
    private LogOperateMapper logOperateMapper;

    @Override
    public int deleteByPrimaryKey(String uk) {
        int result = logOperateMapper.deleteByPrimaryKey(uk);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insert(LogOperate record) {
        int result = logOperateMapper.insert(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int insertSelective(LogOperate record) {
        int result = logOperateMapper.insertSelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public LogOperate selectByPrimaryKey(String uk) {
        LogOperate result = getCacheUtil().get(getNamespace(), "selectByPrimaryKey.uk." + uk,
                new CacheCallback<LogOperate>() {
                    @Override
                    public LogOperate getObject() throws Exception {
                        return logOperateMapper.selectByPrimaryKey(uk);
                    }

                    @Override
                    public List<LogOperate> getObjectList() throws Exception {
                        return null;
                    }
                });
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(LogOperate record) {
        int result = logOperateMapper.updateByPrimaryKeySelective(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(LogOperate record) {
        int result = logOperateMapper.updateByPrimaryKeyWithBLOBs(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public int updateByPrimaryKey(LogOperate record) {
        int result = logOperateMapper.updateByPrimaryKey(record);
        getCacheUtil().cleanSpace(getNamespace());
        return result;
    }

    @Override
    public List<LogOperate> listByCondition(AdminOperateLogCondition adminOperateLogCondition) {
        List<LogOperate> result = getCacheUtil().getList(getNamespace(), "listByCondition." + adminOperateLogCondition.toString(),
                new CacheCallback<LogOperate>() {
                    @Override
                    public LogOperate getObject() throws Exception {
                        return null;
                    }

                    @Override
                    public List<LogOperate> getObjectList() throws Exception {
                        return logOperateMapper.listByCondition(adminOperateLogCondition);
                    }
                });
        return result;
    }

    @Override
    public long countByCondition(AdminOperateLogCondition adminOperateLogCondition) {
        Object result = getCacheUtil().get(getNamespace(), "countByCondition." + adminOperateLogCondition.toString(),
                new CacheCallback<Long>() {
                    @Override
                    public Long getObject() throws Exception {
                        return logOperateMapper.countByCondition(adminOperateLogCondition);
                    }

                    @Override
                    public List<Long> getObjectList() throws Exception {
                        return null;
                    }
                });
        return ObjectUtils.toLong(result);
    }
}