package com.spring.common.po;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2020-06-27 11:34:21
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser implements Serializable {
    /**
     * 唯一标识
     *
     * @mbg.generated
     */
    private String uk;

    /**
     * 用户名
     *
     * @mbg.generated
     */
    private String username;

    /**
     * 用户密码
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 角色
     *
     * @mbg.generated
     */
    private String roleUk;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * 最后一次信息更改时间
     *
     * @mbg.generated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}