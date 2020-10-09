package com.cloud.servercaller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * Spring Security OAuth2 架构上分为Authorization Server认证服务器和Resource Server资源服务器。我们可以为每一个Resource Server（一个微服务实例）设置一个resourceid。
 * Authorization Server给client第三方客户端授权的时候，可以设置这个client可以访问哪一些Resource Server资源服务，如果没设置，就是对所有的Resource Server都有访问权限
 *
 * 在Spring Security的FilterChain中，OAuth2AuthenticationProcessingFilter在FilterSecurityInterceptor的前面，所以会先验证client有没有此resource的权限，
 * 只有在有此resource的权限的情况下，才会再去做进一步的进行其他验证的判断
 *
 * 作者：字母哥课堂
 * 链接：https://www.jianshu.com/p/73ee7436fe7b
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "vcloud";

    /*
      * @Method configure
      * @Description TODO spring security拦截器配置
      * @Params  * @param http :
      * @Author Administrator
      * @Return void
      * @Date 2020/10/9 0009 下午 2:18
      */
    @Override
    public void configure(HttpSecurity http) throws Exception {


        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/common/**", "/images/**", "/page/index2").permitAll()
//            .anyRequest().hasAuthority("ROLE_ADMIN")//必须具有 后台角色ID
            .anyRequest().authenticated()
            .and()
            .httpBasic();

//        /*
//         * 解决BUG
//         * index:1 Refused to display 'http://...:8080/web/pages/welcome.html'
//         * in a frame because it set 'X-Frame-Options' to 'deny'.
//         */
//        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID);
        //...... 还可以有有其他的配置
    }

}
