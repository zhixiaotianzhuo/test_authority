import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.authc.AuthenticationException;
import org.junit.Test;

/**
 * Created by MANU on 2018/5/20.
 */
public class TeatRealm {

    @Test
    public void testCustomeRealm() {
        // 构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        // 通过工厂创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建一个Subject实例，该实例认证需要使用上面创建的SecurityManager
        Subject subject = SecurityUtils.getSubject();
        //创建token令牌，账号和密码是ini文件中配置的
        //AuthenticationToken token = new UsernamePasswordToken("zhangsan", "123");//账号密码正确token
        //AuthenticationToken token = new UsernamePasswordToken("zhangsan", "1234");//密码错误异常token
        AuthenticationToken token = new UsernamePasswordToken("zhangsan1", "123");//账号错误异常token
        try {
            //用户登录
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        //用户认证状态
        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态："+isAuthenticated);//输出true
    }
}
