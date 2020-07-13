package com.spring.webadmin.module.adminUser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OperateTimelineVO {
    @ApiModelProperty(value = "节点内容")
    private String content;
    @ApiModelProperty(value = "节点时间")
    private Date logTime;
    @ApiModelProperty(value = "节点尺⼨")
    private String size;
    @ApiModelProperty(value = "节点颜色")
    private String color;
    @ApiModelProperty(value = "节点icoin")
    private String icon;
}
