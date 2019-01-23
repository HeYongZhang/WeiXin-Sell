package com.example.sell.service.impl;

import com.example.sell.dto.OrderDTO;
import com.example.sell.service.OrderService;
import com.example.sell.service.PushMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne("1546481683580586061");
        pushMessage.orderStatus_finish(orderDTO);
    }

    @Test
    public void orderStatus_cancle(){
        OrderDTO orderDTO = orderService.findOne("1546481683580586060");
        orderService.cancle(orderDTO);
    }
}