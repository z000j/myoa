package com.web.oa.service.impl;

import com.web.oa.mapper.RoleCustomMapper;
import com.web.oa.mapper.RoleMapper;
import com.web.oa.mapper.RolePermissionMapMapper;
import com.web.oa.pojo.Role;
import com.web.oa.pojo.RolePermissionMap;
import com.web.oa.pojo.RolePermissionMapExample;
import com.web.oa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:52
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleCustomMapper customMapper;
    @Autowired
    private RolePermissionMapMapper rolePermissionMapMapper;

    @Override
    public List<Role> findAllRoles() {
        return roleMapper.selectByExample(null);
    }

    @Override
    public Role findRoleAndPermission(String username) {
        return customMapper.findRoleContainPermission(username);
    }

    @Override
    public void addRoleAndPermission(String roleName, int[] permissions) {
        //1.增加角色和权限
        //2.要插入角色和还要更改角色和权限映射表
        Role role = new Role();
        Random random = new Random();
        String roleId = random.nextInt(1000)+"";
        role.setId(roleId);
        role.setAvailable("1");
        role.setName(roleName);
        roleMapper.insert(role);
        //这里需要更改mapper文件，插入回传id
        String id = role.getId();

        for (int i = 0; i < permissions.length; i++){
            //1.生成全球唯一uid作为权限和角色映射表id
            String uuid = UUID.randomUUID().toString();
            //2.生成对象
            RolePermissionMap rolePermissionMap = new RolePermissionMap();
            rolePermissionMap.setId(uuid);
            rolePermissionMap.setSysRoleId(id);
            rolePermissionMap.setSysPermissionId(permissions[i]+"");
            rolePermissionMapMapper.insert(rolePermissionMap);
        }
    }

    @Override
    public void updateRoleAndPermissions(String roleId, int[] permissionIds) {
        //1.删除该角色的所有权限
        RolePermissionMapExample example = new RolePermissionMapExample();
        RolePermissionMapExample.Criteria criteria = example.createCriteria();
        criteria.andSysRoleIdEqualTo(roleId);
        rolePermissionMapMapper.deleteByExample(example);
        //2.遍历权限id，赋予权限
        for(int i = 0; i < permissionIds.length; i++){
            RolePermissionMap rolePermissionMap = new RolePermissionMap();
            String id = UUID.randomUUID().toString();
            rolePermissionMap.setId(id);
            rolePermissionMap.setSysRoleId(roleId);
            rolePermissionMap.setSysPermissionId(permissionIds[i]+"");
            rolePermissionMapMapper.insert(rolePermissionMap);
        }
    }
}
