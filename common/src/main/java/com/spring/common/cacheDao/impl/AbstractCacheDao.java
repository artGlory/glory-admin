package com.spring.common.cacheDao.impl;

import com.spring.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCacheDao {
    @Autowired
    protected CacheUtil cacheUtil;

    protected String getNamespace() {
        return this.getClass().getName();
    }

    protected CacheUtil getCacheUtil() {
        return this.cacheUtil;
    }
}
