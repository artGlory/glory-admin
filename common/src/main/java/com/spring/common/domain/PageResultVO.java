package com.spring.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PageResultVO<T> implements Serializable {

    private static final long serialVersionUID = -3609957407944480616L;
    @ApiModelProperty(value = "当前页码")
    private long pageNum;
    @ApiModelProperty(value = "页码数据量")
    private long pageSize;
    @ApiModelProperty(value = "数据总量")
    private long allNum;
    @ApiModelProperty(value = "数据")
    private T data;

}
