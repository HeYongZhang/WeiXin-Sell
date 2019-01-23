package com.example.sell.controller.pay;


import com.example.sell.dto.OrderDTO;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.service.OrderService;
import com.example.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

     //  /sell/pay/create
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                         @RequestParam("returnUrl")String returnURL,
                            Map<String,Object> map){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO==null){
            log.error("【微信支付】：订单不存在,orderDTO：{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
       // PayResponse payResponse = payService.create(orderDTO);
       // map.put("returnURL",returnURL);
        //log.info("【微信支付】：唤醒中......");
        return new ModelAndView("redirect:".concat(returnURL));
    }
}
