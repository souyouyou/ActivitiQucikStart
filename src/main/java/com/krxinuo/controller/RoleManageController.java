package com.krxinuo.controller;

import com.krxinuo.aspect.Operation;
import com.krxinuo.entity.TPermissionEntity;
import com.krxinuo.entity.TRoleEntity;
import com.krxinuo.service.PermissionService;
import com.krxinuo.service.RoleService;
import com.krxinuo.util.DateUtils;
import com.krxinuo.util.response.ResultViewModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *  角色信息管理
 */
@Controller
@RequestMapping(value = "/roleManage")
public class RoleManageController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "newRole")
    @ResponseBody
    @Operation(operationType = "add", operationName = "新建角色")
    public Object newRole(TRoleEntity role){

        role.setCreateTime(DateUtils.formatTime(new Date()));
        roleService.saveRole(role);

        return ResultViewModelUtil.success();
    }


    @RequestMapping(value = "getRoles")
    @ResponseBody
    @Operation(operationType = "query", operationName = "查询角色")
    public Object getRoles(@RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                           @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit){

        Page<TRoleEntity> roles = roleService.getRoles(null == page?0:page-1, limit, "asc", "id");
        return ResultViewModelUtil.success(roles);
    }

    /**
     * 为用户角色分配权限
     * @param rId
     * @param pIds
     * @return
     */
    @RequestMapping(value = "assignPermissionForRole")
    @ResponseBody
    @Operation(operationType = "add", operationName = "分配权限")
    public Object assignPermissionForRole(Long rId, Long[] pIds){

        TRoleEntity role = roleService.getRoleById(rId);


        Set<TPermissionEntity> rolePermissions = new HashSet<>();

        for (Long pid:pIds) {
            rolePermissions.add(permissionService.getPermissionById(pid));
        }

        role.setPermissions(rolePermissions);
        roleService.saveRole(role);

        return ResultViewModelUtil.success();
    }


    /**
     * 根据角色编号查询已分配的权限
     * @param rId
     * @return
     */
    @RequestMapping(value = "getPermissionByRid")
    @ResponseBody
    @Operation(operationType = "query", operationName = "查询权限")
    public Object getPermissionByRid(Long rId){

        TRoleEntity role = roleService.getRoleById(rId);
        Set<TPermissionEntity> rolePermissions = role.getPermissions();

        return ResultViewModelUtil.success(rolePermissions);
    }

}
