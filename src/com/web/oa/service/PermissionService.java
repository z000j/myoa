package com.web.oa.service;

import com.web.oa.pojo.Permission;
import com.web.oa.pojo.TreeMenu;

import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:52
 * @version 1.0
 */
public interface PermissionService {
    List<TreeMenu> findMenuList();

    List<Permission> getUserPermissions(Long id);

    List<TreeMenu> findAllPermission();

    void addPermission(Permission permission);

    List<Permission> getRolePermissions(String roleId);
}
