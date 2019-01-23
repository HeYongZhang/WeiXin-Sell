package com.example.sell.controller;

import com.example.sell.VO.ResultVO;
import com.example.sell.convert.OrderFrom2OrderDTOConver;
import com.example.sell.dto.OrderDTO;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.from.OrderFrom;
import com.example.sell.service.BuyerService;
import com.example.sell.service.OrderService;
import com.example.sell.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;



    /**
     * 创建订单
     *
     * @param orderFrom  synchronized
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public synchronized ResultVO<Map<String, String>> CreateOrder(@Valid OrderFrom orderFrom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【订单创建】：订单参数存在错误：OrderFrom", orderFrom);
            throw new SellException(ResultEnum.ORDER_PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderFrom2OrderDTOConver.conver(orderFrom);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【订单创建】：购物车不能为空，OrderDetatileList：{}", orderDTO.getOrderDetailList());
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createOrder = orderService.create(orderDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", createOrder.getOrderId());
        return ResultVOUtil.success(map);
    }

  //  /sell/buyer/order/list
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(name = "openid") String openid,
                                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "pagesize", defaultValue = "10") Integer pagesize) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单】：openid为空，openid：{}", openid);
            throw new SellException(ResultEnum.ORDER_PARAM_ERROR);
        }
        Pageable pageable = new PageRequest(page, pagesize);
        Page<OrderDTO> list = orderService.findList(openid, pageable);
        return ResultVOUtil.success(list.getContent());
    }


    @GetMapping(value = "/detail")
    public ResultVO<List<OrderDTO>> detail(@RequestParam(name = "openid") String openid,
                                           @RequestParam(name = "orderId") String orderId) {

        OrderDTO orderOne = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderOne);
    }

    @PostMapping("/cancel")
    public ResultVO<List<OrderDTO>> cancel(@RequestParam(name = "openid") String openid,
                                           @RequestParam(name = "orderId") String orderId) {

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }

}
