package com.cloud.serverprovider.service;

import com.cloud.serverprovider.pojo.UserPojo;

public interface UserPojoService {
    UserPojo selectOne(String username);
}
