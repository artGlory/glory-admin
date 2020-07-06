package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UpdateAdminUserDTO implements Serializable {
    /**
     * 唯一标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "唯一标识")
    private String uk;


    /**
     * 角色
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色标识")
    private String roleUk;

    private static final long serialVersionUID = 1L;
}