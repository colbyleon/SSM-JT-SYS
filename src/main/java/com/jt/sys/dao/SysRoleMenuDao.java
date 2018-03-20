package com.jt.sys.dao;

/**
 *
 */
public interface SysRoleMenuDao {
    /**
     * 根据菜单ID删除数据
     * @param menuId    菜单ID
     * @return          删除的条数
     */
    int deleteObjectsByMenuId(Integer menuId);

}
