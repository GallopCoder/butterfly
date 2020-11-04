package com.artcode.ribbon.controller;

import com.artcode.ribbon.entity.ServiceInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController // 重要，如果用Controller会404
@RequestMapping(value = "service")
public class ServiceController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${server.port}")
    String port;

    // 调用：localhost:6007/consumerServiceRibbon?token=1
    @RequestMapping("/consumerServiceRibbon")
    @HystrixCommand(fallbackMethod="consumerServiceRibbonFallback")
    public String consumerServiceRibbon(@RequestBody ServiceInfo serviceInfo){
        return this.restTemplate.postForObject("http://art-service/service/hello?token=1", serviceInfo, String.class);
    }

    public String consumerServiceRibbonFallback(@RequestBody ServiceInfo serviceInfo){
        return "consumerServiceRibbon异常，端口：" + port + "，Name=" + serviceInfo.getName();
    }
}
