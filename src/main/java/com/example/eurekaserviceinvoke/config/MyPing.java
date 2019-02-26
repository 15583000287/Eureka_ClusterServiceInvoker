package com.example.eurekaserviceinvoke.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义Ping机制实现类
 */
public class MyPing implements IPing {
    private static Logger logger =  LoggerFactory.getLogger(MyPing.class);

    @Override
    public boolean isAlive(Server server) {
        //System.out.println("自定义 Ping 类，服务器信息：" + server.getHostPort() +"");
        logger.info("自定义 Ping 类，服务器信息："+server.getHostPort());
        return false;
    }
}
