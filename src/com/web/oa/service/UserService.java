package com.web.oa.service;

import com.web.oa.pojo.User;

import java.util.List;

public interface UserService {

    User findUserByUserName(String username);

    User findUserById(Long managerID);

    List<User> findAllUserContainLeader();

    int updateUserRole(String rid, String username);

    List<User> findUserByRole(String level);

    int AddUserOrUpdate(User user);
}
