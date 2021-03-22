package com.spring.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * json转换工具
 */
public class JacksonUtils {
    private static ObjectMapper objectMapper;//线程安全对象

    /**
     * 获取ObjectMapper；单例模式；双重验证
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            synchronized (JacksonUtils.class) {
                if (objectMapper == null)
                    objectMapper = new ObjectMapper();
            }
        }
        return objectMapper;
    }

    /**
     * 字符串转对象;包含（普通java对象,Map）
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonStrToObject(String jsonStr, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转字符串；包含（普通java对象,Map）
     *
     * @param object
     * @return
     */
    public static String objectToJsonStr(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return getObjectMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 转换为list
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonStrToList(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.trim().equals(""))
            return null;
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> lst = null;
        try {
            lst = (List<T>) getObjectMapper().readValue(jsonStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst;
    }

    public static void main(String[] args) throws Exception {
//        JavaType javaType = getCollectionType(ArrayList.class, YourBean.class);
//        List<YourBean> lst = (List<YourBean>) mapper.readValue(jsonString, javaType);


//        String str="{\"status\":1,\"info\":{\"id\":\"371899510\",\"game_id\":\"60\",\"cycle_value\":\"201911120678\",\"game_result\":null,\"start_time\":\"2019-11-12 11:17:00\",\"end_time\":\"2019-11-12 11:18:00\",\"cycle_status\":\"1\",\"open_time\":\"0000-00-00 00:00:00\",\"last_edit\":\"2019-11-12 11:15:01\",\"cycle_analysis\":\"\",\"is_analysis\":\"0\",\"cycle_index\":\"60_201911120678\",\"cycle_count\":\"14841\",\"count_down\":51},\"data\":1}";
//        JsonNode jsonNode=getObjectMapper().readTree(str);
//        System.out.println(jsonNode.get("status"));
//        System.out.println(jsonNode.get("info").get("cycle_value"));

    }

}
