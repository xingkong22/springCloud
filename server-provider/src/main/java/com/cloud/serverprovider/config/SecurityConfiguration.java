//package com.cloud.serverprovider.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
//     */
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        AuthenticationManager manager = super.authenticationManagerBean();
////        return manager;
////    }
//
//    /**
//     * @Date 14:35 2019/7/11
//     * @Param [不用加密]
//     * @return org.springframework.security.crypto.password.PasswordEncoder
//     **/
////    @Bean
////    public static PasswordEncoder passwordEncoder(){
////        return NoOpPasswordEncoder.getInstance();
////    }
//
//    /**
//     * 配置请求认证的规则
//     * @param httpSecurity
//     * @throws Exception
//     */
//    public void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .authorizeRequests()
//                //资源不需要验证,外界可直接访问
////                .antMatchers("/css/**", "/js/**", "/common/**", "/demo/**", "/page/**").permitAll()
//                .antMatchers("/**").authenticated()
//                .anyRequest().authenticated()
//                // /user/** 需要验证,并且需要用户的角色是 USER
////                .antMatchers("/user/**").hasRole("USER")
//                // /blogs/** 需要验证,并且需要用户的角色是 ADMIN
////                .antMatchers("/blogs/**").hasRole("ADMIN")
////                .and()
//                //表单登录地址是/login 登录失败的地址是/login-error
////                .formLogin().loginPage("/provider/login").failureUrl("/login-error.html")
//                .and()
//                //异常处理会重定向到/401
//                .exceptionHandling().accessDeniedPage("/401.html");
//        //注销成功，重定向到首页
//        httpSecurity.logout().logoutSuccessUrl("/login.html");
//
//        //关闭默认的csrf认证
//        httpSecurity.csrf().disable();
//    }
//
//    public void configure(WebSecurity web) throws Exception {
//        //资源不需要验证,外界可直接访问
//        web.ignoring().antMatchers("/user/**");
//    }
//
//}
