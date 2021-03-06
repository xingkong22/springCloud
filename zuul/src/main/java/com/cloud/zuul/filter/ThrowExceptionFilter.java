package com.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;


/**
 * @Author: Administrator
 * @Description: TODO 全局异常处理 testing
 * @Date: 2020/9/30 0030 下午 4:50
 * @Version: 1.0
 */
@Component
public class ThrowExceptionFilter extends ZuulFilter {

    private static final String ERROR_STATUS_CODE_KEY = "error.status_code";

    private static Logger log = LoggerFactory.getLogger(ThrowExceptionFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    /*
      * @Method filterOrder
      * @Description TODO return 3, 排在其他两个拦截器之后
      * @Params
      * @Author Administrator
      * @Return int
      * @Date 2020/9/30 0030 下午 5:14
      */
    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.containsKey(ERROR_STATUS_CODE_KEY);
    }

    @Override
    public Object run() {
        log.info("This is a pre filter, it will throw a RuntimeException");
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            doSomething();
        } catch (Exception e) {
            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.exception", e);
            ctx.set("error.message", e.getMessage());
        }
        return null;
    }

    private void doSomething() {
        throw new RuntimeException("Exist some errors...");
    }


}