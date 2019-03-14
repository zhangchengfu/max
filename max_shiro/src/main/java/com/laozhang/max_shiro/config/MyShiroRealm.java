package com.laozhang.max_shiro.config;

import com.laozhang.max_shiro.entity.Role;
import com.laozhang.max_shiro.entity.User;
import com.laozhang.max_shiro.service.UserService;
import com.laozhang.max_shiro.vo.ResourcesVO;
import com.laozhang.max_shiro.vo.RoleVO;
import com.laozhang.max_shiro.vo.UserVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        UserVO userVO = userService.getUserVO(user);
        if (null != userVO) {
            for (RoleVO role : userVO.getRoleVOS()) {
                authorizationInfo.addRole(role.getRoledesc());
                for (ResourcesVO resourcesVO : role.getResourcesVOS()) {
                    authorizationInfo.addStringPermission(resourcesVO.getName());
                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        //UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.selectByUsername(username);
        //String newPassword = new SimpleHash("md5", usernamePasswordToken.getPassword(),  ByteSource.Util.bytes(user.getSalt()), 2).toHex();
        if (null == user) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
