package com.cloud.oauth2userserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient //允许注册到注册中心
@SpringBootApplication
public class Oauth2UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2UserServerApplication.class, args);
    }

}
