package com.spring.webadmin.module.adminSystem.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformInfoVO implements Serializable {

    @ApiModelProperty(value = "平台名称")
    private String platformName;
    @ApiModelProperty(value = "系统版权")
    private String platformCopyright;
    @ApiModelProperty(value = "系统logo")
    @URL
    private String platformLogo;
}
