package com.cloud.oauth2.controller;

import com.cloud.oauth2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PreAuthorize("hasRole('USER')")
 * 这个验证权限的注解，写在哪个层都可以
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/getAll")
//    @PreAuthorize("hasRole('USER')")
    public String getAll(){
//        return "getAll()";
        return orderService.getAll();
    }


    @RequestMapping("/addOrder")
//    @PreAuthorize("hasRole('ADMIN')")
    public String addOrder(){
//        return "addOrder()";
        return orderService.addOrder();
    }
}
