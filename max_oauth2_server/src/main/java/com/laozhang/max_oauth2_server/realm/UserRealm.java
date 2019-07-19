package com.laozhang.max_oauth2_server.realm;

import com.laozhang.max_oauth2_server.entity.User;
import com.laozhang.max_oauth2_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 此处设置角色的权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        log.info("登录名username = " + username);
        User user = userService.findByUsername(username);

        if (user == null) {
            log.error("用户不存在！");
            throw new UnknownAccountException();//没找到帐号
        }

        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(), //密码
                //ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                getName());
    }
}
