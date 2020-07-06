package com.spring.common.constant;

/**
 * 配置表
 */
public enum SystemConfigEnum {


    system_info_name(1, "system_info", "platformName"),
    system_info_logo(1, "system_info", "logo"),
    system_info_copyright(1, "system_info", "copyright");


    /**
     * 配置所有区域  1：admin
     */
    private int configArea;
    /**
     * 配置组
     */
    private String configGroup;
    /**
     * 配置key
     */
    private String configKey;

    SystemConfigEnum(int configArea, String configGroup, String configKey) {
        this.configArea = configArea;
        this.configGroup = configGroup;
        this.configKey = configKey;
    }

    public int getConfigArea() {
        return configArea;
    }

    public String getConfigGroup() {
        return configGroup;
    }

    public String getConfigKey() {
        return configKey;
    }
}
