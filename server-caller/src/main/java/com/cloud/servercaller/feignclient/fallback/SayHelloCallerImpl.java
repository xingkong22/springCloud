package com.cloud.servercaller.feignclient.fallback;

import com.cloud.servercaller.feignclient.SayHelloCaller;
import org.springframework.stereotype.Service;

@Service
public class SayHelloCallerImpl implements SayHelloCaller {
    @Override
    public String sayHello() {
        return "SayHelloCaller失败了";
    }
}
