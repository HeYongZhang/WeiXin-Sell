package com.example.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /** 公众账号appid */
    private String mpAppId;

    /** 公众账号appsecret */
    private String mpAppSecret;

    /** 商户账号id */
    private String mchId;

    /** 商户秘钥  */
    private String mchKey;

    /** 商户证书路径  */
    private String keyPath;

    private String notifyUrl;

    private Map<String,String> templateId;
}
