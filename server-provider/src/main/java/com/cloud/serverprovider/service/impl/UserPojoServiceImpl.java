package com.cloud.serverprovider.service.impl;

import com.cloud.serverprovider.dao.UserPojoDao;
import com.cloud.serverprovider.pojo.UserPojo;
import com.cloud.serverprovider.service.UserPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPojoServiceImpl implements UserPojoService {

    @Autowired
    private UserPojoDao userPojoDao;
    @Override
    public UserPojo selectOne(String username) {
        return userPojoDao.selectOne(username);
    }
}
