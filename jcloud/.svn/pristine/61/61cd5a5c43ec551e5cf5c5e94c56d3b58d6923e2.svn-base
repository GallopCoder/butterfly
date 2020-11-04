package com.artcode.feign.client;


import com.artcode.feign.entity.RestfulResult;
import com.artcode.feign.entity.ServiceInfo;
import org.springframework.stereotype.Component;


@Component
public class ServiceFallback implements ServiceFeignClient {

    @Override
    public RestfulResult hello( ServiceInfo serviceInfo) {
        RestfulResult result = new RestfulResult();
        result.setData("服务调用失败");
        return result;
    }
}

