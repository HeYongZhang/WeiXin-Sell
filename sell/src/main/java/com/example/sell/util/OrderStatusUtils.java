package com.example.sell.util;

import com.example.sell.dto.OrderDTO;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

import java.util.Arrays;
import java.util.List;

public class OrderStatusUtils {

    public static List<WxMpTemplateData> getList(OrderDTO orderDTO,String first,String remark){
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first",first),
                new WxMpTemplateData("keyword1","莲花府"),
                new WxMpTemplateData("keyword2","15273592038"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark",remark)
        );
        return dataList;
    }
}
