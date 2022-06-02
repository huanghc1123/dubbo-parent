package com.huang.dubbo.demo.consumer.comp;

import org.apache.dubbo.config.annotation.DubboReference;
import com.huang.dubbo.demo.DemoService;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("demoServiceComponent")
public class DemoServiceComponent implements DemoService {
    @DubboReference
    private DemoService demoService;

    @Override
    public String sayHello(String name) {
        return demoService.sayHello(name);
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return null;
    }
}
