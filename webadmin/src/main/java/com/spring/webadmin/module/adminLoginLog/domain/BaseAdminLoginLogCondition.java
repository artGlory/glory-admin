package com.spring.common.domain.condition;

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
public class BaseAdminLoginLogCondition extends BaseCondition {

    @ApiModelProperty(value = "username")
    private String userName;
    @ApiModelProperty(value = "主键排序类型")
    @Pattern(regexp = "asc|desc", message = "时间排序类型（asc|desc）")
    private String createTimeSortType = "desc";
    @ApiModelProperty(value = "用户ip")
    private String remoteIp;
    @ApiModelProperty(value = "角色列表")
    List<String> roleUkList = new ArrayList<>();

}
