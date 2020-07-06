package com.spring.webadmin.module.adminOperateLog.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mybatis Generator on 2020-07-01 17:33:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PageLogOperateVO implements Serializable {
    /**
     * 操作结果-异常
     */
    public static final int result_exception = 0;
    /**
     * 操作结果-正常
     */
    public static final int result_normal = 1;
    /**
     * 唯一标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "标识")
    private String uk;

    /**
     * 操作人
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "操作人")
    private String operator;
    /**
     * 操作人主键
     */
    @ApiModelProperty(value = "操作人主键")
    private String operatorUk;
    /**
     * 操作人角色标识
     */
    @ApiModelProperty(value = "操作人角色标识")
    private String operatorRoleUk;
    /**
     * 操作人角色名称
     */
    @ApiModelProperty(value = "操作人角色")
    private String operatorRoleName;

    /**
     * 操作名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "操作名称")
    private String operateName;

    /**
     * 操作结果；1正常，0异常
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "操作结果；1正常，0异常")
    private Integer result;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 操作路径
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "操作路径")
    private String operatePath;

    /**
     * 参数
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "参数")
    private String params;

    /**
     * 失败原因
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "失败原因")
    private String failReason;

    private static final long serialVersionUID = 1L;
}