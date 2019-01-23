package com.example.sell.service.impl;

import com.example.sell.entity.ProductInfo;
import com.example.sell.enums.ProductStatusEnum;
import com.example.sell.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findOne() {
        ProductInfo one = productService.findOne("123");
        assertEquals("123", one.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        assertNotEquals(0, upAll.size());

    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> all = productService.findAll(request);
        System.out.println(all.getTotalPages());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("124");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(10.2));
        productInfo.setProduceStock(100);
        productInfo.setProductDescription("口感酥脆，外焦里内");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setCategoryType(2);
        ProductInfo save = productService.save(productInfo);
        assertNotNull(save);
    }

    @Test
    public void offsale(){
        ProductInfo productInfo = productService.Off_Sale("123");
        assertEquals(productInfo.getProductStatus(),ProductStatusEnum.DOWN.getCode());
    }

    @Test
    public void onsale(){
        ProductInfo productInfo = productService.OnSale("123");
        assertEquals(productInfo.getProductStatus(),ProductStatusEnum.UP.getCode());
    }
}