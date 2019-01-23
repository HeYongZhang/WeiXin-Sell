package com.example.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 判断哪些请求获得锁
     * @param key
     * @param value  当前时间 + 超时时间 = 过期时间
     * @return
     */
    public boolean lock(String key,String value){
        //使用setIfAbsent,对应redis中的命令  (SETNX key value ，SETNX是”SET if Not EXists”的简写)
       if (stringRedisTemplate.opsForValue().setIfAbsent(key,value)){
           return true;
       }
       String currentValue = stringRedisTemplate.opsForValue().get(key);
       //是否过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)<System.currentTimeMillis()){
            String oldVal = stringRedisTemplate.opsForValue().getAndSet(key,value);
            if(!StringUtils.isEmpty(oldVal) && currentValue.equals(oldVal)){
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        String currenValue = stringRedisTemplate.opsForValue().get(key);
        try {
            if(!StringUtils.isEmpty(currenValue) && value.equals(currenValue)){
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【redis分布式锁】：解锁异常，{}",e);

        }
    }
}
