package com.web.oa.mapper;

import com.web.oa.pojo.Permission;
import com.web.oa.pojo.Role;
import com.web.oa.pojo.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleCustomMapper {
   Role findRoleContainPermission(String username);
   List<Permission> findRolePermission(String roleID);
}