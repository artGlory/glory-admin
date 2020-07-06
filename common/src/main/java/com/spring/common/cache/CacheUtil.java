package com.spring.common.cache;

import com.spring.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CacheUtil {

    @Value("${spring.redis.cache.default.expire.seconds:3600}")
    private Long defaultExpireSeconds;
    @Value("${spring.redis.cache.enable:false}")
    private Boolean isRedisEnable;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private String getKey(final String namespace, final String key) {
        return namespace + "." + key;
    }

    /**
     * 获取一个缓存对象;缓存默认过期时间为5分钟
     *
     * @param <T>
     * @param namespace
     * @param key       缓存存储key
     * @return 缓存对象
     */
    public <T> T get(final String namespace, final String key, CacheCallback<T> callback) {

        return get(namespace, key, defaultExpireSeconds, callback);
    }

    /**
     * 获取一个缓存对象
     *
     * @param <T>
     * @param namespace
     * @param key       缓存存储key
     * @return 缓存对象
     */
    public <T> T get(final String namespace, final String key, final long expireSeconds, CacheCallback<T> callback) {
        T t = null;
        try {
            if (!isRedisEnable)
                return callback.getObject();//缓存未开启

            Object objectTemp = redisTemplate.opsForValue().get(getKey(namespace, key));
            if (objectTemp != null) {
                t = (T) objectTemp;
            } else {
                if (callback == null) return null;
                else {
                    t = callback.getObject();
                    if (t != null) {
                        redisTemplate.opsForValue().set(getKey(namespace, key), t, expireSeconds, TimeUnit.SECONDS);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                t = callback.getObject();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t;
    }

    public <T> List<T> getList(final String namespace, final String key, CacheCallback<T> callback) {
        return getList(namespace, key, defaultExpireSeconds, callback);
    }

    /**
     * 获取缓存数据；
     *
     * @param namespace
     * @param key
     * @param expireSeconds
     * @param callback
     * @param <T>
     * @return
     */
    public <T> List<T> getList(final String namespace, final String key, long expireSeconds, CacheCallback<T> callback) {
        List<T> t = null;
        try {
            if (!isRedisEnable)
                return callback.getObjectList();

            Object objectTemp = redisTemplate.opsForValue().get(getKey(namespace, key));
            if (objectTemp == null && callback == null) {
                return null;
            }
            if (objectTemp == null) {
                t = callback.getObjectList();
                if (t != null) {
                    redisTemplate.opsForValue().set(getKey(namespace, key), t, expireSeconds, TimeUnit.SECONDS);
                }
            } else {
                t = (List<T>) objectTemp;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                t = (List<T>) callback.getObject();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t;
    }

    /**
     * 移除一个缓存对
     *
     * @param namespace
     * @param key       缓存对象key
     * @return 移除状态
     */
    public boolean remove(final String namespace, final String... key) {
        if (!isRedisEnable) return true;

        boolean flag = false;
        List<String> keyList = new ArrayList<>();
        for (String k : key) {
            keyList.add(getKey(namespace, k));
        }
        try {
            redisTemplate.delete(keyList);
            flag = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 清除命名空间下的缓存数据
     *
     * @param namespace
     * @return 清除状态
     */
    public boolean cleanSpace(final String namespace) {
        if (!isRedisEnable) return true;

        boolean flag = false;
        try {
            Set<String> keys = redisTemplate.keys(getKey(namespace, "*"));
            redisTemplate.delete(keys);
            flag = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return flag;
    }
}
