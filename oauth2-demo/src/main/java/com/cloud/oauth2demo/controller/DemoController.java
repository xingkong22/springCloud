package com.cloud.oauth2demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.oauth2demo.util.Oauth2Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Description: TODO
 *  1.获取token
 *  http://localhost:10008/oauth/token?grant_type=password&username=hello&password=hello&client_id=client_id&client_secret=client_secret&scope=scope
 *
 *  2.获取到access_token 后。添加到请求头中，访问接口
 *  http://localhost:10008/hello?access_token=as5d45asdasd4as551asd98as7d6xc2v15
 *
 *  3.用refresh_token 换取新的token
 *  http://localhost:10008/oauth/token?grant_type=refresh_token&username=hello&password=hello&client_id=client_id&client_secret=client_secret&scope=scope&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoidmNsb3VkIiwiYXVkIjpbInZjbG91ZCJdLCJ1c2VyX25hbWUiOiJoZWxsbyIsInNjb3BlIjpbInNjb3BlIl0sImF0aSI6IjVmNDQ3Njk2LTY3YTgtNDIyZi04ZmNmLWIwYWYxZmRmOTE5ZSIsImV4cCI6MTYwMTc5OTg2NCwidXNlcklkIjoxLCJqdGkiOiI2ZTM0ZDcxYi1kNWRlLTRhNDItOTM3Yy1iMjBlODViMmI1YmYiLCJjbGllbnRfaWQiOiJjbGllbnRfaWQifQ.VAfdSvQV6SjVR07IgDP6T2DhiRoNvdu3RgNfRviFJe4
 * @Date: 2020/9/30 0030 下午 4:50
 * @Version: 1.0
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping(path = "/login")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }


    @GetMapping(path = "/hello")
    @ResponseBody
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String hello(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.out.println("demo-header:" + authorization);
        return "hello demo";
    }


    @PostMapping("/doLogin")
    @ResponseBody
    public Map<String, Object> doLogin(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String returnCode = "";
        try {
            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            params.put("grant_type", "password");
            params.put("client_id", "client_id");
            params.put("client_secret", "client_secret");
            params.put("scope", "scope");//应用程序 作用域 !!!!

            String backTokenUrl = "http://localhost:10008/oauth/token";
            returnCode = Oauth2Post.AuthHttpPost(backTokenUrl, params);

            if(null != returnCode && !"".equals(returnCode)){
                JSONObject jsonObject = JSONObject.parseObject(returnCode);
                String access_token = jsonObject.get("access_token").toString();
                System.out.println("access_token:" + access_token);

//                request.getSession().setAttribute("token", access_token);

                map.put("access_token", access_token);
                map.put("code", 1);
                map.put("msg", "登陆成功");
            }else{
                map.put("access_token", "");
                map.put("code", 0);
                map.put("msg", "登陆失败");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /*
      * @Method removeToken
      * @Description TODO 退出登录,并清除token
      * @Params  * @param access_token :
      * @Author Administrator
      * @Return java.lang.Boolean
      * @Date 2020/9/30 0030 下午 4:57
      */
    @PostMapping("/removeToken")
    @ResponseBody
    public Boolean removeToken(@CookieValue(value = "token")String token){
        return consumerTokenServices.revokeToken(token);
    }

    @GetMapping("/login-error")
    public String loginerror(){
        return "login-error";
    }

    @GetMapping("/401")
    public String falsePage(){
        return "401";
    }
}
