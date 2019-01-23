package com.example.sell.repository;

import com.example.sell.entity.SellerInfo;
import com.example.sell.util.KeyUtil;
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
public class SellerInfoRepositoryTest {

    private final String openid = "abcdefg";

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void findByOpenid() {
        SellerInfo byOpenid = sellerInfoRepository.findByOpenid(openid);
        assertEquals(openid,byOpenid.getOpenid());
    }

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.GetUniquekey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid(openid);
        SellerInfo save = sellerInfoRepository.save(sellerInfo);
        assertNotNull(save);
    }

}