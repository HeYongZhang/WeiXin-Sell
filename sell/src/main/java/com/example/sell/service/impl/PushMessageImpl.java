package com.example.sell.service.impl;

import com.example.sell.config.WechatAccountConfig;
import com.example.sell.convert.DateToStringConver;
import com.example.sell.dto.OrderDTO;
import com.example.sell.service.PushMessage;
import com.example.sell.util.OrderStatusUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PushMessageImpl implements PushMessage {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public void orderStatus_finish(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        templateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("orderStatus"));
//"感谢您对我们商品的信任，谢谢"
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first","亲，请记得收货"),
                new WxMpTemplateData("keyword1","莲花府"),
                new WxMpTemplateData("keyword2","15273592038"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临")
        );
       templateMessage.setData(dataList);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e){
            log.error("【微信模块消息】 发送失败");
        }
    }

    @Override
    public void orderStatus_create(OrderDTO orderDTO) {

    }

    @Override
    public void orderStatus_cancle(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        templateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("orderCancle"));
        BigDecimal orderAmount = orderDTO.getOrderAmount();
        //"感谢您对我们商品的信任，谢谢"
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first","取消订单成功，欢迎再次光临本店"),
                new WxMpTemplateData("keyword1","莲花府"),
                new WxMpTemplateData("keyword2","15273592038"),
                new WxMpTemplateData("keyword3",orderDTO.getBuyerName()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword5",orderDTO.getPayStatusEnum().getMessage()),
                new WxMpTemplateData("keyword6",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword7","暂不支持"),
                //new WxMpTemplateData("keyword8", DateToStringConver.conver(new Date(),"yyyy-MM-dd HH:mm:ss")),
                new WxMpTemplateData("keyword9",orderAmount==null?"0":"￥"+orderAmount.doubleValue()),
                new WxMpTemplateData("remark","如果已支付，请等待退款通知")
        );
        templateMessage.setData(dataList);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e){
            log.error("【微信模块消息】 发送失败");
        }
    }

}
