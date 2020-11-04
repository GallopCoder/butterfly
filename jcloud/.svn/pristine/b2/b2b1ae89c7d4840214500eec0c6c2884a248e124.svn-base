package com.artcode.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@ServletComponentScan
@EnableDiscoveryClient
@EnableEurekaClient
@RefreshScope   //开启配置更新功能
public class ArtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtServiceApplication.class, args);
    }
}
