package com.web.oa.utils;

import com.web.oa.pojo.User;
import com.web.oa.service.UserService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:50
 * @version 1.0
 */
public class ManagerTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask task) {
        //使用 SpringMVC 的 api 得到 ioc 容器
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        UserService userService = (UserService) context.getBean("userService");

        //获取 request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取 session 中的用户
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Constant.USER_SESSION);

        User maneger = userService.findUserById(user.getManagerId());
        //设置下一任务的代办人
        task.setAssignee(maneger.getName());
    }
}
