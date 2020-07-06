package com.spring.common.po;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2020-06-27 00:20:32
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRole implements Serializable {
    /**
     * uk
     *
     * @mbg.generated
     */
    private String uk;

    /**
     * 角色名称
     *
     * @mbg.generated
     */
    private String roleName;

    /**
     * 角色描述
     *
     * @mbg.generated
     */
    private String roleDesc;

    /**
     * 父uk
     *
     * @mbg.generated
     */
    private String parentUk;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 最后一次更新时间
     *
     * @mbg.generated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}