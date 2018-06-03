package com.manu.auth.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Created by MANU on 2018/5/20.
 */
public class CustomRealmMd5 extends AuthorizingRealm {

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



        Md5Hash md5Hash = new Md5Hash("111111");
        System.out.println("md5加密，不加盐："+md5Hash.toString());

        //md5加密，加盐，一次hash
        String password_md5_sale_1 = new Md5Hash("11111", "aga23", 1).toString();
        System.out.println("md5加密，加盐，一次hash："+password_md5_sale_1);

        //md5加密，加盐，两次hash
        String password_md5_sale_2 = new Md5Hash("11111", "aga23", 2).toString();
        System.out.println("md5加密，加盐，两次hash："+password_md5_sale_2);//相当于md5(md5('1111'))

        //使用simpleHash
        String simpleHash = new SimpleHash("MD5", "11111", "aga23", 1).toString();
        System.out.println(simpleHash);



        // 模拟从数据获取密码
        String password = "fdf907b0d3f427b9ffa2f86f213d1afd";
        // 盐
        String salt = "aga23";
        // 返回认证信息交由父类AuthorizingRealm认证
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password,
                ByteSource.Util.bytes(salt), "");

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
