package com.web.oa.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.pojo.User;
import com.web.oa.service.WorkFlowService;
import com.web.oa.utils.Constant;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    private static final int PAGE_SIZE = 5;

    @RequestMapping("/deployProcess")
    public String deployProcess(MultipartFile processFile, String processName){
        //1. 获取文件和文件名
        //2. 调用service层部署流程
        //3. 跳转到查看流程

        try {
            workFlowService.deploymentProcess(processName, processFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/watchProcess";
    }

    @RequestMapping("/watchProcess")
    public ModelAndView watchProcess(){
        //查看流程
        //得到部署定义的信息
        //得到流程定义的信息
        //返回给 jsp
        ModelAndView mv = new ModelAndView();
        List<Deployment> deploymentDefineds = workFlowService.getDeploymentDefineds();
        List<ProcessDefinition> processDefinitions = workFlowService.getProcessDefinitions();
        mv.addObject("depList",deploymentDefineds);
        mv.addObject("pdList",processDefinitions);
        mv.setViewName("workflow_list");
        return mv;
    }

    @RequestMapping("/delDeployment")
    public String delDeployment(String deploymentId){
        //删除部署定义
        workFlowService.deleteDeployment(deploymentId);
        return "redirect:/watchProcess";
    }

    @RequestMapping("/processDefinitionList")
    public String processDefinitionList(){
        //查看流程
        return "redirect:/watchProcess";
    }

    @RequestMapping("/saveStartBaoxiao")
    public String saveStartLeave(Baoxiaobill baoxiaobill, HttpSession session){
        //保存报销单
        //得到当前用户
        //初始化报销单的数据
        //开启流程实例
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        baoxiaobill.setState(1);
        baoxiaobill.setCreatdate(new Date());
        baoxiaobill.setUserId(user.getId().intValue());
        workFlowService.startProcessInstance(baoxiaobill,user);
        return "redirect:/myTaskList";
    }

    @RequestMapping("/myTaskList")
    public ModelAndView myTaskList(HttpSession session){
        //通过用户名查找任务
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        String taskName = user.getName();
        List<Task> taskList = workFlowService.findTaskListByTaskName(taskName);
        ModelAndView mv = new ModelAndView();
        mv.addObject("taskList", taskList);
        mv.setViewName("workflow_task");
        return mv;
    }

    @RequestMapping("/viewTaskForm")
    public ModelAndView viewTaskForm(String taskId){
        Baoxiaobill baoxiaobill = workFlowService.findBaoXiaoBillByTaskId(taskId);
        List<Comment> commentList = workFlowService.findCommentByTaskId(taskId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("bill", baoxiaobill);
        mv.addObject("commentList", commentList);
        mv.addObject("taskId",taskId);
        mv.setViewName("approve_baoxiao");
        return mv;
    }

    @RequestMapping("/submitTask")
    public String submitTask(String comment, String taskId,
                             Integer id, HttpSession session, String submit){
        User user = (User) session.getAttribute(Constant.USER_SESSION);
        workFlowService.completeTask(taskId, comment, id, user.getName(), submit);
        return "redirect:/myTaskList";
    }

    @RequestMapping("/viewImage")
    public String viewImage(String deploymentId, String imageName, HttpServletResponse response) throws IOException {
        InputStream processImag = workFlowService.findProcessImag(deploymentId, imageName);
        ServletOutputStream outputStream = response.getOutputStream();

        for(int b = -1;(b=processImag.read()) != -1;){
            outputStream.write(b);
        }
        processImag.close();
        outputStream.close();
        return null;
    }

    @RequestMapping("/viewCurrentImage")
    public ModelAndView  viewCurrentImage(String taskId) {
        /**一：查看流程图*/
        //1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
        ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(taskId);

        ModelAndView mv = new ModelAndView();
        mv.addObject("deploymentId", pd.getDeploymentId());
        mv.addObject("imageName", pd.getDiagramResourceName());
        /**
         * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
        Map<String, Object> map = workFlowService.findCoordingByTask(taskId);

        mv.addObject("acs", map);
        mv.setViewName("viewimage");
        return mv;
    }

    @RequestMapping("/myBaoxiaoBill")
    public ModelAndView myBaoxiaoBill(HttpSession session,
                                      @RequestParam(required = false,defaultValue = "1") int pageNum){
        User user = (User)session.getAttribute(Constant.USER_SESSION);
        //分页拦截
        PageHelper.startPage(pageNum,PAGE_SIZE);
        List<Baoxiaobill> baoxiaobillList = workFlowService.findBaoXiaoBillByUserId(user.getId());
        PageInfo pageInfo = new PageInfo<>(baoxiaobillList);
        ModelAndView mv = new ModelAndView();
        mv.addObject("baoxiaobillList", baoxiaobillList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("baoxiaobill");
        return mv;
    }

    @RequestMapping("/baoxiaobillAction_delete")
    public String baoxiaobillAction_delete(String id){
        workFlowService.deleteBaoXiaoBillById(id);
        return "redirect:/myBaoxiaoBill";
    }

    @RequestMapping("/viewHisComment")
    public ModelAndView viewHisComment(String id){
       List<Comment> comments =  workFlowService.findHisCommentByBaoXiaoBillId(id);
       Baoxiaobill baoxiaobill = workFlowService.findBaoxiaoBillById(id);
       ModelAndView mv = new ModelAndView();
       mv.addObject("bill", baoxiaobill);
       mv.addObject("commentList", comments);
       mv.setViewName("baoxiaoRecord");
       return mv;
    }

    @RequestMapping("/viewCurrentImageByBill")
    public ModelAndView viewCurrentImageByBill(String billId){
        String taskId = workFlowService.findTaskIdByBillId(billId);
        /**一：查看流程图*/
        //1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
        ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(taskId);

        ModelAndView mv = new ModelAndView();
        mv.addObject("deploymentId", pd.getDeploymentId());
        mv.addObject("imageName", pd.getDiagramResourceName());
        /**
         * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
        Map<String, Object> map = workFlowService.findCoordingByTask(taskId);

        mv.addObject("acs", map);
        mv.setViewName("viewimage");
        return mv;
    }
}
