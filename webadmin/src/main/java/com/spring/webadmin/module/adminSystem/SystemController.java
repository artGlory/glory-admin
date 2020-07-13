package com.spring.webadmin.module.adminSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.common.domain.PageResultVO;
import com.spring.common.domain.condition.AdminSystemConfigCondition;
import com.spring.common.mybatis.SystemConfigMapper;
import com.spring.common.po.SystemConfig;
import com.spring.common.service.SystemConfigService;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.webadmin.anno.NoNeedLogin;
import com.spring.webadmin.anno.OperateLog;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminSystem.domain.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.spring.common.constant.SystemConfigEnum.*;

@Slf4j
@Api(tags = "系统配置")
@RestController
@RequestMapping(Route.path + Route.AdminSystem.path)
public class SystemController {
    @Autowired
    private SystemConfigMapper systemConfigMapper;
    @Autowired
    private SystemConfigService systemConfigService;

    @ApiOperation(value = "平台名称", notes = "获取平台名称")
    @GetMapping(Route.AdminSystem.getPlatformInfo)
    @NoNeedLogin
    public ResponseDate<PlatformInfoVO> getPlatformInfo() {
        SystemConfig systemConfig = systemConfigMapper.selectByUK(system_info_name.getConfigArea(),
                system_info_name.getConfigGroup(), system_info_name.getConfigKey());

        PlatformInfoVO platformInfoVO = PlatformInfoVO.builder()
                .platformName(systemConfig.getConfigValue()).build();
        return ResponseDate.<PlatformInfoVO>builder()
                .success(true)
                .data(platformInfoVO)
                .build();
    }

    @ApiOperation(value = "系统配置", notes = "获取系统配置")
    @PostMapping(Route.AdminSystem.getPageConfig)
    public ResponseDate<PageResultVO<List<PageSystemConfigVO>>> getPageConfig(HttpServletRequest httpServletRequest
            , @Valid @RequestBody PageAdminSystemConfigCondition pageAdminSystemConfigCondition) {
        AdminSystemConfigCondition adminSystemConfigCondition =
                SmartBeanUtil.copy(pageAdminSystemConfigCondition, AdminSystemConfigCondition.class);
        long count = systemConfigMapper.countByCondition(adminSystemConfigCondition);
        List<PageSystemConfigVO> pageSystemConfigVOList = new ArrayList<>();
        if (count > 0) {
            List<SystemConfig> systemConfigList = systemConfigMapper.listByCondition(adminSystemConfigCondition);
            pageSystemConfigVOList = SmartBeanUtil.copyList(systemConfigList, PageSystemConfigVO.class);
        }
        PageResultVO<List<PageSystemConfigVO>> pageResultVO = PageResultVO.<List<PageSystemConfigVO>>builder()
                .pageSize(pageAdminSystemConfigCondition.getSize())
                .pageNum(pageAdminSystemConfigCondition.getPage())
                .allNum(count)
                .data(pageSystemConfigVOList)
                .build();
        return ResponseDate
                .<PageResultVO<List<PageSystemConfigVO>>>builder()
                .success(true)
                .data(pageResultVO)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "更改系统配置", notes = "更改系统配置")
    @PostMapping(Route.AdminSystem.updateSystemConfig)
    public ResponseDate updateSystemConfig(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateSystemConfigDTO updateSystemConfigDTO) {
        SystemConfig systemConfig = systemConfigMapper.selectByPrimaryKey(updateSystemConfigDTO.getUk());
        if (systemConfig == null) throw new IllegalArgumentException("配置不存在");
        SmartBeanUtil.copyProperties(updateSystemConfigDTO, systemConfig);
        int result = systemConfigService.updateSystemConfigValueAndDesc(systemConfig);
        return ResponseDate
                .builder()
                .success(result == 1)
                .build();
    }

    @ApiModelProperty(value = "获取GoogleAuthentication的下载信息", notes = "获取GoogleAuthentication的下载信息")
    @GetMapping(Route.AdminSystem.getGoogleAuthenticationDownload)
    public ResponseDate<List<GoogleDownloadVO>> getGoogleAuthenticationDownload() {
        List<GoogleDownloadVO> googleDownloadVOList = new ArrayList<>();
        SystemConfig android = systemConfigMapper.selectByUK(google_authentication_download_android.getConfigArea(),
                google_authentication_download_android.getConfigGroup(), google_authentication_download_android.getConfigKey());
        if (android != null) {
            String value = android.getConfigValue();
            try {
                GoogleDownloadDTO googleDownloadDTO = new ObjectMapper().readValue(value, GoogleDownloadDTO.class);
                GoogleDownloadVO googleDownloadVO = SmartBeanUtil.copy(googleDownloadDTO, GoogleDownloadVO.class);
                googleDownloadVOList.add(googleDownloadVO);
            } catch (IOException e) {
            }
        }

        SystemConfig ios = systemConfigMapper.selectByUK(google_authentication_download_ios.getConfigArea(),
                google_authentication_download_ios.getConfigGroup(), google_authentication_download_ios.getConfigKey());
        if (ios != null) {
            String value = ios.getConfigValue();
            try {
                GoogleDownloadDTO googleDownloadDTO = new ObjectMapper().readValue(value, GoogleDownloadDTO.class);
                GoogleDownloadVO googleDownloadVO = SmartBeanUtil.copy(googleDownloadDTO, GoogleDownloadVO.class);
                googleDownloadVOList.add(googleDownloadVO);
            } catch (IOException e) {
            }
        }

        return ResponseDate
                .<List<GoogleDownloadVO>>builder()
                .success(true)
                .data(googleDownloadVOList)
                .build();
    }

}
