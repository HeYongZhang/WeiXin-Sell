package com.example.sell.service.impl;

import com.example.sell.dto.OrderDTO;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.service.BuyerService;
import com.example.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】：无法查询到该订单，orderId：{}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancle(orderDTO);
    }

    public OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO one = orderService.findOne(orderId);
        if (one == null) {
            return null;
        }

        if (!one.getBuyerOpenid().equals(openid)) {
            log.error("【订单查询】：订单的openid不一致，orderDTO：{}，openid：{}", one.getBuyerOpenid(), openid);
            throw new SellException(ResultEnum.BUYER_OPENID_NOT_MATCH);
        }
        return one;
    }
}
