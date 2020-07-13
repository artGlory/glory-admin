package com.spring.common.service.impl;

import com.spring.common.mybatis.SystemConfigMapper;
import com.spring.common.po.SystemConfig;
import com.spring.common.service.SystemConfigService;
import com.spring.common.utils.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Transactional
    @Override
    public int updateSystemConfigValueAndDesc(SystemConfig systemConfig) {
        SystemConfig targetSystemConfig = systemConfigMapper.selectByPrimaryKey(systemConfig.getUk());
        if (StringUtils.isEmpty(systemConfig.getConfigValue()) == false)
            targetSystemConfig.setConfigValue(systemConfig.getConfigValue());
        if ((StringUtils.isEmpty(systemConfig.getConfigDesc())) == false)
            targetSystemConfig.setConfigDesc(systemConfig.getConfigDesc());
        targetSystemConfig.setUpdateTime(new Moment().toDate());
        return systemConfigMapper.updateByPrimaryKeySelective(targetSystemConfig);
    }

}
