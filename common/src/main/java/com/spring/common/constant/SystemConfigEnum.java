package com.spring.common.constant;

import static com.spring.common.po.SystemConfig.config_area_admin;

/**
 * 配置表
 */
public enum SystemConfigEnum {


    system_info_name(config_area_admin, "system_info", "platformName"),
    system_info_logo(config_area_admin, "system_info", "logo"),
    system_info_copyright(config_area_admin, "system_info", "copyright"),
    google_authentication_download_android(config_area_admin, "google_authentication_download", "android"),
    google_authentication_download_ios(config_area_admin, "google_authentication_download", "ios");


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
