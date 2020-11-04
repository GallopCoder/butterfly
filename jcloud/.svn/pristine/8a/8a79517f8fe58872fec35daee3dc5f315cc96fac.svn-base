package com.artcode.feign.client;


import com.artcode.feign.entity.RestfulResult;
import com.artcode.feign.entity.ServiceInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Component
@FeignClient(value = "art-service", fallback=ServiceFallback.class) //这里的value对应调用服务的spring.applicatoin.name
public interface ServiceFeignClient {


    @RequestMapping(value = "/service/hello")//对应微服务的接口uri
    RestfulResult hello(@RequestBody ServiceInfo serviceInfo);//用于熔断处理
}

