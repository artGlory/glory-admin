package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BaseAdminUser implements Serializable {
    /**
     * 唯一标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "唯一标识")
    private String uk;

    /**
     * 用户名
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户密码
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
     * 角色
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色标识")
    private Long roleUk;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后一次信息更改时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "修改时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}