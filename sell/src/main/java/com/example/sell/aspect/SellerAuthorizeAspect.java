package com.example.sell.aspect;

import com.example.sell.constant.CookieConstant;
import com.example.sell.constant.RedisConstant;
import com.example.sell.exception.SellerAuthorizeException;
import com.example.sell.exception.SellerStatusExecption;
import com.example.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private RedisTemplate myredisTemplate;

    @Pointcut("execution(public * com.example.sell.controller.Seller*.*(..))"+
    "&& !execution(public * com.example.sell.controller.SellerUserController.*(..))")
    public void verify_login(){
    }

    /*
    * +
    "&& !execution(public * com.example.sell.controller.SellerUserController.loginout())"
    * */
    @Pointcut("execution(public * com.example.sell.controller.SellerUserController.login*(..)) && !execution(public * com.example.sell.controller.SellerUserController.loginout(..))")
    public void verify_login_status(){}


    @Before("verify_login()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(CookieConstant.TOKEN, request);
        if(cookie==null){
            log.warn("【登录校验】 Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        //去redis里面查询
        Object o = myredisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(o==null){
            log.warn("【登录效验】 Redis中查询不到token");
            throw new SellerAuthorizeException();
        }
    }

   @Before("verify_login_status()")
    public void statusVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtil.get(CookieConstant.TOKEN, request);
        if(cookie!=null){
            log.info("【登录效验】 账号登录状态中....");
            throw new SellerStatusExecption();
        }
    }
}
