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
public class AdminSystemConfigCondition extends BaseCondition {

    @ApiModelProperty(value = "配置域")
    private String configArea;
    @ApiModelProperty(value = "配置组")
    private String configGroup;
    @ApiModelProperty(value = "配置key")
    private String configKey;
    @ApiModelProperty(value = "主键排序类型")
    @Pattern(regexp = "asc|desc", message = "时间排序类型（asc|desc）")
    private String createTimeSortType = "desc";

}
