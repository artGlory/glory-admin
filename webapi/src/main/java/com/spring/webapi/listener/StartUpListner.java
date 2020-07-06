package com.spring.webapi.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartUpListner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.err.println("<<<<<<<<<<<<<<<<<<<程序启动成功>>>>>>>>>>>>>>>>>>>");
    }
}