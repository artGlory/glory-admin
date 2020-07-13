package com.spring.webadmin.module.adminUser.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class PageAdminUserVO implements Serializable {
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
     * 角色
     *
     * @mbg.generated
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "角色")
    private String roleUk;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 必须使用google两步登陆
     */
    @ApiModelProperty(value = "必须使用google两步登陆")
    private Integer googleLogin;

    /**
     * 禁止登陆
     */
    @ApiModelProperty(value = "禁止登陆")
    private Integer forbidLogin;
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