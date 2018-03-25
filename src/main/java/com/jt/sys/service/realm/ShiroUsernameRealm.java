package com.jt.sys.service.realm;

import com.jt.common.util.StringUtils;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 通过Realm这个领域对象对认证领域和授权领域信息进行检测
 */
public class ShiroUsernameRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 提取当前用户的权限
     * 进行封装处理后返回
     * @param principals    身份信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 1. 获取用户名
        String  username = (String) principals.getPrimaryPrincipal();
        // 2. 根据用户用查找对应的权限信息
        List<String> list = sysUserDao.findPermissionByUsername(username);
        // 3. 去除空的重复的权限
        Set<String> set = new HashSet<>();
        list.forEach(per->{
            if (!StringUtils.isEmpty(per)) {
                set.add(per);
            }
        });
        // 4. 封装权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 这里还必须用set注入的方法才能生效
        info.setStringPermissions(set);
        System.out.println("doGetAuthorizationInfo----->set=" + set);
        return info;
    }

    /**
     * 登陆验证
     *
     * @param token 包含角色信息令牌
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 获取令牌中的信息
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        System.out.println("username----->username=" + username);
        // 2. 根据用户名查询用户信息
        SysUser user = sysUserDao.findObjectByUsername(username);
        System.out.println("username----->user=" + user);
        String password = user.getPassword();
        String salt = user.getSalt();
        ByteSource saltByte = ByteSource.Util.bytes(salt);
        // 3. 对数据进行封装并返回
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                username,
                password,
                saltByte,
                getName());
        return info;
    }
}
