package com.cloud.zuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Administrator
 * @Description: TODO Zuul的Fallback回退机制
 *              默认情况下，经过Zuul的请求都会使用Hystrix进行包裹，所以Zuul本身就具有断路器的功能
 *              当我们的zuul进行路由分发时，如果后端服务没有启动，或者调用超时，这时候我们希望Zuul提供一种降级功能，而不是将异常暴露出来
 * @Date: 2020/9/30 0030 下午 4:50
 * @Version: 1.0
 */
@Component
public class ServiceFallbackProvider implements FallbackProvider {

    public static final String DEFAULT_ERR_MSG = "系统繁忙,请稍后再试";

    /*
      * @Method getRoute
      * @Description TODO 返回值表示需要针对此微服务做回退处理（该名称一定要是注册进入 eureka 微服务中的那个 serviceId 名称）
      * @Params
      * @Author Administrator
      * @Return java.lang.String
      * @Date 2020/9/30 0030 下午 5:11
      */
    @Override
    public String getRoute() {
        //为所有路由提供默认回退
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable throwable) {
        return new ClientHttpResponse() {

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(DEFAULT_ERR_MSG.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
