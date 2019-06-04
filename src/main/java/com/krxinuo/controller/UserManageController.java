package com.krxinuo.controller;

import com.krxinuo.aspect.Operation;
import com.krxinuo.entity.TRoleEntity;
import com.krxinuo.entity.TUserEntity;
import com.krxinuo.service.RoleService;
import com.krxinuo.service.UserService;
import com.krxinuo.util.DateUtils;
import com.krxinuo.util.response.ResultViewModel;
import com.krxinuo.util.response.ResultViewModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *  用户信息管理
 */
@Controller
@RequestMapping(value = "/userManage")
public class UserManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 新建用户
     * @param user
     * @return
     */
    @RequestMapping(value = "newUser")
    @ResponseBody
    @Operation(operationType = "add", operationName = "新建用户")
    public Object ceateUser(TUserEntity user){

        user.setCreateTime(DateUtils.formatTime(new Date()));
        userService.saveUser(user);

        return ResultViewModelUtil.success();
    }

    /**
     * 用户列表-分页
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "getUsers")
    @ResponseBody
    @Operation(operationType = "query", operationName = "查询用户")
    public Object getUsers(@RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                           @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit){

        Page<TUserEntity> users = userService.getUsers(null == page?0:page-1, limit, "asc", "id");
//        throw new RuntimeException("");
        return ResultViewModelUtil.success(users);
    }

    @RequestMapping(value = "getUserList")
    @Operation(operationType = "query", operationName = "查询用户-无分页")
    public String getUserList(Model model){
        model.addAttribute("users",userService.getUserList());
        return "system/log-manage::user-fresh";
    }


    @RequestMapping(value = "getRolesByUid")
    @ResponseBody
    @Operation(operationType = "query", operationName = "查询角色")
    public Object getRolesByUid(Long uid){

        TUserEntity user = userService.getUserById(uid);
        Set<TRoleEntity> userRoles = user.getRoles();

        return ResultViewModelUtil.success(userRoles);
    }

    /**
     * 为用户分配角色
     * @param uId
     * @param rIds
     * @return
     */
    @RequestMapping(value = "assignRoleForUser")
    @ResponseBody
    @Operation(operationType = "add", operationName = "分配角色")
    public Object assignRole(Long uId, Long[] rIds){

        TUserEntity user = userService.getUserById(uId);
        Set<TRoleEntity> userRoles = new HashSet<>();

        for (Long rId:rIds) {
            TRoleEntity role = roleService.getRoleById(rId);
            userRoles.add(role);
        }

        user.setRoles(userRoles);
        userService.saveUser(user);

        return ResultViewModelUtil.success();
    }


}
