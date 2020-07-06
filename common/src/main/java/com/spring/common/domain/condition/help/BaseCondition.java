package com.spring.common.domain.condition.help;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 分页查询
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public abstract class BaseCondition {

    @ApiModelProperty(value = "页码。首页页码为1", example = "1")
    @Min(value = 1, message = "页码不能小于1")
    protected Integer page;
    @ApiModelProperty(value = "页面大小", example = "10")
    @Max(value = 100, message = "单页数据不能大于100")
    @Min(value = 1, message = "单页数量不能小于1")
    protected Integer size;
    protected Integer startIndex;
    protected Integer endIndex;
    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date startTime;
    @ApiModelProperty(value = "终止时间", example = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date endTime;

    /**
     * 重新计算startIndex, endIndex
     */
    public boolean reCalcIndex() {
        if (this.page != null && this.size != null) {
            if (this.page == 0)
                this.page = 1;
            this.startIndex = (page - 1) * size;
            this.endIndex = size;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .replace(",", ".");
    }

}
