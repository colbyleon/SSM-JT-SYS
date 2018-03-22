package com.jt.sys.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据角色ID删除数据
     */
    int deleteObjectsByRoleId(Integer roleId);

    /**
     * 根据角色id插入多个关系数据
     */
    int insertObjectsByRoleId(
            @Param("roleId") Integer roleId,
            @Param("menuIds") String[] menuIds);

    /**
     * 根据角色id查找对应的菜单id
     */
    List<Integer> findRoleMenu(Integer roleId);

}
