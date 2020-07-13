package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 1864977105631367755L;
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "用户名：4到16位（字母，数字，下划线）")
    private String username;
    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,16}", message = "密码：6到16位（字母，数字，下划线）")
    private String password;
    @ApiModelProperty(value = "google两步登陆")
    private String googleCode;
}
