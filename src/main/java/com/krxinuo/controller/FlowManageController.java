package com.krxinuo.controller;

import com.krxinuo.aspect.Operation;
import com.krxinuo.bean.AuditTask;
import com.krxinuo.util.DateUtils;
import com.krxinuo.util.response.ResultViewModelUtil;
import net.sf.json.JSONObject;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/flowManage")
public class FlowManageController {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    IdentityService identityService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;


    @RequestMapping("flows")
    @ResponseBody
    @Operation(operationType = "query", operationName = "查询启动流程实例")
    public Object flow(@RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                       @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit){
        page = page - 1;
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
//				.processDefinitionKey("leave").startedBy("songyy")
                .orderByProcessInstanceEndTime().desc().listPage(page,limit);
        int count = historyService.createHistoricProcessInstanceQuery().list().size();

        JSONObject extraData = new JSONObject();
        extraData.put("totalRows",count);

        return ResultViewModelUtil.success(historicProcessInstances,extraData);
    }


    @RequestMapping("leaveApply")
    @ResponseBody
    @Operation(operationType = "create", operationName = "请假申请")
    public Object leaveApply(String username, String reason, Integer days){


        RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        /**
         * 启动请假单流程  并获取流程实例
         * 因为该请假单流程可以会启动多个所以每启动一个请假单流程都会在数据库中插入一条新版本的流程数据
         * 通过key启动的流程就是当前key下最新版本的流程
         *
         */


        identityService.setAuthenticatedUserId(username);
        //ID  2501
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leaveModel");
        System.out.println(processInstance.getId());
        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        // 申明任务
//        taskService.claim(currentTask.getId(), username);

        Map<String, Object> vars = new HashMap<>(4);
        vars.put("applyUser", username);
        vars.put("days", days);
        vars.put("reason", reason);
        // 完成任务
//        taskService.complete(currentTask.getId(), vars);


        return ResultViewModelUtil.success();
    }



    @RequestMapping("auditTask")
    @ResponseBody
    @Operation(operationType = "query", operationName = "任务查询")
    public Object myAudit(String username,
                          @RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                          @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit){
        username = "boss";
        page = page - 1;
        int count = taskService.createTaskQuery().taskCandidateOrAssigned(username).list().size();
        List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned(username).listPage(page,limit);

        List<AuditTask> auditTaskList = new ArrayList<>();

        for (Task task:taskList) {
            AuditTask auditTask = new AuditTask();
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String reason = runtimeService.getVariable(processInstance.getId(), "reason", String.class);
            Integer days = runtimeService.getVariable(processInstance.getId(), "days", Integer.class);

            auditTask.setCreateTime(DateUtils.formatTime(task.getCreateTime()));
            auditTask.setReason(reason);
            auditTask.setDays(days);
            auditTask.setName(task.getName());
            auditTask.setApplyStatus(processInstance.isEnded());

            auditTaskList.add(auditTask);

        }


        JSONObject extraData = new JSONObject();
        extraData.put("totalRows",count);
        return ResultViewModelUtil.success(auditTaskList,extraData);
    }



}
