package com.spring.webadmin.module.adminOperateLog.domain;

import com.spring.common.domain.condition.help.BaseCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PageAdminOperateLogCondition extends BaseCondition {

    @ApiModelProperty(value = "operator")
    private String operator;
    @ApiModelProperty(value = "操作类别")
    private String operateName;
    @ApiModelProperty(value = "操作结果")
//    @
    private Integer result;
}
