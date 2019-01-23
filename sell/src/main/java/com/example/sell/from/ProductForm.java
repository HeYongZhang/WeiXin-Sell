package com.example.sell.from;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {

    /**
     * id
     */
    private String productId;

    /**
     * 名字
     */
    private String productName;

    /**
     * 单价
     */
    private BigDecimal productPrice;

    /**
     * 库存
     */
    private Integer produceStock;

    /**
     * 描述
     */
    private String productDescription;

    /**
     * 图标
     */
    private String productIcon;

    /**
     * 类目编号
     */
    private Integer categoryType;
}
