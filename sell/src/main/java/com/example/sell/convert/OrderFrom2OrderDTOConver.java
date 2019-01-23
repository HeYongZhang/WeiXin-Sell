package com.example.sell.convert;

import com.example.sell.dto.OrderDTO;
import com.example.sell.entity.OrderDetail;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.from.OrderFrom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFrom2OrderDTOConver {

    public static OrderDTO conver(OrderFrom orderFrom) {
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderFrom.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("【对象转换】：对象转换失败，orderFrom.getItems:{}", orderFrom.getItems());
            throw new SellException(ResultEnum.ORDER_PARAM_ERROR);
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderFrom.getName());
        orderDTO.setBuyerOpenid(orderFrom.getOpenid());
        orderDTO.setBuyerPhone(orderFrom.getPhone());
        orderDTO.setBuyerAddress(orderFrom.getAddress());
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
