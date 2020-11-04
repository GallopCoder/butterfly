package com.zhonghong.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import com.dubbo.api.service.CostService;

/**
 * 产品service
 */
@Service
public class ProductServiceImpl implements ProductService {
    /**
     * 注：使用dubbo的注解 com.alibaba.dubbo.config.annotation.Reference。进行远程调用service
     */
    @Reference
    private CostService costService;

    @Override
    public Integer getCost(int a) {
        return costService.add(a);
    }
}
