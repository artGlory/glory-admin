package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UpdateSelfPasswordVO implements Serializable {

    /**
     * 用户密码
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户密码，新")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,16}", message = "密码：6到16位（字母，数字，下划线）")
    private String passwordOld;
    /**
     * 用户密码
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户密码，新")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,16}", message = "密码：6到16位（字母，数字，下划线）")
    private String passwordNew;


    private static final long serialVersionUID = 1L;
}