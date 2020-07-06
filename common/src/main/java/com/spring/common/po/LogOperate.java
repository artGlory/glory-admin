package com.spring.common.po;

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
public class LogOperate implements Serializable {
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
    private String uk;

    /**
     * 操作人
     *
     * @mbg.generated
     */
    private String operator;
    /**
     * 操作人主键
     */
    private String operatorUk;
    /**
     * 操作人角色标识
     */
    private String operatorRoleUk;

    /**
     * 操作名称
     *
     * @mbg.generated
     */
    private String operateName;

    /**
     * 操作结果；1正常，0异常
     *
     * @mbg.generated
     */
    private Integer result;

    private Date createTime;

    private Date updateTime;

    /**
     * 操作路径
     *
     * @mbg.generated
     */
    private String operatePath;

    /**
     * 参数
     *
     * @mbg.generated
     */
    private String params;

    /**
     * 失败原因
     *
     * @mbg.generated
     */
    private String failReason;

    private static final long serialVersionUID = 1L;
}