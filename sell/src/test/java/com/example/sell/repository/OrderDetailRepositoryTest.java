package com.example.sell.repository;

import com.example.sell.entity.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {


    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void findByOrOrderId() {
        List<OrderDetail> byOrOrderId = repository.findByOrOrderId("123456");
        assertNotEquals(0,byOrOrderId.size());
    }


    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("12345678");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123");
        orderDetail.setProductName("皮蛋瘦肉粥");
        orderDetail.setProductPrice(new BigDecimal(15.2));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://www asd.jpg");
        OrderDetail save = repository.save(orderDetail);
        assertNotNull(save);
    }
}