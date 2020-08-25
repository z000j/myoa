package com.web.oa.service.impl;

import com.web.oa.mapper.UserCustomMapper;
import com.web.oa.mapper.UserMapper;
import com.web.oa.pojo.User;
import com.web.oa.pojo.UserExample;
import com.web.oa.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:52
 * @version 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCustomMapper customMapper;

    @Override
    public User findUserByUserName(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        //设置条件
        criteria.andNameEqualTo(username);

        List<User> users = userMapper.selectByExample(example);
        if(users != null && users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User findUserById(Long managerID) {
        return userMapper.selectByPrimaryKey(managerID);
    }

    @Override
    public List<User> findAllUserContainLeader() {
        return customMapper.findUserContainLeader();
    }

    @Override
    public int updateUserRole(String rid, String username) {
        //更改user表的角色id
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        int count = 0;
        if(users!=null && users.size()>0){
            User user = users.get(0);
            user.setRole(Integer.parseInt(rid));
            count = userMapper.updateByExampleSelective(user,example);
        }
        return count;
    }

    @Override
    public List<User> findUserByRole(String level) {
        return customMapper.findUserByRole(Integer.parseInt(level));
    }

    @Override
    public int AddUserOrUpdate(User user) {
        int count = 0;
            //插入
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),"hpj",2);
        user.setPassword(md5Hash.toString());
        user.setSalt("hpj");
        count = userMapper.insert(user);
        return count;
    }


}
