package com.artcode.service.controller;


import com.artcode.service.common.utils.CommUtil;
import com.artcode.service.entity.RestfulResult;
import com.artcode.service.entity.ServiceInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController // 重要，如果用Controller会404
@RequestMapping(value = "service")
public class ServiceController {


    @RequestMapping(value = "hello")
    public void login(HttpServletRequest request, HttpServletResponse response, @RequestBody ServiceInfo serviceInfo) {
        RestfulResult restfulResult = new RestfulResult();
        try {
            restfulResult.setData("Service2:Welcome " + serviceInfo.getName() + "!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommUtil.printDataJason(response, restfulResult);
    }

    @RequestMapping(value = "rest")
    public String rest(@RequestBody ServiceInfo serviceInfo){
        return "Service1:Welcome " + serviceInfo.getName() + " !";
    }
}
