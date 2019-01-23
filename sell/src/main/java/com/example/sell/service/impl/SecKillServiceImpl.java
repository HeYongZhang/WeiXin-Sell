package com.example.sell.service.impl;

import com.example.sell.exception.SellException;
import com.example.sell.service.RedisLock;
import com.example.sell.service.SecKillService;
import com.example.sell.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10 * 1000;

    @Autowired
    private RedisLock redisLock;
    /**
     * 模拟多个表，商品信息表，库存表，订单表
     */
    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;

    static
    {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",100000);
        stock.put("123456",100000);
    }

    private String queryMap(String productId){
        return "国庆活动，肯德基天王套餐特价，限量份"
                +products.get(productId)
                +"还剩:"+stock.get(productId)+"份"
                +"该商品成功下单用户数目:"
                +orders.size()+"人";
    }

    @Override
    public String querySecKillProductInfo(String productInfo) {
        return this.queryMap(productInfo);
    }


    /**
     * 高并发解决办法
     *  500请求测试结果
     *  1.使用synchronized可以解决
     *      缺点 速度慢，无法满足需求，
     *      优点 请求逐一进入线程拿到锁，安全
     *  2.使用redis的安全锁，看
     *      缺点  请求过多时，只有少部分请求才能拿到锁
     *      优点  执行速度快，安全
     * @param productInfo
     */
    @Override
    public void orderProductMockDiffUser(String productInfo) {

        //加锁
        long time = System.currentTimeMillis()+TIMEOUT;
            if(!redisLock.lock(productInfo,String.valueOf(time))){
                throw new SellException(101,"哎呦喂，人也太多了，换个姿势试试");
            };

        int stokNum = stock.get(productInfo);
        //1.查询该商品库存，为0则活动结束
        if(stock.size()==0){
            throw new SellException(100,"活动结束");
        }else{
            //下单（模拟不同用户openid不同）
            orders.put(KeyUtil.GetUniquekey(),productInfo);
            //减库存
            stokNum = stokNum-1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productInfo,stokNum);
        }
        redisLock.unlock(productInfo,String.valueOf(time));
    }
}
