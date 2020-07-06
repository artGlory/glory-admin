package com.spring.common.po;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2020-06-27 22:58:04
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRolePrivilege implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private String uk;

    /**
     * 角色主键
     *
     * @mbg.generated
     */
    private String roleUk;

    /**
     * 权限主键
     *
     * @mbg.generated
     */
    private String privilegeUk;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}