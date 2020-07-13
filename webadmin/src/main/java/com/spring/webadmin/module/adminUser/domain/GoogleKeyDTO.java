package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class GoogleKeyDTO {
    @ApiModelProperty(value = "GoogleKey")
    @NotBlank(message = "GoogleKey不能为空")
    private String googleKey;
    @ApiModelProperty(value = "GoogleCode,动态密码")
    @NotBlank(message = "动态密码不能为空")
    @Length(min = 6, max = 6, message = "动态密码长度必须位6位")
    private String googleCode;
}
