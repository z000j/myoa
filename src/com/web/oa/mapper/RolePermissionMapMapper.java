package com.web.oa.mapper;

import com.web.oa.pojo.RolePermissionMap;
import com.web.oa.pojo.RolePermissionMapExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapMapper {
    int countByExample(RolePermissionMapExample example);

    int deleteByExample(RolePermissionMapExample example);

    int deleteByPrimaryKey(String id);

    int insert(RolePermissionMap record);

    int insertSelective(RolePermissionMap record);

    List<RolePermissionMap> selectByExample(RolePermissionMapExample example);

    RolePermissionMap selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RolePermissionMap record, @Param("example") RolePermissionMapExample example);

    int updateByExample(@Param("record") RolePermissionMap record, @Param("example") RolePermissionMapExample example);

    int updateByPrimaryKeySelective(RolePermissionMap record);

    int updateByPrimaryKey(RolePermissionMap record);
}