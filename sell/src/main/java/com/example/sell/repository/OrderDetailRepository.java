package com.example.sell.repository;

import com.example.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String > {

    /** 根据订单id查询详情  */
    List<OrderDetail> findByOrOrderId(String orderId);
}
