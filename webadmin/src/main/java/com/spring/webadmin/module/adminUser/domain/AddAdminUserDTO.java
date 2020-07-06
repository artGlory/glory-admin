package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AddAdminUserDTO implements Serializable {
    /**
     * 唯一标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "用户名：4到16位（字母，数字，下划线）")

    private String username;


    /**
     * 角色
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色标识")
    @NotBlank(message = "角色不能为空")
    private String roleUk;

    private static final long serialVersionUID = 1L;
}