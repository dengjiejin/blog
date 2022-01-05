package com.djj.blog.cinfig;

import com.djj.blog.entity.User;
import com.djj.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

import java.awt.*;

import static java.awt.SystemColor.info;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");

        //拿到当前登录对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();//拿到user对象

        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
        }

        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            System.out.println("执行了=>认证doGetAuthoricationInfo");
            //用户名密码


            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
            User user = userService.fingByUsername(usernamePasswordToken.getUsername());
            System.out.println(user.getPassword()+" "+user.getUsername());
            //用户名认证
            if(user==null){
                return null;
        }
        //密码认证，shrio自己做
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
