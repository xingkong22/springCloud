package com.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;


/**
 * @类名 ThrowExceptionFilter
 * @描述 全局异常处理 testing
 * @作者 hanlei
 * @日期 2018-8-21 13:39
 **/
@Component
public class ThrowExceptionFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(ThrowExceptionFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * @名称 filterOrder
     * @描述 排在其他两个拦截器之后
     * @参数 []
     * @返回值 int
     * @作者 hanlei
     * @时间 2018-8-21 13:42
     */
    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return false;
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
        }
        return null;
    }

    private void doSomething() {
        throw new RuntimeException("Exist some errors...");
    }


}