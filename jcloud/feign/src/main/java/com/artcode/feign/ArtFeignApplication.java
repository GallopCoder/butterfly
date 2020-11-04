package com.artcode.feign;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication
@ServletComponentScan
@EnableDiscoveryClient
//@EnableEurekaClient  熔断机制管理模式，不能加此注解。否则，熔断机制会Timeout
@EnableFeignClients
public class ArtFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtFeignApplication.class, args);
    }
}
