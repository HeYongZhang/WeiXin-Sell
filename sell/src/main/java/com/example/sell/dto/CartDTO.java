package com.example.sell.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车传输模块
 */
@Data
public class CartDTO implements Serializable {

    private static final long serialVersionUID = 6012627306991685839L;

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
