package com.example.sell.service.impl;

import com.example.sell.convert.OrderMaster2OrderDTOConverter;
import com.example.sell.dto.CartDTO;
import com.example.sell.dto.OrderDTO;
import com.example.sell.entity.OrderDetail;
import com.example.sell.entity.OrderMaster;
import com.example.sell.entity.ProductInfo;
import com.example.sell.enums.OrderStatusEnum;
import com.example.sell.enums.PayStatusEnum;
import com.example.sell.enums.ProductStatusEnum;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.ResponseBankException;
import com.example.sell.exception.SellException;
import com.example.sell.repository.OrderDetailRepository;
import com.example.sell.repository.OrderMasterRepository;
import com.example.sell.service.OrderService;
import com.example.sell.service.ProductService;
import com.example.sell.service.PushMessage;
import com.example.sell.service.WebSocket;
import com.example.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@CacheConfig(cacheNames = "order")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private WebSocket webSocket;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        //总价变量
        BigDecimal amount = new BigDecimal(BigInteger.ZERO);

        //生成订单号
        String orderId = KeyUtil.GetUniquekey();

        //循环将订单写入order_detail
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            //获取商品
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            //测试商品是否存在
            if (productInfo == null || !productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
                //throw new ResponseBankException();
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //计算总价
            amount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(amount);

            //写入订单数据库
            orderDetail.setOrderId(orderId);

            orderDetail.setDetailId(KeyUtil.GetUniquekey());
            //将数据复制到orderDetail
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderAmount(amount);
        orderMasterRepository.save(orderMaster);

        //扣库存
        List<CartDTO> collect = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(collect);

        //websocket 发送消息给后台
        webSocket.sendMessage("有新的订单");

        return orderDTO;
    }

    /**
     * 单个查询
     *
     * @param orderId
     * @return
     */
    @Override
    @Cacheable(key = "#orderId")
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> OrderDetailList = orderDetailRepository.findByOrOrderId(orderMaster.getOrderId());
        if (CollectionUtils.isEmpty(OrderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(OrderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> byBuyerOpenid = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        //将OrderMaster中的数据转为OrderDTO
        List<OrderDTO> convert = OrderMaster2OrderDTOConverter.convert(byBuyerOpenid.getContent());
        //OrderDTO进行分页
        return new PageImpl<OrderDTO>(convert, pageable, byBuyerOpenid.getTotalElements());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "product",allEntries = true),
            },
            put = {
                    @CachePut(key = "#result.orderId")
            }
    )
    public OrderDTO cancle(OrderDTO orderDTO) {
        //orderId orderStatus ProductQuantity
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】：订单状态错误，OrderId：{}，OrderStatus：{}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_NOT_ERROR);
        }

        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.DOWN.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster upadteOrderMaster = orderMasterRepository.save(orderMaster);
        if (upadteOrderMaster == null) {
            log.error("【取消订单】：订单中无商品详情,OrderDTO：{}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        //返回库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //发送取消订单通知
        pushMessage.orderStatus_cancle(orderDTO);

        /*//退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }*/

        return orderDTO;
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "product",allEntries = true),
            },
            put = {
                    @CachePut(key = "#result.orderId")
            }
    )
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】：订单状态错误，OrderId：{}，OrderStatus：{}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_NOT_ERROR);
        }

        //修改订单
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster UpdateOrderMaster = orderMasterRepository.save(orderMaster);
        if (UpdateOrderMaster == null) {
            log.error("【完结订单】：订单修改失败,OrderDTO：{}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //推送微信模板消息
        pushMessage.orderStatus_finish(orderDTO);
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】：订单状态错误，OrderId：{}，OrderStatus：{}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_NOT_ERROR);
        }

        //判断订单是否已支付
        if (orderDTO.getPayStatus().equals(PayStatusEnum.WAIT)) {
            log.error("【订单支付】：订单已支付，OrderId：{}，PayStatus：{}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改订单
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster UpdateOrderMaster = orderMasterRepository.save(orderMaster);
        if (UpdateOrderMaster == null) {
            log.error("【订单支付】：订单支付失败,OrderDTO：{}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable,String createTime) {
        Page<OrderMaster> orderMasterpage = orderMasterRepository.findList(pageable,createTime);
        List<OrderDTO> convert = OrderMaster2OrderDTOConverter.convert(orderMasterpage.getContent());
        return new PageImpl<>(convert,pageable,orderMasterpage.getTotalElements());
    }
}
