package com.cloud.oauth2demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class DemoTest {
    public static void main(String[] args) {
        String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password");
        System.out.println("password:" + password);

        String password1 = new BCryptPasswordEncoder().encode("password");
        System.out.println("password1:" + password1);
    }
}
