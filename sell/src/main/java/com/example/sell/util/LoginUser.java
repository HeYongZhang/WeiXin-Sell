package com.example.sell.util;



import com.example.sell.entity.SellerInfo;

import java.util.HashMap;

public class LoginUser {
    //储存用户信息
    private static HashMap<String, Object> loginMap = new HashMap<String, Object>();
    
    public static HashMap<String, Object> getLoginMap() {
        return loginMap;
    }

}
