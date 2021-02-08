package com.cloud.zuul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ProjectName: oauth2-user-server
 * @Package: com.cloud.zuul.controller
 * @ClassName: Login
 * @Author: Administrator
 * @Description: TODO
 * @Date: 2020/10/26 0026 下午 2:05
 * @Version: 1.0
 */
@Controller
public class Login {

    @GetMapping(path = "/login")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }
}
