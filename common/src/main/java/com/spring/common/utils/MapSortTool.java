package com.spring.common.utils;

import java.util.*;

/**
 * Integer类型的key排序；Integer类型的value排序
 */
public class MapSortTool {

    /**
     * Integer KEY的比较器
     */
    private static class MapIntegerKeyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    /**
     * 使用 Map按key进行排序;
     *
     * @param map
     * @return
     */
    public static Map sortByKey(Map map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map sortMap = new TreeMap(new MapIntegerKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }


    /**
     * Integer  VALUE的比较器
     */
    private static class MapIntegerValueComparator implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
            return me1.getValue() - (me2.getValue());
        }
    }

    /**
     * 使用 Map按value进行排序;由低到高
     *
     * @param map
     * @return
     */
    public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new MapIntegerValueComparator());

        Iterator<Map.Entry<String, Integer>> iter = list.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }



}
