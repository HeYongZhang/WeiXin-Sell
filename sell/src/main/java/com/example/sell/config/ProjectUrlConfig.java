package com.example.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat.url")
@Data
public class ProjectUrlConfig {


    /**
     * 微信公众平台
     */
    private String wechatMpAutorizeUrl;

    /**
     * 第三方二维码验证
     */
    private String wechatRqCodeUrl;

    /**
     * 点餐系统
     */
    private String sell;
}
