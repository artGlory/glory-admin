package com.spring.webapi;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebapiApplicationTests {

    @Test
    public void contextLoads() {
    }

    //    测试配置文件加密
    @Autowired
    StringEncryptor encryptor;

    @Test
    public void encryptorTest() {
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("root");
        System.out.println("【" + name + "】");
        System.out.println("【" + password + "】");
        System.out.println(encryptor.decrypt(name));
        System.out.println(encryptor.decrypt(password));

        Assert.assertTrue(name.length() > 0);
        Assert.assertTrue(password.length() > 0);

    }


}
