package com.spring.common.utils.validateCode.kaptcha;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateCodeDTO {
    /**
     * 验证码-文本
     */
    private String text;
    /**
     * 验证码-base64图片
     */
    private String base64Image;
}
