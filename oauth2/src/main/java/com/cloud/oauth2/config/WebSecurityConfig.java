package com.cloud.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
//开始方法级别的保护
//@PreAuthorize 进入方法前进行权限验证
//@PostAuthorize 方法执行后再进行权限验证
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    @Bean
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        InMemoryUserDetailsManager manage = new InMemoryUserDetailsManager();
//        manage.createUser(User.withUsername("forezp").password("123456").roles("USER").build());
//        manage.createUser(User.withUsername("aaa").password("123456").roles("ADMIN").build());
//        return manage;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("{noop}password1")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("{noop}password2")
//                .roles("USER", "ADMIN");
//    }


    /**
     * 配置了读取用户认证信息的方式
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication()
//                .withUser("forezp").password("123456").roles("USER");


        //不加.passwordEncoder(new MyPasswordEncoder())
        //就不是以明文的方式进行匹配，会报错
        //这样，页面提交时候，密码以明文的方式进行匹配
        auth.inMemoryAuthentication()
                .passwordEncoder(new MyPasswordEncoder())
                .withUser("aaa").password("123456").roles("ADMIN", "USER");
        auth.inMemoryAuthentication()
                .passwordEncoder(new MyPasswordEncoder())
                .withUser("forezp").password("123456").roles("USER");


    }


    /**
     * 配置请求认证的规则
     * @param httpSecurity
     * @throws Exception
     */
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                //资源不需要验证,外界可直接访问
                .antMatchers("/css/**", "/index").permitAll()
                // /user/** 需要验证,并且需要用户的角色是 USER
                .antMatchers("/user/**").hasRole("USER")
                // /blogs/** 需要验证,并且需要用户的角色是 ADMIN
                .antMatchers("/blogs/**").hasRole("ADMIN")
                .and()
                //表单登录地址是/login 登录失败的地址是/login-error
                .formLogin().loginPage("/login").failureUrl("/login-error")
                .and()
                //异常处理会重定向到/401
                .exceptionHandling().accessDeniedPage("/401");
        //注销成功，重定向到首页
        httpSecurity.logout().logoutSuccessUrl("/");

        //关闭默认的csrf认证
        httpSecurity.csrf().disable();
    }
}
