package com.example.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品实体类
 */
@Table(name = "product_info")
@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;

    /** 名字 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer produceStock;

    /** 描述 */
    private String productDescription;

    /** 图标 */
    private String productIcon;

    /** 状态，0正常1下架*/
    private Integer productStatus;

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}
