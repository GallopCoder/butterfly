package com.zhonghong.dubbo.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.api.service.CostService;
import org.springframework.stereotype.Component;


/**
 * 花费服务
 */
@Service(interfaceClass = CostService.class, retries = 0, timeout = 6000)//注：使用dubbo的service服务提供者需要将服务注册至注册中心，如zookeeper

public class CostServiceImpl implements CostService {
    /**
     * 假设之前总花费了100
     */
    private final Integer totalCost = 1000;

    /**
     * 之前总和 加上 最近一笔
     * @param cost
     * @return
     */
    @Override
    public Integer add(int cost) {
        return totalCost + cost;
    }
}
