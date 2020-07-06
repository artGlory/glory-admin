package com.spring.common.po;

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
public class LogAdminLogin implements Serializable {
    /**
     * 标识
     *
     * @mbg.generated
     */
    private String uk;

    /**
     * 用户标识
     *
     * @mbg.generated
     */
    private String userUk;
    /**
     * 用户名称
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * 用户角色标识
     */
    private String userRoleUk;

    /**
     * 登陆ip
     *
     * @mbg.generated
     */
    private String remoteIp;

    /**
     * 登陆地址
     *
     * @mbg.generated
     */
    private String remoteAddress;

    private Date createTime;

    private Date updateTime;

    /**
     * 附加信息
     *
     * @mbg.generated
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}