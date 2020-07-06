package com.spring.webadmin.module.adminUser.domain;

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
public class BaseAdminUserCondition extends BaseCondition {


    @ApiModelProperty(value = "username")
    private String username;
    @ApiModelProperty(value = "主键排序类型",example = "desc")
    @Pattern(regexp = "asc|desc", message = "主键排序类型（asc|desc）")
    private String ukSortType="desc";
    @ApiModelProperty(value = "标识")
    private Long uk;
    @ApiModelProperty(value = "角色列表")
    List<Long> roleUkList = new ArrayList<>();

}
