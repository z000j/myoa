package com.web.oa.mapper;

import com.web.oa.pojo.Permission;
import com.web.oa.pojo.TreeMenu;

import java.util.List;

public interface PermissionCustomMapper {
    List<TreeMenu> findMenuList();

    List<Permission> getSubMenu();

    List<Permission> getUserPermissions(Long id);
}
