package com.example.sell.service.impl;

import com.example.sell.entity.SellerInfo;
import com.example.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    private final String openid = "abcdefg";

    @Autowired
    private SellerService sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo sellerInfoByOpenid = sellerService.findSellerInfoByOpenid(openid);
        assertEquals(openid,sellerInfoByOpenid.getOpenid());
    }
}