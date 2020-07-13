package com.spring.webadmin.module.adminSystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class GoogleDownloadDTO {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "下载地址")
    private String url;
}
