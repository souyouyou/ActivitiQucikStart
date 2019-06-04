package com.krxinuo.entity;

import javax.persistence.*;

@Entity
@Table(name = "sys_log")
public class SysLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //操作类型 add/delete/update/query
    private String optype;

    //操作内容
    private String optContent;

    //操作人
    private String optUserId;

    private String optUsername;

    //操作时间
    private String optime;

    //操作结果 成功 失败
    @Lob
    private String optRes;

    //请求参数
    @Lob
    private String apiReq;

    //响应参数
    @Lob
    private String apiRes;

    //操作前
    @Lob
    private String optBefore;

    //操作后
    @Lob
    private String optAfter;

    //操作IP
    private String ip;

    //请求url
    private String url;

    private String method;

    private String exception;

    public String getOptUsername() {
        return optUsername;
    }

    public void setOptUsername(String optUsername) {
        this.optUsername = optUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public String getOptContent() {
        return optContent;
    }

    public void setOptContent(String optContent) {
        this.optContent = optContent;
    }

    public String getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(String optUserId) {
        this.optUserId = optUserId;
    }

    public String getOptime() {
        return optime;
    }

    public void setOptime(String optime) {
        this.optime = optime;
    }

    public String getOptRes() {
        return optRes;
    }

    public void setOptRes(String optRes) {
        this.optRes = optRes;
    }

    public String getApiReq() {
        return apiReq;
    }

    public void setApiReq(String apiReq) {
        this.apiReq = apiReq;
    }

    public String getApiRes() {
        return apiRes;
    }

    public void setApiRes(String apiRes) {
        this.apiRes = apiRes;
    }

    public String getOptBefore() {
        return optBefore;
    }

    public void setOptBefore(String optBefore) {
        this.optBefore = optBefore;
    }

    public String getOptAfter() {
        return optAfter;
    }

    public void setOptAfter(String optAfter) {
        this.optAfter = optAfter;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
