package com.spring.common.constant;

public enum OperateTypeEnum {
    EXAMPLE("操作类型", "操作类型描述"),
    LOGIN("api-login", "api登陆");
    private String value;
    private String desc;

    OperateTypeEnum(String value, String desc) {
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
