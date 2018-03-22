package com.jt.sys.service.impl;

import com.jt.common.exception.ServiceException;
import com.jt.common.util.StringUtils;
import com.jt.common.vo.Node;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    /**
     * 根据菜单ID进行菜单及用角色关联关系删除
     */
    @Override
    public String deleteObject(Integer menuId) {
        // 1. 进行合法性验证
        if (menuId == null || menuId < 0)
            throw new ServiceException("参数不合法");
        // 2. 执行查询操作，如果有子菜单，刚抛异常
        int chileCount = sysMenuDao.getChileCount(menuId);
        if (chileCount > 0)
            throw new ServiceException("此菜单下还有子菜单，不能进行删除");
        // 3. 执行菜单及关系删除
        int rows, rows2;
        try {
            // 3.1 删除菜单
            rows = sysMenuDao.deleteObject(menuId);
            if (rows == 0)
                throw new ServiceException("此菜单可能已经不存在");
            // 3.2 删除菜单对应的角色关系
            rows2 = sysRoleMenuDao.deleteObjectsByMenuId(menuId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器正在维护中...");
        }
        // 4. 返回结果
        return "删除成功，已经删除\n" +
                "菜单:" + rows + "条\n" +
                "关系角色：" + rows2 + "条";
    }

    /**
     * @return 所有的菜单，这里没有自定义实体类，使用map
     */
    @Override
    public List<Map<String, Object>> findObjects() {
        return sysMenuDao.findObjects();
    }

    @Override
    public List<Node> findZtreeMenuNodes() {
        return sysMenuDao.findZtreeMenuNodes();
    }

    /**
     * @param entity 菜单实体
     * @return       插入结果
     */
    @Override
    public String insertObject(SysMenu entity) {
        // 1. 对参数进行验证
        if(entity == null)
            throw new ServiceException("参数不合法");
        if(StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名字不能为空");
        if(entity.getType()!=1&&entity.getType()!=2)
            throw new ServiceException("类型不正确，只能为1或2");
        if (entity.getId()!=null)
            throw new ServiceException("插入时怎么会有id值");
        // 2. 执行插入操作
        int row ;
        try {
            row = sysMenuDao.insertObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器正在维护中...");
        }
        if(row<1)
            throw new ServiceException("插入失败了");
        // 3. 封装并返回结果
        return "成功插入"+row+"个菜单";
    }

    /**
     * 根据id查找菜单
     * @param id
     * @return
     */
    @Override
    public SysMenu findObjectById(Integer id) {
        // 1. 对参数进行合法性检查
        if (id == null || id < 1)
            throw new ServiceException("菜单ID不合法");
        // 2. 查找数据
        SysMenu menu;
        try {
            menu = sysMenuDao.findObjectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器正在维护中");
        }
        // 3. 返回数据
        return menu;
    }

    /**
     * 根据id更新一个菜单
     */
    @Override
    public String updateObject(SysMenu entity) {
        // 1. 对参数进行合法性检查
        if (StringUtils.isEmpty(entity.getName()))
            throw new ServiceException("菜单名称不能为空");
        // 2. 进行更新操作
        int rows;
        try {
            rows = sysMenuDao.updateObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("服务器正在维护中...");
        }
        // 3. 对结果封装并返回
        if (rows <= 0){
            throw new ServiceException("更新失败");
        }
        return "更新成功";
    }
}
