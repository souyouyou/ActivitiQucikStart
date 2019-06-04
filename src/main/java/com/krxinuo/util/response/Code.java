package com.krxinuo.util.response;


/**
 * 系统基础参数定义
 */
public enum  Code {

    SUCCESS(0,"success"),
    FAIL(1,"failed"),
    SYS_ERROR(-1,"系统错误");

    private Integer code;
    private String message;

    Code(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //    //状态码
//    public final static String SUCCESS = "000";
//    public final static String FAIL = "001";
//    public final static String NO_LOGIN = "003";
//    public final static String NO_PRIVILEGE = "004";
//
//    //资源状态
//    public final static int DELETE = 0;
//    public final static int ENABLE = 1;
//    public final static int DISABLE = 2;
}
