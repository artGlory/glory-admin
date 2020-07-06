package com.spring.webadmin.module.adminLoginLog.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mybatis Generator on 2020-07-02 01:13:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BaseLogAdminLogin implements Serializable {
    /**
     * 标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "标识")
    private String uk;

    /**
     * 用户标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "标识")
    private String userUk;
    /**
     * 用户名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 用户角色标识
     */
    @ApiModelProperty(value = "用户角色标识")
    private String userRoleUk;

    /**
     * 登陆ip
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户ip")
    private String remoteIp;

    /**
     * 登陆地址
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户地址")
    private String remoteAddress;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 附加信息
     *
     * @mbg.generated
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}