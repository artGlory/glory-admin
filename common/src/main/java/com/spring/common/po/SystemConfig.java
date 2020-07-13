package com.spring.common.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfig implements Serializable {

    /**
     * 配置所有区域  1：admin
     */
    public static final int config_area_admin = 1;
    /**
     * 唯一标识
     *
     * @mbg.generated
     */
    private String uk;

    /**
     * 配置所属区域
     *
     * @mbg.generated
     */
    private Integer configArea;

    /**
     * 组
     *
     * @mbg.generated
     */
    private String configGroup;

    /**
     * key
     *
     * @mbg.generated
     */
    private String configKey;

    /**
     * 值
     *
     * @mbg.generated
     */
    private String configValue;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String configDesc;

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