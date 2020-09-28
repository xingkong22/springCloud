package com.cloud.oauth2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pageIndex")
public class PageController {

    @GetMapping(path = "/index")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ModelAndView login(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.out.println("pageIndex-header:" + authorization);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        return modelAndView;
    }

    @GetMapping(path = "/aaa")
    @ResponseBody
    public String aaa(){
        return "1111111111";
    }
}
