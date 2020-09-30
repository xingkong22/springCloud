package com.cloud.servercaller.feignclient;

import com.cloud.servercaller.feignclient.fallback.SayHelloCallerImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "server-provider", fallback = SayHelloCallerImpl.class)
public interface SayHelloCaller {

    @GetMapping("/provider/hello")
    String sayHello();
}
