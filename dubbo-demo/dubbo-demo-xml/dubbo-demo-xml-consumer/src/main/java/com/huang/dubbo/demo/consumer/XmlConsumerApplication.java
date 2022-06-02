package com.huang.dubbo.demo.consumer;

import com.huang.dubbo.demo.DemoService;
import com.huang.dubbo.demo.GreetingService;
import com.huang.dubbo.demo.RestDemoService;
import com.huang.dubbo.demo.TripleService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

public class XmlConsumerApplication {

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 100;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final int QUEUE_CAPACITY = 200;

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();
        DemoService demoService = context.getBean("demoService", DemoService.class);
        GreetingService greetingService = context.getBean("greetingService", GreetingService.class);
        RestDemoService restDemoService = context.getBean("restDemoService", RestDemoService.class);
        TripleService tripleService = context.getBean("tripleService", TripleService.class);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,KEEP_ALIVE_TIME
                ,TimeUnit.SECONDS,new ArrayBlockingQueue<>(QUEUE_CAPACITY), Executors.defaultThreadFactory());

        poolExecutor.execute(() -> {
            while (true) {
                try {
                    String greetings = greetingService.hello();
                    System.out.println(greetings + " from separated thread.");
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        });

        poolExecutor.execute(()->{
            while (true) {
                try {
                    String restResult = restDemoService.sayHello("rest");
                    System.out.println(restResult + " from separated thread.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        });

        poolExecutor.execute(()->{
            while (true) {
                try {
                    String restResult = tripleService.hello();
                    System.out.println(restResult + " from separated thread.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        });

        while (true) {
            try {
                CompletableFuture<String> hello = demoService.sayHelloAsync("world");
                System.out.println("result: " + hello.get());

                String greetings = greetingService.hello();
                System.out.println("result: " + greetings);
            } catch (Exception e) {
//                e.printStackTrace();
            }
            Thread.sleep(5000);
        }
    }
}
