package com.spring.webapi.config;

import com.spring.webapi.filter.logFilter.LogFilter;
import com.spring.webapi.filter.xssFilter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public Filter xssFilter() {
        return new XssFilter();
    }

    @Bean
    public Filter uuidFilter() {
        return new LogFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean_2() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(xssFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(20);//order的数值越小 则优先级越高
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean_3() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(uuidFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(30);
        return filterRegistrationBean;
    }


}

