package com.krxinuo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRoleEntity {
    @Id
    private Integer id;
    //用户id
    private String userId;
    //角色id
    private String roleId;
}
