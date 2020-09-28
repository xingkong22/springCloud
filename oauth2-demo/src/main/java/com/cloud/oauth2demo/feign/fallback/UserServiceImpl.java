package com.cloud.oauth2demo.feign.fallback;

import com.cloud.oauth2demo.bean.UserPojo;
import com.cloud.oauth2demo.feign.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserPojo getUserByUsername(String username) {
        return null;
    }
}
