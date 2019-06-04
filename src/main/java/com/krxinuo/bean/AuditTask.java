package com.krxinuo.bean;

public class AuditTask {
    private String name;
    private String createTime;
    private String applyUser;
    private String applyTime;
    private Integer days;
    private String reason;
    private Boolean applyStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Boolean applyStatus) {
        this.applyStatus = applyStatus;
    }
}
