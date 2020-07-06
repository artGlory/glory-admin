package com.spring.common.cache;

import java.util.List;

public interface CacheCallback<T> {

    T getObject() throws Exception;

    List<T> getObjectList() throws Exception;

}