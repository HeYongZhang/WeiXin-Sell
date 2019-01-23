package com.example.sell.controller;

import com.example.sell.constant.CookieConstant;
import com.example.sell.constant.RedisConstant;
import com.example.sell.entity.SellerInfo;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.from.SellerInfoFrom;
import com.example.sell.service.SellerService;
import com.example.sell.util.CookieUtil;
import com.example.sell.util.KeyUtil;
import com.example.sell.util.LoginUser;
import com.google.gson.Gson;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private RedisTemplate myredisTemplate;

    @Autowired
    private SellerService sellerService;

     // /生成二维码
    @GetMapping("/index")
    public String login_index(){
        return "rqcode/index";
    }


    // {"openid":"oKgw11l8aYUYSa_OJu-aVu-NxrTs","uuid":"1547448991280859603"}
    // {"openid":"oKgw11l8aYUYSa_OJu-aVu-NxrTs","uuid":"1547449137655484753"}
    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    @ResponseBody
    public String login_verify(@RequestParam String sell,Map<String,String> map){
        SellerInfoFrom sellerInfoFrom = JsonUtil.toObject(sell, SellerInfoFrom.class);
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(sellerInfoFrom.getOpenid());
            if(sellerInfo==null){
                log.warn("【管理员登录】：{}",ResultEnum.USER_VIP_LOGIN_NOT_OPENID.getMessage());
                map.put("msg","当前账户不是管理员账户！");
            }else{
                sellerInfo.setPassword(DigestUtils.md5Hex(sellerInfo.getPassword()));
                //将token包村到redis
                //if(myredisTemplate.opsForValue().setIfAbsent("admin",sellerInfo.getUsername())){
                    myredisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,sellerInfoFrom.getUuid()),sellerInfo,RedisConstant.EXPIRE, TimeUnit.SECONDS);
                    LoginUser.getLoginMap().put(CookieConstant.TOKEN,sellerInfoFrom.getUuid());
                    log.info("【管理员登录】：{}",ResultEnum.USER_VIP_LOGIN_IN_SUCESS.getMessage());
                    map.put("msg","新的一天新的开始，欢迎回家！");
               // }else{
                    //map.put("msg","账号已在其他地方登录");
                    //log.warn("【管理员登录】：{}",ResultEnum.USER_VIP_NOT_ADDRES.getMessage());
                //}
            }
        return JsonUtil.toJson(map);
    }

    // 扫码登录
    @GetMapping("/login")  //
    public ModelAndView login(@RequestParam("openid")String openid,
                      @RequestParam("uuid") String uuid,
                      Map<String,Object> map){
        map.put("openid",openid);
        map.put("uuid",uuid);
        return new ModelAndView("rqcode/verify",map);
    }


    @RequestMapping("/check")
    @ResponseBody
    public String login_check(HttpServletRequest request,HttpServletResponse response,
                        Map<String,Object> objectMap){
        String uuid = request.getParameter("uuid");
        Gson gson = new Gson();
        long inTime = new Date().getTime();
            Boolean bool = true;
            while (bool) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 检测登录
                Object o = LoginUser.getLoginMap().get(CookieConstant.TOKEN);
                if(o!=null){
                    if(o.toString().equals(uuid)){
                        CookieUtil.set(response,CookieConstant.TOKEN,uuid,CookieConstant.EXPIRE);
                        objectMap.put("msg","欢迎回家");
                        objectMap.put("url","http://hyzgt.nat300.top/sell/seller/order/list");
                        bool = false;
                    }
                    LoginUser.getLoginMap().clear();
                }else{
                    long inTime2 =  new Date().getTime();
                    if ( inTime2 - inTime > 60000) {
                        bool = false;
                    }
                    objectMap.put("msg","true");
                    log.info("【token】,检查登录中....");
                }
            }
        return gson.toJson(objectMap);
    }

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(String seller, HttpServletResponse response,Map<String,String> map){
        SellerInfoFrom sellerInfoFrom = JsonUtil.toObject(seller, SellerInfoFrom.class);
        SellerInfo login = sellerService.login(sellerInfoFrom.getUsername(), sellerInfoFrom.getPassword());
        String uuid = null;
        if(login==null){
            map.put("msg","账号或者密码错误！");
        }else{
           // if(myredisTemplate.opsForValue().setIfAbsent("admin",login.getUsername())){
                uuid = KeyUtil.GetUniquekey();
                login.setPassword(DigestUtils.md5Hex(login.getPassword()));
                //将token包村到redis
                myredisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,uuid),login,RedisConstant.EXPIRE, TimeUnit.SECONDS);
                // 将token保存在页面
                CookieUtil.set(response,CookieConstant.TOKEN,uuid,CookieConstant.EXPIRE);
                map.put("msg","新的一天新的开始，欢迎回家");
                map.put("url","http://hyzgt.nat300.top/sell/seller/order/list");
                log.info("【管理员登录】：{}",ResultEnum.USER_VIP_LOGIN_IN_SUCESS.getMessage());
            /*}else{
                map.put("msg","账号已在其他地方登录");
                log.warn("【管理员登录】：{}",ResultEnum.USER_VIP_NOT_ADDRES.getMessage());
            }*/
        }
        return JsonUtil.toJson(map);
    }


    // 退出
    @GetMapping(value = "/logout")
    public ModelAndView loginout(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map){
        Cookie cookie = CookieUtil.get(CookieConstant.TOKEN, request);
        if(cookie!=null){
            //清除redis中的token_cookie
            myredisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //清除页面cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
            //退出登录状态
            myredisTemplate.opsForValue().getOperations().delete("admin");
            log.info("【管理员退出】：{}",ResultEnum.USER_VIP_LOGIN_OUT_SUCESS.getMessage());
        }
        map.put("msg",ResultEnum.USER_VIP_LOGIN_OUT_SUCESS.getMessage());
        map.put("url","/sell/seller/main");
        return new ModelAndView("common/success",map);
    }


    @GetMapping("/main")
    public String login_main(){
        return "seller/login";
    }
}
