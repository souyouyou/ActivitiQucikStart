package com.krxinuo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    private String id;
    //角色名称
    private String role;
    //描述
    private String description;
    //是否可用
    private Integer enable;
    //权限信息
    private String permissions;
}
