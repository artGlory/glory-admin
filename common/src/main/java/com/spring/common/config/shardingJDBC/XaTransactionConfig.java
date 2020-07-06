package com.spring.common.config.shardingJDBC;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = JtaAutoConfiguration.class)
@ComponentScan(basePackages = {"com.spring.common"})
public class XaTransactionConfig {
}