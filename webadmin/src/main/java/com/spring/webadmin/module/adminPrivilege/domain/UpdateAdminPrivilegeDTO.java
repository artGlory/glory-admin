package com.spring.webadmin.module.adminPrivilege.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Mybatis Generator on 2020-06-27 00:20:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UpdateAdminPrivilegeDTO implements Serializable {

    /**
     * 角色唯一标识
     */
    @ApiModelProperty(value = "角色唯一标识")
    @NotNull(message = "角色标识不能为空")
    private String roleUk;
    @ApiModelProperty(value = "权限")
    private String[] permissions;

}