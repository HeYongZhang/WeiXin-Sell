package com.example.sell.exception;

import com.example.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;


    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String massage) {
        super(massage);
        this.code = code;
    }
}
