package com.example.sell.repository;

import com.example.sell.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    private final String OPENID="16218237asd";

    @Autowired
    private OrderMasterRepository repository;


    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> byBuyerOpenid = repository.findByBuyerOpenid(OPENID, new PageRequest(0, 1));
        assertNotEquals(0,byBuyerOpenid.getTotalElements());
    }

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerAddress("长沙湖南开福区青湖街道湘江北路一段1栋");
        orderMaster.setBuyerName("刘晓燕");
        orderMaster.setBuyerPhone("15273592038");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(10.2));
        OrderMaster save = repository.save(orderMaster);
        assertNotNull(save);
    }
}