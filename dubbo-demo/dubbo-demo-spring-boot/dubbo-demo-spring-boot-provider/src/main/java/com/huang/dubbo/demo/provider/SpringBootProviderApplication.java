package com.huang.dubbo.demo.provider;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableDubbo(scanBasePackages = {"com.huang.dubbo.demo.provider"})
public class SpringBootProviderApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringBootProviderApplication.class,args);
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
