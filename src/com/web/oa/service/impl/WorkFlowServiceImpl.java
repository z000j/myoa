package com.web.oa.service.impl;

import com.github.pagehelper.PageHelper;
import com.web.oa.mapper.BaoxiaobillMapper;
import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.pojo.BaoxiaobillExample;
import com.web.oa.pojo.User;
import com.web.oa.service.WorkFlowService;
import com.web.oa.utils.Constant;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:48
 * @version 1.0
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BaoxiaobillMapper baoxiaobillMapper;

    @Override
    public void deploymentProcess(String processName, InputStream is) {
        ZipInputStream zipInputStream = new ZipInputStream(is);
        repositoryService.createDeployment()
                         .name(processName)
                         .addZipInputStream(zipInputStream)
                         .deploy();
    }

    @Override
    public List<Deployment> getDeploymentDefineds() {
        return repositoryService.createDeploymentQuery().list();
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitions() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

    @Override
    public void startProcessInstance(Baoxiaobill baoxiaobill, User user) {
        //存储报销单
        baoxiaobillMapper.insert(baoxiaobill);
        //拼凑businessKey
        String name = user.getName();
        Integer id = baoxiaobill.getId();
        String businessKey = Constant.PROCESS_KEY + "." + id;
        Map map = new HashMap();
        map.put("inputUser", name);
        //最后一个参数指定任务的代理人
        runtimeService.startProcessInstanceByKey(Constant.PROCESS_KEY, businessKey, map);
    }

    @Override
    public void deleteDeployment(String deploymentID) {
        repositoryService.deleteDeployment(deploymentID,true);
    }

    @Override
    public List<Task> findTaskListByTaskName(String taskName) {
        return taskService.createTaskQuery().taskAssignee(taskName).list();
    }

    @Override
    public Baoxiaobill findBaoXiaoBillByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String businessKey = instance.getBusinessKey();
        String billID = businessKey.split("\\.")[1];
        Baoxiaobill baoxiaobill = baoxiaobillMapper.selectByPrimaryKey(Integer.parseInt(billID));
        return baoxiaobill;
    }

    @Override
    public List<Comment> findCommentByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        return taskService.getProcessInstanceComments(processInstanceId);
    }

    @Override
    public void completeTask(String taskId, String comment,
                             Integer id, String userId, String submit) {
        Authentication.setAuthenticatedUserId(userId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        taskService.addComment(taskId,processInstanceId,comment);
        if("提交".equals(submit)) {
            taskService.complete(taskId);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("message", submit);
            taskService.complete(taskId, map);
        }
        ProcessInstance instance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if(instance == null){
            Baoxiaobill baoxiaobill =  baoxiaobillMapper.selectByPrimaryKey(id);
            baoxiaobill.setState(2);
            baoxiaobillMapper.updateByPrimaryKey(baoxiaobill);
        }
    }

    @Override
    public InputStream findProcessImag(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId,imageName);
    }

    @Override
    public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //查询流程定义的对象
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef
                .processDefinitionId(processDefinitionId)//使用流程定义ID查询
                .singleResult();
        return pd;
    }

    @Override
    public Map<String, Object> findCoordingByTask(String taskId) {
        //存放坐标
        Map<String, Object> map = new HashMap<String,Object>();
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //获取流程定义的ID
        String processDefinitionId = task.getProcessDefinitionId();
        //获取流程定义的实体对象（对应.bpmn文件中的数据）
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
        //流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        //获取当前活动的ID
        String activityId = pi.getActivityId();
        //获取当前活动对象
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
        //获取坐标
        map.put("x", activityImpl.getX());
        map.put("y", activityImpl.getY());
        map.put("width", activityImpl.getWidth());
        map.put("height", activityImpl.getHeight());
        return map;
    }

    @Override
    public List<Baoxiaobill> findBaoXiaoBillByUserId(Long id) {
        BaoxiaobillExample example = new BaoxiaobillExample();
        BaoxiaobillExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(id.intValue());
        List<Baoxiaobill> baoxiaobills = baoxiaobillMapper.selectByExample(example);
        return baoxiaobills;
    }

    @Override
    public void deleteBaoXiaoBillById(String id) {
        baoxiaobillMapper.deleteByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public List<Comment> findHisCommentByBaoXiaoBillId(String id) {
        String businessKey = "baoxiao" + "." + id;
        HistoricProcessInstance baoxiao = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("baoxiao")
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        String processId = baoxiao.getId();
        List<Comment> comments = taskService.getProcessInstanceComments(processId);
        return comments;
    }

    @Override
    public Baoxiaobill findBaoxiaoBillById(String id) {
        return baoxiaobillMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public String findTaskIdByBillId(String billId) {
        String businessKey = "baoxiao" + "." + billId;
        return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult().getId();
    }
}
