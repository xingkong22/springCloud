package com.cloud.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@EnableEurekaClient //允许注册到注册中心
@SpringBootApplication
//@RestController
//@EnableAuthorizationServer // 告诉springcloud该服务是oauth2鉴权服务
//@EnableResourceServer  // 告诉springcloud该服务是oauth2也是资源服务，为了保护该类内的/user接口
public class Oauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }


//    @RequestMapping(value = {"/user"}, produces = "application/json")
//    public Map<String, Object> user(OAuth2Authentication user) {
//
//        Map<String, Object> userInfo = new HashMap<>();
//        userInfo.put("user", user.getUserAuthentication().getPrincipal());
//        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
//        return userInfo;
//    }

}
