package com.krxinuo.controller;

import com.krxinuo.aspect.Operation;
import com.krxinuo.entity.TPermissionEntity;
import com.krxinuo.service.PermissionService;
import com.krxinuo.util.DateUtils;
import com.krxinuo.util.response.ResultViewModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(value = "/permissionManage")
public class PermissionManageController {

    @Autowired
    private PermissionService permissionService;


    @RequestMapping(value = "newPermission")
    @ResponseBody
    @Operation(operationType = "add", operationName = "新建权限")
    public Object newPermission(TPermissionEntity entity){

        entity.setCreateTime(DateUtils.formatTime(new Date()));
        permissionService.savePermission(entity);

        return ResultViewModelUtil.success();
    }


    @RequestMapping(value = "getPermissions")
    @ResponseBody
    @Operation(operationType = "query", operationName = "查询权限")
    public Object getPermissions(@RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                           @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit){

        Page<TPermissionEntity> permissions = permissionService.getPermissions(null == page?0:page-1, limit, "asc", "id");
        return ResultViewModelUtil.success(permissions);
    }
}
