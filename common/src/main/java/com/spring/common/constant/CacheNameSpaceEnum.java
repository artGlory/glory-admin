package com.spring.common.constant;

/**
 * 缓存key
 */
public enum CacheNameSpaceEnum {
    EXAMPLE("namaSpace", "命名空间"),
    admin_validate_code("admin_validate_code", "后台验证码");


    /**
     * 值
     */
    private String value;
    /**
     * 描述
     */
    private String desc;

    CacheNameSpaceEnum() {
    }

    CacheNameSpaceEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
