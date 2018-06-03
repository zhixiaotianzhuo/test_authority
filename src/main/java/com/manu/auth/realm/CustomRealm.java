package com.manu.auth.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by MANU on 2018/5/20.
 */
public class CustomRealm extends AuthorizingRealm {

        /**
         * 认证方法
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

            // 从token中获取用户身份信息
            String username = (String) token.getPrincipal();

            // 正常逻辑应该是通过username查询数据库。
            // 如果查询不到返回null
            if (!"zhangsan".equals(username)) {// 这里模仿查询不到
                return null;
            }

            // 模拟从数据获取密码
            String password = "123";

            // 返回认证信息交由父类AuthorizingRealm认证
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "");

            return authenticationInfo;
        }

        /**
         * 授权方法
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            return null;
        }

    }
