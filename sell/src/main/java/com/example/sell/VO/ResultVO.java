package com.example.sell.VO;

import lombok.Data;

@Data
public class ResultVO<T> {

    /** 错误码 */
    private Integer code;

    /** 提示消息 */
    private String msg;

    /** 数据（泛型）*/
    private T data;
}
