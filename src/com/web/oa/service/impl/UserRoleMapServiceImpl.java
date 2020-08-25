package com.web.oa.service.impl;

import com.web.oa.mapper.UserRoleMapMapper;
import com.web.oa.pojo.UserRoleMap;
import com.web.oa.pojo.UserRoleMapExample;
import com.web.oa.service.UserRoleMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:52
 * @version 1.0
 */

@Service
public class UserRoleMapServiceImpl implements UserRoleMapService {

    @Autowired
    private UserRoleMapMapper userRoleMapMapper;

    @Override
    public int updateRoleIDByUserId(String userID,String rid) {
        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        criteria.andSysUserIdEqualTo(userID);
        List<UserRoleMap> userRoleMaps = userRoleMapMapper.selectByExample(example);
        int count = 0;
        if(userRoleMaps != null && userRoleMaps.size()>0){
            UserRoleMap userRoleMap = userRoleMaps.get(0);
            userRoleMap.setSysRoleId(rid);
            count = userRoleMapMapper.updateByExample(userRoleMap,example);
        }
        return count;
    }

    @Override
    public int addUserRole(String userID, String rid) {
        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setId(userID);
        userRoleMap.setSysUserId(userID);
        userRoleMap.setSysRoleId(rid);
        return userRoleMapMapper.insert(userRoleMap);
    }
}
