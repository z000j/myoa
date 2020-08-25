package com.web.oa.mapper;

import com.web.oa.pojo.Permission;
import com.web.oa.pojo.TreeMenu;

import java.util.List;

public interface PermissionCustomMapperSecond {
    List<TreeMenu> findAllPermission();

    List<Permission> getSubMenuSecond();
}
