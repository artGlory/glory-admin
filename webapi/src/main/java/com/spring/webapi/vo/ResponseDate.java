package com.spring.webapi.vo;

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
public class ResponseDate<T> implements Serializable {
    private static final long serialVersionUID = 4177313172779343309L;
    @ApiModelProperty(value = "uuid")
    private String responseUUID;
    @ApiModelProperty(value = "是否成功")
    protected Boolean success;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private T data;

}
