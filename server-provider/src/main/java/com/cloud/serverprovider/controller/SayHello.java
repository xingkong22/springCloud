package com.cloud.serverprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/provider")
public class SayHello {

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello" + this.getClass().getName();
    }
}
