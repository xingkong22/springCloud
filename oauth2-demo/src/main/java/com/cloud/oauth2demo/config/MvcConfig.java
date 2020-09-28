package com.cloud.oauth2demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * thymeleaf 给根路径配置视图，结果出现不能找到templates下的html资源
 * 因为 thymeleaf 已经默认配置好了mvc的视图解析器，所以需要转换一下
 * 返回的页面，前面加上/page/，以便在 SecurityConfiguration 里进行资源路径拦截配置
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/page/");
    }
}
