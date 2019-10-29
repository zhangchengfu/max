package com.laozhang.max_feign.entity;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单
 */
@Data
public class Order {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer count;

    private BigDecimal money;

}
