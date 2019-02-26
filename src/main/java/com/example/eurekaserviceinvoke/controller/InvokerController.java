package com.example.eurekaserviceinvoke.controller;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
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
 *
 *使用代码配置了Ribbon的Rule和Ping
 * ########### 使用了自定义负载规则，由于choose方法只返回第一个服务器，所以每次都访问同一个服务器 ########
 */
@RestController
@Configuration
public class InvokerController {
    @Bean
    @LoadBalanced   //此注解使之有访问分布式服务的能力
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * 1.用来对外发布REST服务
     * 2.该方法只是一个路由作用，实际上使用RestTemplate来调用的service-provider（服务提供者）服务
     */
    @RequestMapping("/router")
    public String router(){
        RestTemplate restTemplate = getRestTemplate();
        //根据应用名称调用服务  格式： http://应用名/服务名
        String json = restTemplate.getForObject("http://service-provider/user/1", String.class);
        return  json;
    }


    @RequestMapping("router2")
    public String router2(){
        RestTemplate restTemplate = getRestTemplate();
        //根据应用名调用服务
        String json = restTemplate.getForObject("http://service-provider/user/2",String.class);
        return json;
    }


    /**
     * Spring Cloud 对 Ribbon 进行封装，例如像负载客户端、负载均衡器等，我们可以直接
     * 使用 Spring 的 LoadBalancerClient 来处理请求以及服务选择
     */
    @Autowired
    private LoadBalancerClient loadBalancer;
    @RequestMapping("/uselb")
    public ServiceInstance uselb(){
        // 查找服务器实例
        ServiceInstance si = loadBalancer.choose("service-provider");
        System.out.println(si.getUri());
        return si;
    }


    /**
     * 除了使用 Spring 封装的负载客户端外，还可以直接使用 Ribbon 的 API
     * 直接获取 Spring Cloud 默认环境中，各个 Ribbon 的实现类
     *
     * 使用了 SpringClientFactory，通过该实例，可获取各个默认的实现类以及配置，
     * 分别输出了 默认配置 以及“service-provider”配置。
     */

    @Autowired
    private SpringClientFactory factory;

    @RequestMapping(value = "/defaultValue")
    public String defaultValue() {
        System.out.println("==== 输出默认配置：");
        // 获取默认的配置
        ZoneAwareLoadBalancer alb = (ZoneAwareLoadBalancer) factory
                .getLoadBalancer("default");
        System.out.println(" IClientConfig: " + factory.getLoadBalancer("default").getClass()
                .getName());
        System.out.println(" IRule: " + alb.getRule().getClass().getName());
        System.out.println(" IPing: " + alb.getPing().getClass().getName());
        System.out.println(" ServerList: " + alb.getServerListImpl().getClass().getName());
        System.out.println(" ServerListFilter: " + alb.getFilter().getClass().getName());
        System.out.println(" ILoadBalancer: " + alb.getClass().getName());
        System.out.println(" PingInterval: " + alb.getPingInterval());



        System.out.println("==== 输出 cloud-provider 配置：");
        // 获取 service-provider 的配置
        ZoneAwareLoadBalancer alb2 = (ZoneAwareLoadBalancer) factory
                .getLoadBalancer("service-provider");
        System.out.println(" IClientConfig: " + factory.getLoadBalancer("cloud-provider").getClass()
                .getName());
        System.out.println(" IRule: " + alb2.getRule().getClass().getName());
        System.out.println(" IPing: " + alb2.getPing().getClass().getName());
        System.out.println(" ServerList: " + alb2.getServerListImpl().getClass().getName());
        System.out.println(" ServerListFilter: " + alb2.getFilter().getClass().getName());
        System.out.println(" ILoadBalancer: " + alb2.getClass().getName());
        System.out.println(" PingInterval: " + alb2.getPingInterval());
        return "";

//        IClientConfig
//        IRule
//        IPing
//        ServerList
//        ServerListFilter
//        ILoadBalancer
    }


}
