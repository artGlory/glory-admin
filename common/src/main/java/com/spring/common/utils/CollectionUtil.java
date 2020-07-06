package com.spring.common.utils;

import java.util.HashSet;
import java.util.List;

public class CollectionUtil {
    /**
     * 通过HashSet踢除重复元素
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
