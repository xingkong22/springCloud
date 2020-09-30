package com.cloud.serverprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/provider")
public class SayHello {

    /*  
      * @Method sayHello
      * @Description TODO 
      * @Params 
      * @Author Administrator
      * @Return java.lang.String
      * @Date 2020/9/30 0030 下午 4:50
      */
    @RequestMapping("/hello")
    public String sayHello() {
        return "hello" + this.getClass().getName();
    }

    /*
      * @Method getBaidu
      * @Description TODO FeignClient与RestTemplate的区别比较简单研究
      *     区别：
      *         RestTemplate是用来制作的同步呼叫。使用RestTemplate时，URL参数是以编程方式构造的，数据被发送到其他服务
      *         Feign是Spring Cloud Netflix库，使用Feign时，我们在客户端编写声明式REST服务接口，并使用这些接口来编写客户端程序。开发人员不用担心这个接口的实现。
      *             这将在运行时由Spring动态配置。通过这种声明性的方法，开发人员不需要深入了解由HTTP提供的HTTP级别API的细节的RestTemplate
      *     总结：
      *         RestTemplate还需要写上服务器IP这些信息等等，而FeignClient则不用
      *         FeignClient简化了请求的编写，且通过动态负载进行选择要使用哪个服务进行消费，而这一切都由Spring动态配置实现，我们不用关心这些，只管使用方法即可
      * @Params
      * @Author Administrator
      * @Return java.lang.String
      * @Date 2020/9/30 0030 下午 5:07
      */
    @RequestMapping("/getBaidu")
    @ResponseBody
    public String getBaidu(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://www.baidu.com", String.class);
    }
}
