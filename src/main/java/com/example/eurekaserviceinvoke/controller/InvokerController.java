package com.example.eurekaserviceinvoke.controller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 在控制器中，配置了 RestTemplate 的 bean，RestTemplate 本来是 spring-web 模块下面的类，主要用来调用 REST 服务，本身并不具备调用分布式服务的能力
 * 但是RestTemplate 的 bean 被@LoadBalanced 注解修饰后，这个 RestTemplate 实例就具有访问分布式服务的能力，
 * 关于该类的一些机制，我们将放到负载均衡一章中讲解。
 *
 *
 * 在控制器中，新建了一个 router 的测试方法，用来对外发布 REST 服务，该方法只是
 * 一个路由作用，实际上使用 RestTemplate 来调用“service-provider”（服务提供者）的服务
 * 注意: 调用服务时，仅仅是通过服务名称来进行调用。
 */
@RestController
@Configuration
public class InvokerController {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    //用来对外发布REST服务，该方法只是一个路由作用，实际上使用RestTemplatelatel来调用的service-provider（服务提供者）服务
    @RequestMapping("/router")
    public String router(){
        RestTemplate restTemplate = getRestTemplate();
        //根据应用名称调用服务  格式： http://应用名/服务名
        String json = restTemplate.getForObject("http://service-provider/user/1", String.class);
        return  json;
    }

}
