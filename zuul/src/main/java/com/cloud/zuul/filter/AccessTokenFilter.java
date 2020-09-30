package com.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @Author: Administrator
 * @Description: TODO Filter的四个方法，FilterType，filterOrder，shouldFilter，run
 *              filterType代表过滤类型
 *                  PRE: 该类型的filters在Request routing到源web-service之前执行。用来实现Authentication、选择源服务地址等
 *                  ROUTING：该类型的filters用于把Request routing到源web-service，源web-service是实现业务逻辑的服务。这里使用HttpClient请求web-service
 *                  POST：该类型的filters在ROUTING返回Response后执行。用来实现对Response结果进行修改，收集统计数据以及把Response传输会客户端
 *                  ERROR：上面三个过程中任何一个出现错误都交由ERROR类型的filters进行处理
 *                  主要关注 pre、post和error。分别代表前置过滤，后置过滤和异常过滤
 *              filterOrder代表过滤器顺序
 *              shouldFilter代表这个过滤器是否生效
 *              Run方法是主要的处理逻辑的地方，我们做权限控制、日志等都是在这里
 * @Date: 2020/9/30 0030 下午 4:50
 * @Version: 1.0
 */
@Component
public class AccessTokenFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessTokenFilter.class);

    private static final String PARAM_TOKEN = "token";

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        return true;
    }

    /*
      * @Method run
      * @Description TODO 本拦截器任务
      *         1、拦截所有访问。
      *         2、获取Token。
      *         3、把token加到headers中Authorization : Bearer $('token_info')，以便OAUTH2服务端验证。
      * @Params
      * @Author Administrator
      * @Return java.lang.Object
      * @Date 2020/9/30 0030 下午 5:12
      */
    @Override
    public Object run() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();

        log.info("send {} request to {}", req.getMethod(), req.getRequestURL().toString());

        try {
            /*
               1、获取Token。
             */
            Cookie cookie = WebUtils.getCookie(req, PARAM_TOKEN);
            if(cookie!=null) {
                String token_back = cookie.getValue();
                if (token_back != null && !token_back.isEmpty()) {
                    /*
                        2、把token加到headers中Authorization : Bearer $('token_info')，以便OAUTH2服务端验证。
                     */
                    System.out.println("token:" + token_back);

                    ctx.addZuulRequestHeader("Authorization", "bearer" + token_back);
                }
            }
        } catch (Exception e) {
            System.out.println("token异常！");
            ctx.setResponseBody("token error!");
            ctx.setSendZuulResponse(false);
        }
        //这里return的值没有意义，zuul框架没有使用该返回值
        return null;
    }

    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 0;
    }
}
