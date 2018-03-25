package com.jt.sys.service.realm;

import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroMobileRealm extends AuthorizingRealm {

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
        UsernamePasswordToken mobileToken = (UsernamePasswordToken)token;
        String mobile = mobileToken.getUsername();
        System.out.println("mobile----->mobile=" + mobile);
        // 2. 从数据库中取出密码数据
        SysUser user = sysUserDao.findObjectByMobile(mobile);
        System.out.println("mobile----->user=" + user);
        String password = user.getPassword();
        String salt = user.getSalt();
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        // 3. 封装数据并返回
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                mobile,
                password,
                byteSource,
                getName());
        return info;
    }
}
