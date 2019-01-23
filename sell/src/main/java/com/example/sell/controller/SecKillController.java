package com.example.sell.controller;

import com.example.sell.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/skill")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId)throws Exception{
        return secKillService.querySecKillProductInfo(productId);
    }

    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId)throws Exception{
        log.info("@skill request , productId:"+productId);
        secKillService.orderProductMockDiffUser(productId);
        return secKillService.querySecKillProductInfo(productId);
    }
}
