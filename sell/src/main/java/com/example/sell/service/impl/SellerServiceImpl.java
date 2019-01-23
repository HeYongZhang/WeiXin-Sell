package com.example.sell.service.impl;

import com.example.sell.entity.SellerInfo;
import com.example.sell.repository.SellerInfoRepository;
import com.example.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

    @Override
    public SellerInfo login(String name, String pwd) {
        return sellerInfoRepository.findByUsernameAndPassword(name,pwd);
    }
}
