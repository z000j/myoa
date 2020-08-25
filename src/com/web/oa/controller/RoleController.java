package com.web.oa.controller;

import com.web.oa.pojo.Permission;
import com.web.oa.pojo.Role;
import com.web.oa.pojo.TreeMenu;
import com.web.oa.service.PermissionService;
import com.web.oa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:48
 * @version 1.0
 */
@Controller
public class RoleController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/toAddRole")
    public String toAddRole(Model model){
        //拿到所有权限包括菜单
        List<TreeMenu> menuList = permissionService.findAllPermission();
        //拿到菜单和其一级子菜单
        List<TreeMenu> menuList1 = permissionService.findMenuList();
        model.addAttribute("allPermissions", menuList);
        model.addAttribute("menuTypes", menuList1);
        return "rolelist";
    }


    @RequestMapping("/findRoles")
    public String findRoles(Model model){
        //1.查找所有的角色
        List<Role> allRoles = roleService.findAllRoles();
        //2.查找所有权限包括菜单
        List<TreeMenu> allPermission = permissionService.findAllPermission();
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("allMenuAndPermissions", allPermission);
        return "permissionlist";
    }

    @RequestMapping("/saveRoleAndPermissions")
    public String saveRoleAndPermissions(String name, int[] permissionIds){
        roleService.addRoleAndPermission(name, permissionIds);
        return "redirect:/findRoles";
    }

    @RequestMapping("/saveSubmitPermission")
    public String saveSubmitPermission(Permission permission){
        permissionService.addPermission(permission);
        return "redirect:/toAddRole";
    }

    @RequestMapping("/loadMyPermissions")
    @ResponseBody
    public List<Permission> loadMyPermissions(String roleId){
        List<Permission> rolePermissions = permissionService.getRolePermissions(roleId);
        return rolePermissions;
    }

    @RequestMapping("/updateRoleAndPermission")
    public String updateRoleAndPermission(String roleId, int[] permissionIds){
        roleService.updateRoleAndPermissions(roleId, permissionIds);
        return "redirect:/findRoles";
    }
}
