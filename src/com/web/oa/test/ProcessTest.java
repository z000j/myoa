package com.web.oa.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:53
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class ProcessTest {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Test
    public void testCreateTable(){
        //在加载spring配置文件时，自动创建了processEngine
        //这个时候就会自动创建 23 张表
    }

    @Test
    public void testProcessDeployment(){
        Deployment deployment = repositoryService.createDeployment()
                .name("报销流程")
                .addClasspathResource("diagram/baoxiaoprocess.bpmn")
                .addClasspathResource("diagram/baoxiaoprocess.png")
                .deploy();
        System.out.println("部署id：" + deployment.getId());
        System.out.println("部署名：" + deployment.getName());
    }

    @Test
    public void testStartProcess(){
        Map<String, Object> map = new HashMap();
        map.put("inputUser", "zhang");
        String key = "baoxiao";
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key, map);
        System.out.println(instance.getId());
        System.out.println(instance.getProcessInstanceId());
    }

    @Test
    public void testViewAgent(){
        String agent = "zhang";
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(agent);
        Task task = taskQuery.singleResult();
        System.out.println("任务id：" + task.getId());
        System.out.println("任务名：" + task.getName());
    }

    @Test
    public void testTaskComplete(){
        String taskId = "903";
        taskService.complete(taskId);
        //这里需要创建监听器类并指定下一任务的代办人
        System.out.println("申请提交成功");
    }

    @Test
    public void testRejectTask(){
        Map<String, Object> map = new HashMap<>();
        map.put("message", "驳回");
        String taskId = "802";
        //第二个参数是为了控制分支的选择
        //绘图时设置了分支判断的变量 message
        //一条分支是 ‘同意’ 一支是 ‘驳回’
        taskService.complete(taskId, map);
        System.out.println("申请驳回成功");
    }

    @Test
    public void testAgreeTask(){
        Map<String, Object> map = new HashMap<>();
        map.put("message", "金额小于等于5000");
        String taskId = "1002";
        taskService.complete(taskId, map);
        System.out.println("申请通过");
    }

    @Test
    public void testFinal(){
        String taskId = "1102";
        taskService.complete(taskId);
        System.out.println("流程结束");
    }

    @Test
    public void testWatchImg() throws IOException {
        String resource = "diagram/baoxiaoprocess.png";
        InputStream resourceAsStream = repositoryService
                .getResourceAsStream("1", resource);
        File file = new File("d:" + File.separator + resource);
        FileUtils.copyInputStreamToFile(resourceAsStream, file);
    }
}
