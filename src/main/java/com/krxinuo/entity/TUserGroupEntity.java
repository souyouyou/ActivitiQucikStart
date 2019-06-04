package com.krxinuo.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_user_group")
public class TUserGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //用户组名称
    private String groupName;
    //创建时间
    private String createTime;
    //描述信息
    private String description;
    //
    //
}
