package com.cloud.servercaller.controller;

import com.cloud.servercaller.feignclient.SayHelloCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/person")
public class Person {

    @Autowired(required = false)
    private SayHelloCaller caller;

    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello() {
        return "我是server_caller,Person() : " + caller.sayHello();
    }

    @RequestMapping("/sayHello2")
    @ResponseBody
    public String page2(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.out.println("authorization:" + authorization);
        return "sayHello2";
    }
}
