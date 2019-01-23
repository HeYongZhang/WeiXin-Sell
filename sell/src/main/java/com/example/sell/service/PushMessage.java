package com.example.sell.service;

import com.example.sell.dto.OrderDTO;
import org.hibernate.criterion.Order;

/**
 * 推送消息
 */
public interface PushMessage {

    /**
     * 完结订单
     * @param orderDTO
     */
    void orderStatus_finish(OrderDTO orderDTO);

    /**
     * 下单
     */
    void orderStatus_create(OrderDTO orderDto);

    void orderStatus_cancle(OrderDTO orderDTO);
}
