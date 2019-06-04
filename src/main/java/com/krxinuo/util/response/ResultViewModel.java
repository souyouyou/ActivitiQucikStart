package com.krxinuo.util.response;

public class ResultViewModel<T> {
    private Integer code;
    private String message;
    private T data;
    private T extraData;
    private String redirectUrl;
    private Boolean refresh;

    public Boolean getRefresh() {
        return refresh;
    }

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getExtraData() {
        return extraData;
    }

    public void setExtraData(T extraData) {
        this.extraData = extraData;
    }
}