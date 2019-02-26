package com.example.eurekaserviceinvoke.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * 使用代码配置Ribbon，也可以使用配置文件配置 （yum , proterties）
 *
 *
 * @RibbonClient
 * name: 配置RibbonClient的名称 ( Eureka服务器实例名/应用名) 为service-provider，对应配置类为MyConfig
 *
 * 即：名称为“cloud-provider”的客户端，将使用 MyRule 与 MyPing 两个类
 */
//@RibbonClient(name = "service-provider",configuration = MyConfig.class)     //采用配置文件配置Ribbon
public class ServiceProviderConfig {
}
