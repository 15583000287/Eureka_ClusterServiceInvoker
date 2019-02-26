package com.example.eurekaserviceinvoke.restclient;

import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.util.HashMap;
import java.util.Map;

public class RestletClient {
    public static void main(String[] args) throws Exception {
        ClientResource client = new ClientResource("http://localhost:8080/user/1");
        // 调用 get 方法，服务端发布的是 GET
        Representation response = client.get();
        // 创建 JacksonRepresentation 实例，将响应转换为 Map
        JacksonRepresentation jr = new JacksonRepresentation(response, HashMap.class);
        // 获取转换后的 Map 对象
        Map result = (Map) jr.getObject();
        // 输出结果
        System.out.println(result.get("id") + "-" + result.get("username") + "-" + result.get("password") + "-" + result.get("message"));
    }
}
