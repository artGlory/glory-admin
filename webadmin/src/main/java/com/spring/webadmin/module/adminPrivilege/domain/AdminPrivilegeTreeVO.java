package com.spring.webadmin.module.adminPrivilege.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 00:20:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AdminPrivilegeTreeVO implements Serializable {
    /**
     * 权限类型-目录权限
     */
    public static final int privilege_type_catalogue = 1;
    /**
     * 权限类型-页面权限
     */
    public static final int privilege_type_page = 2;
    /**
     * 权限类型- 按钮权限
     */
    public static final int privilege_type_button = 3;

    /**
     * 权限唯一标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限唯一标识")
    private String uk;

    /**
     * 权限类型 1：目录权限；2：页面权限；3：按钮权限
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限类型 1：目录权限；2：页面权限；3：按钮权限")
    private Integer privilegeType;

    /**
     * 权限名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限名称")
    private String privilegeName;
    /**
     * 权限路径
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限路径")
    private String privilegePath;

    /**
     * 排序
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "排序")
    private String sort;

    /**
     * 父标签标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "父标签标识")
    private String parentUk;

    /**
     * 插入时间
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 最后更改时间
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * 子树
     */
    private List<AdminPrivilegeTreeVO> children = new ArrayList<>();
}