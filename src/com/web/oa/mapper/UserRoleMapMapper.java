package com.web.oa.mapper;

import com.web.oa.pojo.UserRoleMap;
import com.web.oa.pojo.UserRoleMapExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapMapper {
    int countByExample(UserRoleMapExample example);

    int deleteByExample(UserRoleMapExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserRoleMap record);

    int insertSelective(UserRoleMap record);

    List<UserRoleMap> selectByExample(UserRoleMapExample example);

    UserRoleMap selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserRoleMap record, @Param("example") UserRoleMapExample example);

    int updateByExample(@Param("record") UserRoleMap record, @Param("example") UserRoleMapExample example);

    int updateByPrimaryKeySelective(UserRoleMap record);

    int updateByPrimaryKey(UserRoleMap record);
}