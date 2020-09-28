package com.cloud.oauth2userserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
