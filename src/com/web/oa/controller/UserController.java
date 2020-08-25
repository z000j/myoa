package com.web.oa.controller;

import com.web.oa.exception.CustomException;
import com.web.oa.pojo.Result;
import com.web.oa.pojo.Role;
import com.web.oa.pojo.TreeMenu;
import com.web.oa.pojo.User;
import com.web.oa.service.PermissionService;
import com.web.oa.service.RoleService;
import com.web.oa.service.UserRoleMapService;
import com.web.oa.service.UserService;
import com.web.oa.utils.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:48
 * @version 1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleMapService userRoleMapService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request) throws CustomException {
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if(exception != null){
            if(UnknownAccountException.class.getName().equals(exception)){
                throw new CustomException("账号错误");
            }
            if(IncorrectCredentialsException.class.getName().equals(exception)){
                throw new CustomException("密码错误");
            }
            if("validateCodeError".equals(exception)){
                throw new CustomException("验证码错误");
            }
        }
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model, HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        session.setAttribute(Constant.USER_SESSION, user);
        List<TreeMenu> menuList = permissionService.findMenuList();
        model.addAttribute("menuTree", menuList);
        return "index";
    }

    @RequestMapping("/refuse")
    public String refuse(){
        return "refuse";
    }

    @RequestMapping("/validateCode")
    public String validateCode(){
        return "validateCode";
    }

    @RequestMapping("/findUserList")
    public String findUserList(Model model){
        List<User> userList = userService.findAllUserContainLeader();
        List<Role> allRoles = roleService.findAllRoles();
        model.addAttribute("userList", userList);
        model.addAttribute("allRoles", allRoles);
        return "userlist";
    }

    @RequestMapping("/viewPermissionByUser")
    @ResponseBody
    public Role viewPermissionByUser(String userName){
        //查找角色名 和 权限
        return roleService.findRoleAndPermission(userName);
    }

    @RequestMapping("/assignRole")
    @ResponseBody
    public Result assignRole(String roleId, String userId){
        int count1 = userService.updateUserRole(roleId,userId);
        int count2 = userRoleMapService.updateRoleIDByUserId(userId,roleId);
        Result result = new Result();
        if(count1 > 0 && count2 > 0) {
            result.setMsg("修改成功");
        }else {
            result.setMsg("修改失败");
        }
        return result;
    }

    @RequestMapping("/findNextManager")
    @ResponseBody
    public List<User> findNextManager(String level){
        List<User> userByRole = userService.findUserByRole(level);
        return userByRole;
    }

    @RequestMapping("/saveUser")
    public String saveUser(User user){
        int i = userService.AddUserOrUpdate(user);
        userRoleMapService.addUserRole(String.valueOf(user.getId()),
                String.valueOf(user.getRole()));
        if(i>0) {
            System.out.println("成功");
        }
        return "redirect:/findUserList";
    }
}
