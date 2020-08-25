package com.web.oa.mapper;

import com.web.oa.pojo.User;
import com.web.oa.pojo.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCustomMapper {
   List<User> findUserContainLeader();

   List<User> findUserByRole(int level);
}