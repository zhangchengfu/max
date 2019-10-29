package com.laozhang.max_feign.service;


import com.laozhang.max_feign.entity.Order;

public interface OrderService {

    /**
     * 创建订单
     * @param order
     * @return
     */
    void create(Order order);
}
