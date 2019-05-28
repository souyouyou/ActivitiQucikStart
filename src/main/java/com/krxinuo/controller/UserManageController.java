package com.krxinuo.controller;

import com.krxinuo.entity.UserEntity;
import com.krxinuo.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  用户信息管理
 */
@Controller
@RequestMapping(value = "/userManage")
public class UserManageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "newUser")
    public void ceateUser(){


    }


    @RequestMapping(value = "user-list")
    @ResponseBody
    public String getUsers(){
        JSONObject result = new JSONObject();

        Page<UserEntity> users = userService.getUsers(0, 10, "asc", "id");
        result.put("msg","success");
        result.put("status","200");
        result.put("data",users);
        System.out.println("总计："+users.getTotalElements());

        System.out.println(result.toString());
        return result.toString();
    }

}
