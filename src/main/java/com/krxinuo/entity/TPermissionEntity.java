package com.krxinuo.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_permission")
public class TPermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private String permission;

    //描述信息
    private String description;

    //是否可用
    private Boolean available = Boolean.FALSE;

    //创建时间
    private String createTime;

    @Column(columnDefinition="enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]

    private String url;//资源路径.

    private Long parentId; //父编号

    private String parentIds; //父编号列表
//
//    @ManyToMany
//    @JoinTable(name="t_role_permission_relation",joinColumns={@JoinColumn(name="permissionId", referencedColumnName = "id")},inverseJoinColumns={@JoinColumn(name="roleId", referencedColumnName = "id")})
//    private Set<TRoleEntity> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

//    public Set<TRoleEntity> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<TRoleEntity> roles) {
//        this.roles = roles;
//    }
}
