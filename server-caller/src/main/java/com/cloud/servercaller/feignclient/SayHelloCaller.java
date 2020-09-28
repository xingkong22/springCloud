package com.cloud.servercaller.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "server-provider")
public interface SayHelloCaller {

    @RequestMapping("/provider/hello")
    public String sayHello();
}
