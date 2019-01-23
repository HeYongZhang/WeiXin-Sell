package com.example.sell.servlet;

import com.example.sell.config.ProjectUrlConfig;
import com.example.sell.rqcode.TwoDimensionCode;
import com.example.sell.util.KeyUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String,Object> map = new HashMap<>();
        PrintWriter out = response.getWriter();
        //生成uuid(验证是需要用到)
        String uuid = KeyUtil.GetUniquekey();
        //生成二维码：
            //二维码内容,我填写的是验证地址（登录页面）+uuid
        String content = "http://hyzgt.nat300.top/sell/wechat/authorize?returnUrl=&uuid="+uuid;
        String imgName = "image_"+uuid+".png";

        String imgPath = request.getServletContext().getRealPath("rqcode/")+ imgName;

        System.out.println(imgPath);

        TwoDimensionCode handler = new TwoDimensionCode();
        handler.encoderQRCode(content, imgPath, "png");

        // 生成的图片访问地址
        String qrCodeImg = "http://hyzgt.nat300.top/sell/rqcode/" + imgName;
        map.put("uuid",uuid);
        map.put("qrCodeImg",qrCodeImg);
        Gson gson = new Gson();
        out.print(gson.toJson(map));
        out.flush();
        out.close();
    }
}
