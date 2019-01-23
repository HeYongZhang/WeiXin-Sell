package com.example.sell.repository;

import com.example.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /* 分页查询+买家微信号buyerOpenid 条件查询 */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);


   /* @Query(value = "SELECT * FROM order_master WHERE create_time like concat('%',:createTime,'%') ",nativeQuery = true,
            countQuery = "SELECT count(orderId) FROM order_master WHERE create_time like concat('%',:createTime,'%')")*/
   @Query(value = "FROM OrderMaster WHERE createTime like concat('%',:createTime,'%') order by createTime desc ",
           countQuery = "select count(orderId) from OrderMaster WHERE createTime like concat('%',:createTime,'%') order by createTime desc ")
    Page<OrderMaster> findList(Pageable pageable, @Param("createTime") String createTime);
}
