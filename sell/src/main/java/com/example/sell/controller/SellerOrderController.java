package com.example.sell.controller;

import com.example.sell.convert.DateToStringConver;
import com.example.sell.dto.OrderDTO;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.service.OrderService;
import com.google.gson.Gson;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * /sell/seller/order/list
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name="page",defaultValue = "1")Integer page,
                             @RequestParam(name = "size",defaultValue = "10") Integer size,
                                String createTime){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(createTime)){
            createTime = DateToStringConver.conver_int(new Date());
        }else if(createTime.equals("yesterday")){
            createTime = DateToStringConver.Yesterday(new Date());
        }
        Pageable pageable = new PageRequest(page-1,size);
        Page<OrderDTO> OrderDTOpage = orderService.findList(pageable,createTime);
        map.put("OrderDTOpage",OrderDTOpage);
        map.put("currenPage",page);
        map.put("createTime",createTime);
        return new ModelAndView("order/list",map);
    }

    /**
     * /sell/seller/order/cencer
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cencer")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        map.put("url","/sell/seller/order/list");
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancle(orderDTO);
        }catch (SellException e){
            log.error("【卖家端取消订单】查询不到订单{}",orderId);
            map.put("msg",e);
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CENCER_SUCCESS.getMessage());
        return new ModelAndView("common/success",map);
    }

    /**
     *  sell/seller/order/detail
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                                Map<String,Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try {
             orderDTO = orderService.findOne(orderId);
        }catch (SellException e){
            log.error("【买家端查询订单详情】订单出现异常{}",e);
            map.put("msg",e);
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    /**
     *  sell/seller/order/finish
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
        map.put("url","/sell/seller/order/list");
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            log.error("【卖家端完结订单】发生异常{}",e);
            map.put("msg",e);
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        return new ModelAndView("common/success",map);
    }
}
