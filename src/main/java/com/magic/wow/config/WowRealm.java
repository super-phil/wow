package com.magic.wow.config;

import com.magic.wow.model.User;
import com.magic.wow.service.UserService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoxf on 2017/3/6.
 */
public class WowRealm extends AuthorizingRealm {
    private Logger logger = Logger.getLogger(WowRealm.class);
    @Autowired
    private UserService userService;
    public WowRealm() {//加密验证
        setName("WowRealm");
        HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();

        //使用SHA-1 加密
        hcm.setHashAlgorithmName(Sha1Hash.ALGORITHM_NAME);
        setCredentialsMatcher(hcm);
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * @see .经测试：本例中该方法的调用时机为需授权资源被访问时
     * @see .经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see .经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        User u = (User) super.getAvailablePrincipal(principalCollection);
        //到数据库查是否有此对象 权限复杂的话
        User user = userService.findByName(u.getUsername(), u.getPwd());// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
            info.addRole(user.getRole());
//            info.setRoles(Sets.newHashSet("user:edit"));
//            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
//            List<Role> roleList=user.getRoleList();
//            for (Role role : roleList) {
//                info.addStringPermissions(role.getPermissionsName());
//            }
            // 或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
//            simpleAuthorInfo.addRole("admin");
            //添加权限
//            simpleAuthorInfo.addStringPermission("admin:manage");
//            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            return info;
        }
        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        User user = userService.findByName(token.getUsername(), new Sha1Hash(token.getPassword(), token.getUsername()).toHex());
        if (user != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPwd(), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername()));
            return info;
        }
        return null;
    }
    public static void main(String[] args) {
//        System.out.println("cd5ea73cd58f827fa78eef7197b8ee606c99b2e6".length());
        System.out.println(new Sha1Hash("123456", "test").toHex());
    }
}
