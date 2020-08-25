package com.web.oa.service;

import com.web.oa.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();

    Role findRoleAndPermission(String username);

    void addRoleAndPermission(String roleName, int[] permissions);

    void updateRoleAndPermissions(String roleId, int[] permissionIds);
}
