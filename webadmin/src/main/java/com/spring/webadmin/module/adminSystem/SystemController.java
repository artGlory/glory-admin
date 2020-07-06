package com.spring.webadmin.module.adminSystem;

import com.spring.common.cacheDao.SystemConfigCacheDao;
import com.spring.common.po.SystemConfig;
import com.spring.webadmin.anno.NoNeedLogin;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminSystem.domain.PlatformInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.spring.common.constant.SystemConfigEnum.system_info_name;

@Slf4j
@Api(tags = "系统配置")
@RestController
@RequestMapping(Route.path + Route.System.path)
public class SystemController {
    @Autowired
    private SystemConfigCacheDao systemConfigCacheDao;

    @ApiOperation(value = "平台名称", notes = "获取平台名称")
    @GetMapping(Route.System.getPlatformInfo)
    @NoNeedLogin
    public ResponseDate<PlatformInfoVO> getPlatformInfo() {
        SystemConfig systemConfig = systemConfigCacheDao.selectByUK(system_info_name.getConfigArea(),
                system_info_name.getConfigGroup(), system_info_name.getConfigKey());

        PlatformInfoVO platformInfoVO = PlatformInfoVO.builder()
                .platformName(systemConfig.getConfigValue()).build();
        return ResponseDate.<PlatformInfoVO>builder()
                .success(true)
                .data(platformInfoVO)
                .message("")
                .build();
    }
}
