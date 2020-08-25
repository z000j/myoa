package com.web.oa.service.impl;

import com.web.oa.mapper.PermissionCustomMapper;
import com.web.oa.mapper.PermissionCustomMapperSecond;
import com.web.oa.mapper.PermissionMapper;
import com.web.oa.mapper.RoleCustomMapper;
import com.web.oa.pojo.Permission;
import com.web.oa.pojo.TreeMenu;
import com.web.oa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:51
 * @version 1.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionCustomMapper customMapper;
    @Autowired
    private PermissionCustomMapperSecond customMapper2;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleCustomMapper roleCustomMapper;

    @Override
    public List<TreeMenu> findMenuList() {
        return customMapper.findMenuList();
    }

    @Override
    public List<Permission> getUserPermissions(Long id) {
        return customMapper.getUserPermissions(id);
    }

    @Override
    public List<TreeMenu> findAllPermission() {
        return customMapper2.findAllPermission();
    }

    @Override
    public void addPermission(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public List<Permission> getRolePermissions(String roleId) {
        return  roleCustomMapper.findRolePermission(roleId);
    }
}
