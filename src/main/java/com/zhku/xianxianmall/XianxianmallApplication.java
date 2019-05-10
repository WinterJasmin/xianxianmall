package com.zhku.xianxianmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.zhku.xianxianmall.filter")
@MapperScan("com.zhku.xianxianmall.dao")
public class XianxianmallApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(XianxianmallApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(XianxianmallApplication.class, args);
    }

}
