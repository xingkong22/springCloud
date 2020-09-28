package com.cloud.oauth2demo.feign;

import com.cloud.oauth2demo.bean.UserPojo;
import com.cloud.oauth2demo.feign.fallback.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "server-provider", fallback = UserServiceImpl.class)
public interface UserService {

    @GetMapping("/user/getUserByUsername/{username}")
    UserPojo getUserByUsername(@PathVariable("username") String username);
}
