package com.laozhang.points.controller;

import com.laozhang.points.dao.OrderDao;
import com.laozhang.points.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DinerPointsController {

    @Autowired
    private OrderDao orderDao;

    @GetMapping("points")
    public String points() {

        return "points";
    }

    @GetMapping("save")
    public Object save() {
        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setOrderId(System.currentTimeMillis());
        orderEntity.setUserId(new Random().nextInt(999));
        orderDao.save(orderEntity);
        return orderEntity.getOrderId();
    }
}
