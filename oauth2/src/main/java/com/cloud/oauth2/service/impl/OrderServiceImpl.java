package com.cloud.oauth2.service.impl;

import com.cloud.oauth2.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String addOrder() {
        return "addOrder()";
    }

    @Override
    public String getAll() {
        return "getAll()";
    }
}
