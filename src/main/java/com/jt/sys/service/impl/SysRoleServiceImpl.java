package com.jt.sys.service.impl;

import com.jt.common.exception.ServiceException;
import com.jt.common.util.StringUtils;
import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<CheckBox> findObjects() {
        List<CheckBox> objects = sysRoleDao.findObjects();
        return objects;
    }

    @Override
    public PageObject<SysRole> findPageObjects(Integer pageCurrent, String name) {
        // 1. 判断数据的合法性
        if (pageCurrent == null || pageCurrent < 0) {
            throw new ServiceException("页码数据不合法");
        }
        // 2. 定义页面大小,计算查询起始页
        int pageSize = 3;
        int startIndex = (pageCurrent - 1) * pageSize;
        // 3. 分页查询角色信息
        List<SysRole> roles = sysRoleDao.findPageObjects(startIndex, pageSize, name);
        // 4. 查询总记录数
        int rowCount = sysRoleDao.getRowCount(name);
        // 5. 封装数据
        PageObject<SysRole> pageObject = new PageObject<>();
        pageObject.setRecords(roles);
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        // 6. 返回数据（提供给调用者）
        return pageObject;
    }


    /**
     * @param entity  角色实体
     * @param menuIds 菜单id
     * @return
     */
    @Override
    public String saveObject(SysRole entity, String[] menuIds) {
        // 1. 验证数据合法性
        if (entity == null) {
            throw new ServiceException("写入的值不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ServiceException("名字不能为空");
        }
        if (menuIds.length == 0)
            throw new ServiceException("至少选择一个菜单");
        // 2. 将对象写入数据库
        int rows = 0;
        int menuRows = 0;
        try {
            rows = sysRoleDao.insertObject(entity);
            menuRows = sysRoleMenuDao.insertObjectsByRoleId(entity.getId(), menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器异常");
        }
        // 3. 返回数据
        return "添加成功\n角色增加：" + rows + "\n菜单关系：" + menuRows;
    }

    /**
     * 更新一个角色 及其对应的菜单关系
     */
    @Override
    public String updateObjct(SysRole entity, String menuIds) {
        // 1. 验证数据合法性
        if (entity == null) {
            throw new ServiceException("写入的值不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ServiceException("名字不能为空");
        }
        if (StringUtils.isEmpty(menuIds))
            throw new ServiceException("至少需要有一项授权");
        int menuRows = 0;
        try {
            // 2. 将对象写入数据库
            int rows = sysRoleDao.updateObject(entity);
            // 3 更新关系
            // 3. 1根据角色id删除原有的菜单关系
            sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
            // 3. 2插入角色与菜单的关系
            menuRows = sysRoleMenuDao.insertObjectsByRoleId(entity.getId(), menuIds.split(","));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器在正维护中...");
        }
        // 3. 返回数据
        return "修改成功\n菜单关系：" + menuRows;
    }

    //删除一个角色，并删除对应的菜单关系
    @Override
    public String deleteObject(Integer id) {
        // 1. 验证数据合法性
        if (id == null || id <= 0) {
            throw new ServiceException("角色id不合法");
        }
        // 2. 将对象写入数据库
        int rows = 0;
        int menuRows = 0;
        try {
            rows = sysRoleDao.deleteObject(id);
            menuRows = sysRoleMenuDao.deleteObjectsByRoleId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器异常");
        }
        // 3. 返回数据
        return "删除成功\n角色删除：" + rows + "\n菜单关系：" + menuRows;
    }

    // 根据角色id查找对应的菜单id
    @Override
    public List<Integer> findRoleMenu(Integer roleId) {
        // 1. 验证数据合法性
        if (roleId == null || roleId <= 0) {
            throw new ServiceException("角色id不合法");
        }
        // 2. 查找菜单id
        List<Integer> menuIds;
        try {
            menuIds = sysRoleMenuDao.findRoleMenu(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器正在维护中");
        }
        // 3. 返回数据
        return menuIds;
    }
}
