package com.krxinuo.controller;

import com.krxinuo.aspect.Operation;
import com.krxinuo.entity.SysLogEntity;
import com.krxinuo.entity.TPermissionEntity;
import com.krxinuo.service.PermissionService;
import com.krxinuo.service.SysLogService;
import com.krxinuo.util.DateUtils;
import com.krxinuo.util.response.ResultViewModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(value = "/sysLogManage")
public class SysLogManageController {

    @Autowired
    private SysLogService sysLogService;



    @RequestMapping(value = "getLogs")
    @ResponseBody
    @Operation(operationType = "query", operationName = "查询日志")
    public Object getLogs(@RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                          @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit,
                          @ModelAttribute(value = "sysEntity")SysLogEntity entity){

        Page<SysLogEntity> logs = sysLogService.getConditionLogs(entity,null == page ? 0 : page - 1, limit, "asc", "id");
        return ResultViewModelUtil.success(logs);
    }
}
