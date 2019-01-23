package com.example.sell.service.impl;

import com.example.sell.dto.CartDTO;
import com.example.sell.dto.OrderDTO;
import com.example.sell.entity.OrderDetail;
import com.example.sell.enums.OrderStatusEnum;
import com.example.sell.enums.PayStatusEnum;
import com.example.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    private final String Openid = "110110";

    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        //用户信息
        orderDTO.setBuyerName("刘宁");
        orderDTO.setBuyerAddress("长沙开福区青湖街道北大青鸟");
        orderDTO.setBuyerPhone("15273592038");
        orderDTO.setBuyerOpenid(Openid);

        //购物车内容
        List<OrderDetail> orderDetailList = new ArrayList<>();

        orderDetailList.add(new OrderDetail("123", 2));
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】：result={}", result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne("1545468718369775890");
        assertNotEquals(0, orderDTO.getOrderDetailList().size());
    }

    @Test
    public void findList() {
        Page<OrderDTO> list = orderService.findList("110110", new PageRequest(0, 2));
        System.out.println(list.getContent());
    }

    @Test
    public void cancle() {
        OrderDTO orderDTO = orderService.findOne("1545468718369775890");
        OrderDTO cancle = orderService.cancle(orderDTO);
        assertEquals(OrderStatusEnum.DOWN.getCode(), cancle.getOrderStatus());
    }

    @Test
    @Transactional
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1545468718369775890");
        OrderDTO finish = orderService.finish(orderDTO);
        assertEquals(OrderStatusEnum.FINISH.getCode(), orderDTO.getOrderStatus());
    }

    @Test
    @Transactional
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1545468718369775890");
        OrderDTO paid = orderService.paid(orderDTO);
        assertEquals(PayStatusEnum.SUCCESS.getCode(), paid.getPayStatus());
    }

    @Test
    public void findList2(){
        Page<OrderDTO> list = orderService.findList(new PageRequest(0, 5),"2019-01-03");
       // assertEquals(2,list.getSize());
        assertTrue("查询所有的订单列表错误",list.getSize()==5);
    }
}