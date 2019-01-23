package com.example.sell.service.impl;

import com.example.sell.dto.OrderDTO;
import com.example.sell.service.OrderService;
import com.example.sell.service.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceimpl implements PayService {

    private static final String ORDER_NAME="微信点餐订单";

    @Autowired
    private BestPayService bestPayService;

    private OrderService orderService;

    /**
     * 微信统一下单
     * @param orderDTO
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest= new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】：request：{}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        return  payResponse;
    }
}
