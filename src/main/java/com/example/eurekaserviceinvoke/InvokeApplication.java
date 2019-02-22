package com.example.eurekaserviceinvoke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  //@EnableDiscoveryClient 注解来修改启动类，该注解使得服务调用者，有能力去 Eureka 中发现服务
public class InvokeApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvokeApplication.class, args);
    }

}
