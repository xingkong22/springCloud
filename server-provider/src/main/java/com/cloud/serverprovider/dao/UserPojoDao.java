package com.cloud.serverprovider.dao;

import com.cloud.serverprovider.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserPojoDao {
    UserPojo selectOne(String username);
}
