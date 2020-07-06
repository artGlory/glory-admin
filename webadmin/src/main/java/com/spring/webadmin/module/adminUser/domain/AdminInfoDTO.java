package com.spring.webadmin.module.adminUser.domain;

import com.spring.common.po.AdminRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class AdminInfoDTO implements Serializable {

    @ApiModelProperty(value = "管理员角色")
    private String[] permissions;
    @ApiModelProperty(value = "管理员名称")
    private String name;
    @ApiModelProperty(value = "管理员头像")
    private String avatar;
    @ApiModelProperty(value = "系统名称")
    private String systemName;
    @ApiModelProperty(value = "系统logo")
    private String systemLogo;
    @ApiModelProperty(value = "系统版权")
    private String systemCopyright;
    @ApiModelProperty(value = "角色")
    private AdminRole adminRole;
    @ApiModelProperty(value = "用户标识")
    private String adminUk;
}
