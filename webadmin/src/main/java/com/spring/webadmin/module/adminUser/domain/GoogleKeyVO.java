package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class GoogleKeyVO {
    @ApiModelProperty(value = "GoogleKey")
    private String googleKey;
    @ApiModelProperty(value = "GoogleKey二维码")
    private String googleKeyPicture;
}
