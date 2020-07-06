package com.spring.common.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 排序
 */
public class SortUtil {
    /**
     * 按照值的大小升序
     *
     * @param list
     * @return
     */
    public static List sortListAES(List list) {
        Collections.sort(list);
        return list;
    }

    public static void main(String[] args) {
        List<Double> list = new LinkedList<>();
        list.add(8.1D);
        list.add(0.1D);
        list.add(0D);
        list.add(-8.1D);
        Collections.sort(list);
        list.forEach(dd -> {
            System.out.println(dd);
        });
    }
}

