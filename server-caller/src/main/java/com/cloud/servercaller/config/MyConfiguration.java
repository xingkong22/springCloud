package com.cloud.servercaller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springboot访问本地路径获取图片
 * 通过映射可以直接获取图片
 */
@Configuration
public class MyConfiguration extends WebMvcConfigurerAdapter {

    @Value("${showImg}")
    private String imgPath;

    @Value("${showImgPrefix}")
    private String showImgPrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //其中upload表示访问的前缀。"file:F:/MyQRCode/"是文件真实的存储路径
        registry.addResourceHandler(showImgPrefix).addResourceLocations(imgPath);
    }
}
