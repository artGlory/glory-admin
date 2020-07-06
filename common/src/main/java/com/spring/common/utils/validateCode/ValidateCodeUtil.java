package com.spring.common.utils.validateCode;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.spring.common.utils.validateCode.kaptcha.ValidateCodeDTO;
import com.spring.common.utils.validateCode.kaptcha.KaptchaNoise;
import com.spring.common.utils.validateCode.kaptcha.KaptchaWordRenderer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Slf4j
public class ValidateCodeUtil {
    private static DefaultKaptcha defaultKaptcha;

    static {
        if (defaultKaptcha == null) {
            Properties properties = new Properties();
            properties.setProperty("kaptcha.border", "no");
            properties.setProperty("kaptcha.border.color", "34,114,200");
            properties.setProperty("kaptcha.image.width", "125");
            properties.setProperty("kaptcha.image.height", "45");
            properties.setProperty("kaptcha.textproducer.char.string", "ABCDEFG23456789");
            properties.setProperty("kaptcha.textproducer.char.length", "5");
            properties.setProperty("kaptcha.textproducer.font.names", "Arial,Arial Narrow,Serif,Helvetica,Tahoma,Times New Roman,Verdana");
            properties.setProperty("kaptcha.textproducer.font.size", "38");

            properties.setProperty("kaptcha.background.clear.from", "white");
            properties.setProperty("kaptcha.background.clear.to", "white");

            properties.setProperty("kaptcha.word.impl", KaptchaWordRenderer.class.getName());
            properties.setProperty("kaptcha.noise.impl", KaptchaNoise.class.getName());

            Config config = new Config(properties);
            defaultKaptcha = new DefaultKaptcha();
            defaultKaptcha.setConfig(config);
        }
    }

    /**
     * 生成base64d的验证码
     *
     * @return
     */
    public static ValidateCodeDTO generateValidateCode() {
        String kaptchaText = defaultKaptcha.createText();

        String base64Code = "";

        BufferedImage image = defaultKaptcha.createImage(kaptchaText);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            base64Code = Base64.encodeBase64String(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("verificationCode exception .{}", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error("verificationCode outputStream close exception .{}", e);
                }
            }
        }
        String base64Image = "data:image/png;base64," + base64Code;

        return ValidateCodeDTO.builder().text(kaptchaText).base64Image(base64Image).build();
    }

    public static void main(String[] args) {
        System.err.println(generateValidateCode().getBase64Image());
    }


}
