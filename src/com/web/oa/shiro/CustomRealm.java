package com.web.oa.shiro;

import com.web.oa.pojo.Permission;
import com.web.oa.pojo.User;
import com.web.oa.service.PermissionService;
import com.web.oa.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:53
 * @version 1.0
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //通过工具类获取在下一个方法传入的 user
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //拿到用户具有的权限
        List<Permission> userPermissions = permissionService.getUserPermissions(user.getId());
        List<String> permissions = new ArrayList<>();
        userPermissions.forEach(permission -> {
            permissions.add(permission.getPercode());
        });
        //将权限存放到 info
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //1.得到用户名
        String username = (String) token.getPrincipal();
        //2.判断用户是否存在
        User user = userService.findUserByUserName(username);
        if(user == null){
            return null;
        }
        //获取加密过的密码和盐
        String password_db = user.getPassword();
        String salt = user.getSalt();

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password_db,
                ByteSource.Util.bytes(salt), "CustomRealm");
        return info;
    }
}
