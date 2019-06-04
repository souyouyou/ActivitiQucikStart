package com.krxinuo.controller;

import com.krxinuo.util.ActUtils;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/first")
public class FirstController {


    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    ProcessEngineFactoryBean processEngine;
    @Autowired
    HistoryService historyService;
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/test1")
    public void test1(HttpServletResponse response) throws Exception {

        ActUtils.getFlowImgByInstanceId("7501",repositoryService,managementService,runtimeService,processEngineConfiguration,processEngine,historyService,taskService,"leave:2:2504",response.getOutputStream());
    }

}