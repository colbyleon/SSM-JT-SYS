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

public class ShiroEmailRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 满足leastOne原则即可，这里不用写了
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 取出传入的数据
        UsernamePasswordToken emailToken = (UsernamePasswordToken)token;
        String email = emailToken.getUsername();
        System.out.println("email----->email=" + email);
        // 2. 从数据库中取出密码数据
        SysUser user = sysUserDao.findObjectByEmail(email);
        System.out.println("email----->user=" + user);
        String password = user.getPassword();
        String salt = user.getSalt();
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        // 3. 封装数据并返回
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                email,
                password,
                byteSource,
                getName());
        return info;
    }
}
