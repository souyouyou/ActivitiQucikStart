package com.krxinuo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krxinuo.util.response.Code;
import com.krxinuo.util.response.ResultViewModelUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootWithActivitiApplicationTests {

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	IdentityService identityService;

	@Autowired
	HistoryService historyService;


	/**
	 * 发布流程
	 * 发布流程后，流程文件会保存到数据库中
	 */
	@Test
	public void deployFlow() throws IOException {
		String id = "1";
		Model modelData = repositoryService.getModel(id);
		byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

		if (bytes == null) {
			System.out.println("模型数据为空，请先设计流程并成功保存，再进行发布。");
		}

		JsonNode modelNode = new ObjectMapper().readTree(bytes);

		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		if(model.getProcesses().size()==0){
			System.out.println("数据模型不符要求，请至少设计一条主线流程。");
		}
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

		//发布流程
		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment()
				.name(modelData.getName())
				.addString(processName, new String(bpmnBytes, "UTF-8"))
				.deploy();
	}

	@Test
	public void queryProcdef(){
		//创建查询对象
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		//添加查询条件
		query.processDefinitionKey("leave");//通过key获取
		// .processDefinitionName("My process")//通过name获取
		// .orderByProcessDefinitionId()//根据ID排序
		//执行查询获取流程定义明细
		List<ProcessDefinition> pds = query.list();
		for (ProcessDefinition pd : pds) {
			System.out.println("ID:"+pd.getId()+",NAME:"+pd.getName()+",KEY:"+pd.getKey()+",VERSION:"+pd.getVersion()+",RESOURCE_NAME:"+pd.getResourceName()+",DGRM_RESOURCE_NAME:"+pd.getDiagramResourceName());
		}
	}

	/**
	 * 发布流程
	 */
	@Test
	public void startFlow(){

		RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
		/**
		 * 启动请假单流程  并获取流程实例
		 * 因为该请假单流程可以会启动多个所以每启动一个请假单流程都会在数据库中插入一条新版本的流程数据
		 * 通过key启动的流程就是当前key下最新版本的流程
		 *
		 */


		identityService.setAuthenticatedUserId("songyy");
		ProcessInstance processInstance = runtimeService.startProcessInstanceById("leave:3:27524");
		System.out.println("id:"+processInstance.getId()+",activitiId:"+processInstance.getActivityId());
	}


	/**
	 * 查看任务
	 */
	@Test
	public void queryTaskUser(){
		//获取任务服务对象
		TaskService taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();
		//根据接受人获取该用户的任务
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateGroup("userGroup")
				.list();
		for (Task task : tasks) {
			System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());
		}
	}


	@Test
	public void startTaskUser(){
		TaskService taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();
		//taskId 就是查询任务中的 ID
		String taskId = "5004";
		//完成请假申请任务
		taskService.complete(taskId );
	}

	/**
	 * 查看任务
	 */
	@Test
	public void queryTaskBoss(){
		//获取任务服务对象
		TaskService taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();
		//根据接受人获取该用户的任务
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateGroup("bossGroup")
				.list();
		for (Task task : tasks) {
			System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());
		}
	}

	@Test
	public void startTaskBoss(){
		TaskService taskService = ProcessEngines.getDefaultProcessEngine().getTaskService();
		//taskId 就是查询任务中的 ID
		String taskId = "7502";
		//完成请假申请任务
		taskService.complete(taskId );
	}


	@Test
	public void myTasks(){
		List<HistoricProcessInstance> hisProInstance = historyService.createHistoricProcessInstanceQuery()
//				.processDefinitionKey("leave").startedBy("songyy")
				.orderByProcessInstanceEndTime().desc().list();

		System.out.println(111);
	}
}
