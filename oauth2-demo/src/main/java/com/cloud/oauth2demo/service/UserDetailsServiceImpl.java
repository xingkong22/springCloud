package com.cloud.oauth2demo.service;

import com.cloud.oauth2demo.bean.UserPojo;
import com.cloud.oauth2demo.bean.UserVoDetail;
import com.cloud.oauth2demo.feign.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo userByUsername = userService.getUserByUsername(username);

        if(null != userByUsername){
            UserVoDetail userVoDetail = new UserVoDetail();
            userVoDetail.setUserId(userByUsername.getUserId());
            userVoDetail.setUsername(userByUsername.getUsername());
            userVoDetail.setPassword(userByUsername.getPassword());
            userVoDetail.setRoles(userByUsername.getRoles());
            return userVoDetail;
        }else{
            throw new UsernameNotFoundException("用户不存在！！！");
        }
    }
}
