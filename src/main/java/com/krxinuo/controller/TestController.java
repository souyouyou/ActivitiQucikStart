package com.krxinuo.controller;

import com.krxinuo.entity.UserEntity;
import com.krxinuo.service.UserService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserService userService;
    @RequestMapping(value = "test")
    public void test(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId("111");
        userEntity.setUsername("Username");
        userEntity.setNickname("NickName");
        userEntity.setPassword("password");
        userEntity.setEmail("EMail");
        userEntity.setMobile("18646305941");
        UserEntity userEntity1 = userService.saveUser(userEntity);

        System.out.println(userEntity1.getNickname());

    }
}
