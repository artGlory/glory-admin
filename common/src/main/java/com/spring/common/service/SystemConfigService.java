package com.spring.common.service;

import com.spring.common.po.SystemConfig;

public interface SystemConfigService {

    /**
     * 更改系统配置-值和描述
     *
     * @param systemConfig
     * @return
     */
    int updateSystemConfigValueAndDesc(SystemConfig systemConfig);
}
