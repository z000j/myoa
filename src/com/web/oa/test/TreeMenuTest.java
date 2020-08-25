package com.web.oa.test;

import com.web.oa.mapper.PermissionCustomMapper;
import com.web.oa.pojo.Permission;
import com.web.oa.pojo.TreeMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:54
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class TreeMenuTest {

    @Autowired
    private PermissionCustomMapper customMapper;

    @Test
    public void testTreeMenu(){
        List<TreeMenu> menuList = customMapper.findMenuList();
        for (TreeMenu treeMenu : menuList) {
            System.out.println("一级菜单id：" + treeMenu.getId());
            System.out.println("一级菜单name：" + treeMenu.getName());
            List<Permission> children = treeMenu.getChildren();
            for (Permission child : children) {
                System.out.println("二级菜单:" + child.getName());
            }
            System.out.println("-------------------------------------");
        }
    }
}
