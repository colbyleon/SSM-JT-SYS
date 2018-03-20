package com.jt.sys.service.impl;

import com.jt.common.exception.ServiceException;
import com.jt.common.util.StringUtils;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;


    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public String validById(Integer id, Integer vaild, String modifiedUser) {
        // 1. 对数据进行合法性验证
        if (id == null || id < 1)
            throw new ServiceException("参数不合法，id=" + id);
        if (vaild != 1 && vaild != 0)
            throw new ServiceException("参数不合法，vaild=" + vaild);
        if (StringUtils.isEmpty(modifiedUser))
            throw new ServiceException("修改用户不能为空");

        // 2. 执行更新操作
        int rows = 0;
        try {
            rows = sysUserDao.validById(id, vaild, modifiedUser);
        } catch (Throwable e) {
            // 报警，给维护人员发短信
            e.printStackTrace();
            throw new ServiceException("服务器正在维护中");
        }
        // 3. 结果进行验证并返回
        if (rows == 0)
            throw new ServiceException("此记录可能已不存在");
        return "操作成功";
    }

    /**
     * 根据用户id更新用户以及用户角色关系
     *
     * @param entity  用户实体
     * @param roleIds 用户对应的角色id
     */
    public String updateObject(SysUser entity, String roleIds) {
        // 1. 对数据进行合法性核验
        if (entity == null)
            throw new ServiceException("用户信息不能为空");
        if (StringUtils.isEmpty(roleIds))
            throw new ServiceException("至少有一个角色信息");
        if (StringUtils.isEmpty(entity.getUsername()))
            throw new ServiceException("用户名不能为空");
        // 用户名已经存在的验证，尝试自己实现
        // 如果重新设置了密码需要重新加密
        if (!StringUtils.isEmpty(entity.getPassword())) {
            String salt = UUID.randomUUID().toString();
            SimpleHash hash = new SimpleHash("MD5", entity.getPassword(), salt);
            entity.setPassword(hash.toString());
        }
        // 2. 进行更新操作
        // 2.1 对用户表进行更新操作
        int i = 0;
        try {
            i = sysUserDao.updateObject(entity);
            // 2.2 先根据用户id删除相应的角色
            int userId = entity.getId();
            sysUserRoleDao.deleteObjects(userId);
            // 2.3 重新添加新的用户角色关系数据
            sysUserRoleDao.insertObject(userId, roleIds.split(","));
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ServiceException("服务器正在维护中");
        }
        // 3. 对返回结果进行判断
        if (i < 1) {
            throw new ServiceException("修改失败：用户可能已经不存在");
        }
        // 4. 封装结果并返回
        return "修改成功";
    }

    @Override
    public String insertObject(
            @RequestParam("entity") SysUser entity,
            @RequestParam("roleIds") String roleIds
    ) {

        if (StringUtils.isEmpty(entity.getUsername())) {
            throw new ServiceException("用户名不能为空");
        }
        if (StringUtils.isEmpty(entity.getPassword()))
            if (entity == null || roleIds == null) {
                throw new ServiceException("数据参数不能为空");
            }
        // 2. 执行插入
        String salt = UUID.randomUUID().toString();
        entity.setSalt(salt);
        SimpleHash sHash = new SimpleHash("MD5", entity.getPassword(), salt);
        entity.setPassword(sHash.toString());
        int rows = 0;
        try {
            rows = sysUserDao.insertObject(entity);
            // 插入后mybatis会自动更新id值
            sysUserRoleDao.insertObject(entity.getId(), roleIds.split(","));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("系统正在维护中");
        }

        // 3. 根据执行结果判定返回
        if (rows < 1) {
            throw new ServiceException("插入失败");
        }
        // 4. 封装数据并返回
        return "插入成功";
    }


    @Override
    public PageObject<SysUser> findPageObjects(String username, Integer pageCurrent) {
        // 1. 数据合法性验证
        if (pageCurrent == null || pageCurrent < 1) {
            throw new ServiceException("当前页面值不合法");
        }
        // 2. 计算startIndex的值
        int pageSize = 3;
        int startSize = pageSize * (pageCurrent - 1);
        // 3. 依据条件获取当前页数据
        List<SysUser> list = sysUserDao.findPageObjects(username, startSize, pageSize);
        // 4. 依据条件获取总数据
        int rowCount = sysUserDao.getRowCount(username);
        // 5. 封装数据
        PageObject<SysUser> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(list);
        pageObject.setRowCount(rowCount);
        return pageObject;
    }

    @Override
    public Map<String, Object> findObjectById(Integer userId) {
        // 1. 验证数据合法性
        if (userId == null || userId < 1)
            throw new ServiceException("用户ID不合法,userId=" + userId);
        // 2. 进行查询并接收数据
        List<Integer> roleIds = null;
        SysUser user = null;
        try {
            user = sysUserDao.findObjectById(userId);
            roleIds = sysUserRoleDao.findRoleIdById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("系统正在维护中");
        }
        // 3. 对返回数据进行检查并封装
        if (roleIds.size() < 1) {
            throw new ServiceException("此用户没有角色信息");
        }
        if (user == null)
            throw new ServiceException("此用户已经不存在");
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("roleIds", roleIds);
        return map;
    }
}
