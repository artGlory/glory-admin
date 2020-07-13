package com.spring.webadmin.module.adminSystem.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class UpdateSystemConfigDTO implements Serializable {
    /**
     * 唯一标识
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "")
    private String uk;
//
//    /**
//     * 配置所属区域
//     *
//     * @mbg.generated
//     */
//    @ApiModelProperty(value = "配置所属区域")
//    private Integer configArea;
//
//    /**
//     * 组
//     *
//     * @mbg.generated
//     */
//    @ApiModelProperty(value = "组")
//    private String configGroup;

//    /**
//     * key
//     *
//     * @mbg.generated
//     */
//    @ApiModelProperty(value = "key")
//    private String configKey;

    /**
     * 值
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "值")
    private String configValue;

    /**
     * 描述
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "描述")
    private String configDesc;



    private static final long serialVersionUID = 1L;
}