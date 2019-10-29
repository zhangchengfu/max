package com.laozhang.max_feign.dao;

import com.laozhang.max_feign.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {

    /**
     * 创建订单
     * @param order
     * @return
     */
    void create(Order order);
}
