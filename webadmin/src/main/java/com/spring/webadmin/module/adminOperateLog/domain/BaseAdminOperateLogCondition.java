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
public class BaseAdminOperateLogCondition extends BaseCondition {

    @ApiModelProperty(value = "operator")
    private String operator;
    @ApiModelProperty(value = "主键排序类型")
    @Pattern(regexp = "asc|desc", message = "时间排序类型（asc|desc）")
    private String createTimeSortType = "desc";
    @ApiModelProperty(value = "操作类别")
    private String operateName;
    @ApiModelProperty(value = "角色列表")
    List<String> roleUkList = new ArrayList<>();

}
