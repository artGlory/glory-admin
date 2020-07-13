package com.spring.common.po;

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
public class AdminUser implements Serializable {
    /**
     * 必须使用Google两步登陆
     */
    public static final int google_login_master = 1;
    /**
     * 非必须使用Google两步登陆
     */
    public static final int google_login_not_master = 0;

    /**
     * 禁止登陆
     */
    public static final int forbid_login = 1;
    /**
     * 不禁止登陆
     */
    public static final int not_forbid_login = 0;
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
     * 当前token
     */
    private String nowToken;

    /**
     * google_key
     */
    private String googleKey;

    /**
     * 必须使用google两步登陆
     */
    private Integer googleLogin;

    /**
     * 禁止登陆
     */
    private Integer forbidLogin;

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