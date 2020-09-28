package com.cloud.servercaller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/page")
public class Page {

    @RequestMapping("/index")
    public String page() {
        return "index";
    }

    @RequestMapping("/index2")
    @ResponseBody
    public String page2(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.out.println("authorization:" + authorization);
        return "index2";
    }
}
