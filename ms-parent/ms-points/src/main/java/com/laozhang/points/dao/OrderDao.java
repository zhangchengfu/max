package com.laozhang.points.dao;

import com.laozhang.points.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByOrderId(Long orderId);

    List<OrderEntity> findByUserId(Integer userId);
}
