package com.example.sell.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 3938626101358237007L;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 数据（泛型）
     */
    private T data;
}
