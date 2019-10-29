package com.laozhang.max_feign.controller;

import com.laozhang.max_feign.entity.Order;
import com.laozhang.max_feign.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderService orderServiceImpl;

    /**
     * 创建订单
     * @param order
     * @return
     */
    @GetMapping("create")
    public String create(Order order){
        orderServiceImpl.create(order);
        return "Create order success";
    }
}
