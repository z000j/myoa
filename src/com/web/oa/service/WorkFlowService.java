package com.web.oa.service;

import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.pojo.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface WorkFlowService {
    //部署流程
    void deploymentProcess(String processName, InputStream is);

    List<Deployment> getDeploymentDefineds();

    List<ProcessDefinition> getProcessDefinitions();

    void startProcessInstance(Baoxiaobill baoxiaobill, User user);

    void deleteDeployment(String DeploymentID);

    List<Task> findTaskListByTaskName(String taskName);


    Baoxiaobill findBaoXiaoBillByTaskId(String taskId);

    List<Comment> findCommentByTaskId(String taskId);

    void completeTask(String taskId, String comment, Integer id, String name, String submit);

    InputStream findProcessImag(String deploymentId, String imageName);

    ProcessDefinition findProcessDefinitionByTaskId(String taskId);

    Map<String, Object> findCoordingByTask(String taskId);

    List<Baoxiaobill> findBaoXiaoBillByUserId(Long id);

    void deleteBaoXiaoBillById(String id);

    List<Comment> findHisCommentByBaoXiaoBillId(String id);

    Baoxiaobill findBaoxiaoBillById(String id);

    String findTaskIdByBillId(String billId);

}
