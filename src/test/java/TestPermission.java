import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by MANU on 2018/5/20.
 */
public class TestPermission {

    @Test
    public void testPermission() {
        // 构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        // 通过工厂创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建一个Subject实例，该实例认证需要使用上面创建的SecurityManager
        Subject subject = SecurityUtils.getSubject();
        //创建token令牌，账号和密码是ini文件中配置的
        //AuthenticationToken token = new UsernamePasswordToken("zhangsan", "123");//账号密码正确token 角色是 role1,role2
        AuthenticationToken token = new UsernamePasswordToken("lisi", "123");//lisi账号的token 角色是role3
        /**
         * role1=user:create,user:update
         role2=user:create,user.delete
         role3=user:create
         */
        try {
            //用户登录
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        //用户认证状态
        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态："+isAuthenticated);//输出true


        //用户授权检测
        //是否拥有一个角色
        System.out.println("是否拥有一个角色："+subject.hasRole("role1"));
        //是否拥有多个角色
        System.out.println("是否拥有多个角色："+subject.hasAllRoles(Arrays.asList("role1","role2")));

        //检测权限
        try {
            subject.checkPermission("user:create");
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        try {
            subject.checkPermissions("user:create","user.delete");
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
    }

}
