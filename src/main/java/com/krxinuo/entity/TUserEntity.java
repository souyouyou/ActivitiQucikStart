package com.krxinuo.entity;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_user")
@EntityListeners(AuditingEntityListener.class)
public class TUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //用户编号
    private Long id;
    //用户昵称，用于在界面显示
    private String nickname;
    //用户名
    @Column(unique =true)
    private String username;
    //密码
    private String password;
    //加密盐值
    private String salt;
    //手机号码
    private String mobile;
    //电子邮箱
    private String email;
    //创建时间
    private String createTime;
    //登录时间
    private String loginTime;
    //最后登录时间
    private String lastLoginTime;
    //登录次数
    private Long loginCounts;
    //状态信息
    private Boolean available = Boolean.FALSE;

    @LastModifiedDate
    private Date lastUpdateTime;

    @ManyToMany(targetEntity = TRoleEntity.class,cascade = CascadeType.MERGE,fetch= FetchType.EAGER)
    @JoinTable(name = "t_user_role_relation", joinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") }, inverseJoinColumns ={@JoinColumn(name = "roleId", referencedColumnName = "id") })
    private Set<TRoleEntity> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getLoginCounts() {
        return loginCounts;
    }

    public void setLoginCounts(Long loginCounts) {
        this.loginCounts = loginCounts;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<TRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<TRoleEntity> roles) {
        this.roles = roles;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
