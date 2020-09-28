package com.cloud.serverprovider.controller;

import com.cloud.serverprovider.pojo.UserPojo;
import com.cloud.serverprovider.service.UserPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserPojoService userPojoService;

    @GetMapping("/getUserByUsername/{username}")
    public UserPojo getUserByUsername(@PathVariable String username){
        UserPojo sysUser = userPojoService.selectOne(username);
        if(sysUser==null){
            return null;
        }
        return sysUser;
    }

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello，" + this.getClass().getName();
    }
}
