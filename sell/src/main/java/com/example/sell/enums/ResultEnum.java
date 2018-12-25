package com.example.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不正确"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详细不存在"),
    ORDER_STATUS_NOT_ERROR(14,"订单状态错误"),
    ORDER_DETAIL_EMPTY(15,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(16,"订单已支付"),
    ORDER_PARAM_ERROR(17,"创建订单时，参数错误"),
    CART_EMPTY(18,"购物车为空"),
    BUYER_OPENID_NOT_MATCH(19,"该订单不属于当前用户");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
