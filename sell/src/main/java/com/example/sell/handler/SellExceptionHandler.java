package com.example.sell.handler;

import com.example.sell.VO.ResultVO;
import com.example.sell.config.ProjectUrlConfig;
import com.example.sell.exception.ResponseBankException;
import com.example.sell.exception.SellException;
import com.example.sell.exception.SellerAuthorizeException;
import com.example.sell.exception.SellerStatusExecption;
import com.example.sell.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    //@ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException(){

        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/main"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseBankException(ResponseBankException e){

    }

    @ExceptionHandler(value = SellerStatusExecption.class)
    public ModelAndView handlerSellerStatusExecption(){
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/order/list"));
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Map<String,String> handlerEverOne(RuntimeException e){
        Map<String,String> map = new HashMap<>();
        map.put("msg","未知错误，等待维护");
        map.put("url","http://hyzgt.nat300.top");
        log.error("【未知错误】{}",e);
        map.put("错误位置",String.valueOf(e.getClass().getResource("/")));
        return map;
    }
}
