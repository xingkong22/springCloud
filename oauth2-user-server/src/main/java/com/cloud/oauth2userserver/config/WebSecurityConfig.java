package com.cloud.oauth2userserver.config;

import com.cloud.oauth2userserver.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserServiceDetail userServiceDetail;


    //验证管理的bean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /*
      * @Method configureGlobal
      * @Description TODO 配置了读取用户认证信息的方式
      * @Params  * @param auth :
      * @Author Administrator
      * @Return void
      * @Date 2020/9/30 0030 下午 4:59
      */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        //不加.passwordEncoder(new MyPasswordEncoder())
        //就不是以明文的方式进行匹配，会报错
        //这样，页面提交时候，密码以明文的方式进行匹配
        auth.inMemoryAuthentication()
                .passwordEncoder(new MyPasswordEncoder())
                .withUser("aaa").password("123456").roles("ADMIN", "USER");
        auth.inMemoryAuthentication()
                .passwordEncoder(new MyPasswordEncoder())
                .withUser("forezp").password("123456").roles("USER");


//        auth.userDetailsService(userServiceDetail).passwordEncoder(new BCryptPasswordEncoder());


    }


    /*
      * @Method configure
      * @Description TODO 配置请求认证的规则
      * @Params  * @param httpSecurity :
      * @Author Administrator
      * @Return void
      * @Date 2020/9/30 0030 下午 5:00
      */
    public void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .authorizeRequests()
//                //资源不需要验证,外界可直接访问
//                .antMatchers("/css/**", "/index").permitAll()
//                // /user/** 需要验证,并且需要用户的角色是 USER
//                .antMatchers("/user/**").hasRole("USER")
//                // /blogs/** 需要验证,并且需要用户的角色是 ADMIN
//                .antMatchers("/blogs/**").hasRole("ADMIN")
//                .and()
//                //表单登录地址是/login 登录失败的地址是/login-error
//                .formLogin().loginPage("/login").failureUrl("/login-error")
//                .and()
//                //异常处理会重定向到/401
//                .exceptionHandling().accessDeniedPage("/401");
//        //注销成功，重定向到首页
//        httpSecurity.logout().logoutSuccessUrl("/");
//
//        //关闭默认的csrf认证
//        httpSecurity.csrf().disable();


        //都需要安全验证
        httpSecurity
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
