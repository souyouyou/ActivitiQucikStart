package com.krxinuo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_role")
public class TRoleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //角色标识
    private String role;
    //描述
    private String description;
    //是否可用
    private Boolean enable = Boolean.FALSE;
    //创建时间
    private String createTime;

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(targetEntity = TPermissionEntity.class,cascade = CascadeType.MERGE,fetch= FetchType.EAGER)
    @JoinTable(name="t_role_permission_relation",joinColumns={@JoinColumn(name="roleId",referencedColumnName = "id")},inverseJoinColumns={@JoinColumn(name="permissionId",referencedColumnName = "id")})
    private Set<TPermissionEntity> permissions = new HashSet<>();

//    // 用户 - 角色关系定义;
//    @ManyToMany
//    @JoinTable(name="t_user_role_relation",joinColumns={@JoinColumn(name="roleId",referencedColumnName = "id")},inverseJoinColumns={@JoinColumn(name="userId",referencedColumnName = "id")})
//    private Set<TUserEntity> users = new HashSet<>();// 一个角色对应多个用户


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Set<TPermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<TPermissionEntity> permissions) {
        this.permissions = permissions;
    }

//    public Set<TUserEntity> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<TUserEntity> users) {
//        this.users = users;
//    }
}
