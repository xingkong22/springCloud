package com.cloud.servercaller.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign调用出现权限问题 feign.FeignException$Unauthorized: [401] during [GET] to[....]
 * 这种情况为没有认证就请求了资源服务器的资源，解决办法为使用Feign的时候把请求的认证信息传递过去，或者资源服务那边开放一些接口。
 * 不过开放接口的话情况有点多，所以就选择把token携带过去了。feign提供了一个名为RequestInterceptor得拦截器，可以在请求的时候指定请求头
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader("Authorization");
        System.out.println("FeignConfig-远程调用服务，添加请求头认证：" + authorization);
        requestTemplate.header("Authorization", authorization);
    }
}
