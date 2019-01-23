package com.example.sell.controller;

import com.example.sell.entity.SellerInfo;
import com.example.sell.from.SellerInfoFrom;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeChatControllerTest {

    @Autowired
    private RedisTemplate myredisTemplate;

    @Test
    public void loginout() {
        Object token_123 = myredisTemplate.opsForValue().get("token_123");
        Gson gson = new Gson();
        SellerInfoFrom sellerInfoFrom = gson.fromJson(token_123.toString(), SellerInfoFrom.class);
        System.out.println(sellerInfoFrom);
    }
}