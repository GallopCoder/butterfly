package com.zhonghong.dubbo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 添加完 返回总共消费
     * @param a
     * @return
     */
    @RequestMapping("/add")
    public String getCost(int a){
        return "该产品总共消费 ："+productService.getCost(a);
    }
}
