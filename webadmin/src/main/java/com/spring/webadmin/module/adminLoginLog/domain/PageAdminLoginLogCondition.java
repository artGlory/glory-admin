package com.spring.webadmin.module.adminLoginLog.domain;

import com.spring.common.domain.condition.help.BaseCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PageAdminLoginLogCondition extends BaseCondition {

    @ApiModelProperty(value = "username")
    private String userName;

    @ApiModelProperty(value = "用户ip")
    private String remoteIp;

}
