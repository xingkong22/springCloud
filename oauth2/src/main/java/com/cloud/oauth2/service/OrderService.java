package com.cloud.oauth2.service;


import org.springframework.security.access.prepost.PreAuthorize;

public interface OrderService {

    @PreAuthorize("hasRole('ADMIN')")
    String addOrder();

    @PreAuthorize("hasRole('USER')")
    String getAll();
}
