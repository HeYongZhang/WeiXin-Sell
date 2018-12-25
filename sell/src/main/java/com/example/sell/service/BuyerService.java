package com.example.sell.service;

import com.example.sell.dto.OrderDTO;

public interface BuyerService {

    /** 判断是否是当前买家的订单 */
        OrderDTO findOrderOne(String openid , String orderId);

    /** 取消订单操作用户判断 */
        OrderDTO cancelOrder(String openid , String orderId);
}
