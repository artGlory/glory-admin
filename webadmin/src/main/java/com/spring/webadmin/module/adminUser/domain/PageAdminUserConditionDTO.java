package com.spring.webadmin.module.adminUser.domain;

import com.spring.common.domain.condition.help.BaseCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PageAdminUserConditionDTO extends BaseCondition {

    @ApiModelProperty(value = "username")
    private String username;
    @ApiModelProperty(value = "主键排序类型", example = "desc")
    @Pattern(regexp = "asc|desc", message = "时间排序类型（asc|desc）")
    private String createTimeSortType = "desc";
    @ApiModelProperty(value = "标识")
    private String uk;
    @ApiModelProperty(value = "roleName")
    private String roleName;
}
