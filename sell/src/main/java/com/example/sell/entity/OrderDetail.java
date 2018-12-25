package com.example.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class OrderDetail {

    @Id
    private String detailId;

    /*   订单id   */
    private String orderId;

    /*   商品id   */
    private String productId;

    /*   商品名字   */
    private String productName;

    /*   商品价格   */
    private BigDecimal productPrice;

    /*   商品数量   */
    private Integer productQuantity;

    /*   商品图标   */
    private String productIcon;

    public OrderDetail(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public OrderDetail() {
    }
 /*   private Date createTime ;

    private Date updateTime;*/
}
