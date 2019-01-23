package com.example.sell.service;

import com.example.sell.entity.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo login(String name,String pwd);
}
