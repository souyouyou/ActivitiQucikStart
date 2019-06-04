package com.krxinuo.util.exception;

/**
 * 自定义异常
 */
public class BaseException extends RuntimeException{
    private Integer code;//状态码

    public BaseException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
