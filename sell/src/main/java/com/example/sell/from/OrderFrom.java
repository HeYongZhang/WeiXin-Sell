package com.example.sell.from;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderFrom {

    /** 买家姓名  */
    @NotEmpty(message = "姓名必填")
    private String name;

    /** 买家号码  */
    @NotEmpty(message = "电话号码必填")
    private String phone;

    /** 买家地址  */
    @NotEmpty(message = "地址必填")
    private String address;

    /** 买家openid  */
    @NotEmpty(message = "openid必填")
    private String openid;

    /** 买家购物车  */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
