package com.example.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    USER_SUCCES(0,"操作成功"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单详细不存在"),
    ORDER_STATUS_NOT_ERROR(14, "订单状态错误"),
    ORDER_DETAIL_EMPTY(15, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(16, "订单已支付"),
    ORDER_PARAM_ERROR(17, "创建订单时，参数错误"),
    CART_EMPTY(18, "购物车为空"),
    BUYER_OPENID_NOT_MATCH(19, "该订单不属于当前用户"),
    WECHAT_MP_ERROR(20,"微信公众号方面存在问题"),
    ORDER_CENCER_SUCCESS(21,"订单取消成功"),
    ORDER_FINISH_SUCCESS(22,"订单完结成功"),
    PRODUCT_IS_ONSALE(23,"商品处于在架状态,命令无效"),
    PRODUCT_IS_OFFSALE(24,"商品处于在下架状态,命令无效"),
    PRODUCT_OFFSALE_SUCCESS(25,"商品下架操作成功"),
    PRODUCT_ONSALE_SUCCESS(26,"商品上架操作成功"),
    SELLER_VAILD_ERROR(27,"格式不符合要求,错误"),
    USER_VIP_LOGIN_NOT_OPENID(28,"你不是超级管理员，行为可疑，已记录"),
    USER_VIP_LOGIN_OUT_SUCESS(29,"VIP管理员退出成功"),
    USER_VIP_LOGIN_IN_SUCESS(30,"VIP管理员登录成功"),
    USER_VIP_NOT_ADDRES(31,"账户异地登录"),
    CATEGORY_NOT_EXIT(32,"商品类目不存在"),
    IMAGE_SIZE_OUT_OF_SCOPE(33,"图片大于1.5mb");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
