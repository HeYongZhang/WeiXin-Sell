package com.example.sell.dto;

import com.example.sell.entity.OrderDetail;
import com.example.sell.util.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /*  买家名字  */
    private String buyerName;

    /*  买家电话 */
    private String buyerPhone;

    /*  买家地址 */
    private String buyerAddress;

    /*  买家微信openid */
    private String buyerOpenid;

    /** 订单编号 */
    private String orderId;

    /*  订单总金额  */
    private BigDecimal orderAmount;

    /*  订单状态,默认0新下单 */
    private Integer orderStatus;

    /*   支付状态,默认0未支付   */
    private Integer payStatus;

    /*  创建时间   @JsonSerialize使用Date2LongSerialize中重写的 serialize 进行计算，排除3个0*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /*  修改时间    */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /* 订单详情 */
    private List<OrderDetail> orderDetailList;
}
