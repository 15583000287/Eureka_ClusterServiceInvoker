package com.example.eurekaserviceinvoke.restclient;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * CXF （Celtix + XFire）是一个开源的Services框架，Apache整合
 */
public class CxfClient {
    public static void main(String[] args) throws Exception {
        //创建WebClient
        WebClient client = WebClient.create("http://localhost:8080/user/1");
        //获取响应
        Response response = client.get();
        //获取响应内容
        InputStream is = (InputStream) response.getEntity();
        String content = IOUtils.readStringFromStream(is);

        System.err.println(content);
    }
}