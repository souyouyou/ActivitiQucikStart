package com.krxinuo.activiti.controller;

import com.krxinuo.activiti.vo.DeploymentResponse;
import com.krxinuo.common.RestServiceController;
import com.krxinuo.util.response.ResultViewModelUtil;
import net.sf.json.JSONObject;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("deployments")
public class DeploymentController implements RestServiceController<Deployment, String>{
    @Autowired
    RepositoryService repositoryService;

    @Override
    public Object getOne(@PathVariable("id") String id) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
//        return ToWeb.buildResult().setObjData(new DeploymentResponse(deployment));
        return null;
    }

    @Override
    public Object getList(@RequestParam(value = "limit", defaultValue = "10", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<Deployment> deployments = repositoryService.createDeploymentQuery()
                .listPage(rowSize * (page - 1), rowSize);
        long count = repositoryService.createDeploymentQuery().count();
        List<DeploymentResponse> list = new ArrayList<>();
        for(Deployment deployment: deployments){
            list.add(new DeploymentResponse(deployment));
        }

        JSONObject extraData = new JSONObject();
        extraData.put("totalRows",count);

        return ResultViewModelUtil.success(list,extraData);    }

    @Override
    public Object deleteOne(@PathVariable("id") String id) {
        repositoryService.deleteDeployment(id);
        return ResultViewModelUtil.refresh();
    }

    @Override
    public Object postOne(@RequestBody Deployment entity) {
        return null;
    }

    @Override
    public Object putOne(@PathVariable("id") String s, @RequestBody Deployment entity) {
        return null;
    }

    @Override
    public Object patchOne(@PathVariable("id") String s, @RequestBody Deployment entity) {
        return null;
    }
}
