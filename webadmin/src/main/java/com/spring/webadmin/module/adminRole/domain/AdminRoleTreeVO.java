package com.spring.webadmin.module.adminRole.domain;

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
 * Created by Mybatis Generator on 2020-06-27 00:20:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AdminRoleTreeVO implements Serializable {
    /**
     * uk
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "唯一标识")
    private String uk;

    /**
     * 角色名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色描述
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    /**
     * 父uk
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "父标识")
    private String parentUk;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后一次更新时间
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "最后时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @ApiModelProperty(value = "子树结构")
    List<AdminRoleTreeVO> children = new ArrayList<AdminRoleTreeVO>();
}