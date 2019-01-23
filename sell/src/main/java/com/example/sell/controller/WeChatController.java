package com.example.sell.controller;

import com.example.sell.config.ProjectUrlConfig;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.from.SellerInfoFrom;
import com.example.sell.service.SellerService;
import com.example.sell.util.FileUtils;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    // mpAppid、mpAppSecret
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //  http://hyzgt.nat300.top/sell/wechat/authorize?returnUrl=http://www.imooc.com
    /**
     * redirect_uri： url
     * state： returnUrl
     * 获取code,跳转到url()，将returnUrl放入state中
     * @param returnUrl
     * @return   /sell/wechat/authorize?returnUrl='http://www.imooc.com?uuid=123'
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam(value = "returnUrl",required = false) String returnUrl,String uuid){
        //http://hyzgt.nat300.top/wechat/userInfo
        String url = projectUrlConfig.getWechatMpAutorizeUrl()+"/sell/wechat/userInfo";
        if(StringUtils.isEmpty(returnUrl)){
            returnUrl = projectUrlConfig.getWechatRqCodeUrl()+"/sell/seller/login?uuid="+uuid;
        }
        String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx718d1594eea0ab8a&redirect_uri=http%3A%2F%2Fhyzgt.nat300.top%2Fwechat%2FuserInfo&response_type=code&scope=snsapi_userinfo&state=http%3A%2F%2Fwww.imooc.com#wechat_redirect
        return "redirect:"+redirectURL;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,@RequestParam("state") String returnURL){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken =new WxMpOAuth2AccessToken();
        try {
            //获取assert_token、openid
             wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信账号方面存在问题】：{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
       // String openId =
       if(returnURL.contains("uuid")){
           return "redirect:"+returnURL+"&openid="+wxMpOAuth2AccessToken.getOpenId();
       }
        return "redirect:"+returnURL+"?openid="+wxMpOAuth2AccessToken.getOpenId();
    }

    // /sell/wechat/upload
    @RequestMapping("/upload")
    public String Upload(){
        return "common/uploadFile";
    }


    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,
                               Map<String,String> map){

            if(file.getSize()>550000){
               throw new SellException(ResultEnum.IMAGE_SIZE_OUT_OF_SCOPE);
            }
            String imagePath = "C:/Users/admin/Pictures/莲花府图片资源/"+file.getOriginalFilename();
            if(FileUtils.upload(file,imagePath)){
                map.put("path","http://localhost:8080/"+file.getOriginalFilename());
            }else{
                map.put("path","图片上传失败");
            }
        return JsonUtil.toJson(map);
    }
}
