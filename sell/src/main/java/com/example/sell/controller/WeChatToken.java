package com.example.sell.controller;

import com.example.sell.util.CheckoutUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/wechat/token")
@Slf4j
public class WeChatToken {

    /**
     * 微信消息接收和token验证
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/hello")
    public void hello(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/index")
    public void index(@RequestParam("code") String code, @RequestParam("state")String state){
        System.out.println("获取code........"+code);
        System.out.println("获取state........"+code);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx718d1594eea0ab8a&secret=21c27704ef6da226a5e4539e56121414&code="+code+"&grant_type=authorization_code";
        String response = restTemplate.getForObject(url, String.class);
        log.info("【response】：{}",response);
    }

}
