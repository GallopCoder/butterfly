package com.artcode.feign.controller;


import com.artcode.feign.client.ServiceFeignClient;
import com.artcode.feign.common.util.CommUtil;
import com.artcode.feign.entity.RestfulResult;
import com.artcode.feign.entity.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ServiceController {

    @Autowired
    ServiceFeignClient serviceFeignClient;

    // 调用：localhost:6004/consumerService?token=1
    @PostMapping("/consumerService/hello")
    public void consumerService(HttpServletRequest request, HttpServletResponse response, @RequestBody ServiceInfo serviceInfo) {

        RestfulResult restfulResult = serviceFeignClient.hello(serviceInfo);
        CommUtil.printDataJason(response, restfulResult);
    }

}
