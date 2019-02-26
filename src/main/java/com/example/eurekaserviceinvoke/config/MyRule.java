package com.example.eurekaserviceinvoke.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
/**
 * 自定义负载规则
 */
import java.util.List;

public class MyRule implements IRule {
    private ILoadBalancer lb;

    public MyRule() {
    }

    public MyRule(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public Server choose(Object o) {
        List<Server> servers = lb.getAllServers();
        System.out.println("Eureka服务器中 获得的服务器列表>>>>>>>>>>>>>：");
        for(Server s : servers){
            System.out.println(s.getHostPort());
        }
        return servers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.lb = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
