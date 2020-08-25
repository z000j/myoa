package com.web.oa.service;

public interface UserRoleMapService {
    int updateRoleIDByUserId(String userID, String rid);
    int addUserRole(String userID, String rid);
}
