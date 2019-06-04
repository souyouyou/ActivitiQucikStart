package com.krxinuo.controller;

import com.krxinuo.entity.TUserEntity;
import com.krxinuo.service.UserService;
import org.activiti.engine.IdentityService;
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
        TUserEntity TUserEntity = new TUserEntity();
        TUserEntity.setUsername("Username");
        TUserEntity.setNickname("NickName");
        TUserEntity.setPassword("password");
        TUserEntity.setEmail("EMail");
        TUserEntity.setMobile("18646305941");
        TUserEntity TUserEntity1 = userService.saveUser(TUserEntity);


        System.out.println(TUserEntity1.getNickname());

    }
}
