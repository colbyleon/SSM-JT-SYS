package com.jt.sys.dao;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuDao {
    /**
     * 一个记录对应一个map
     */
    List<Map<String,Object>> findObjects();

    /**
     * 根据id查询是否有子菜单
     */
    int getChileCount(Integer menuId);

    /**
     * 根据id删除菜单
     */
    int deleteObject(Integer menuId);

    /**
     * 基于请求获取数据库中所有的菜单（id,name,parentId)
     */
    List<Node> findZtreeMenuNodes();

    /**
     * 插入一个菜单至数据库中
     */
    int insertObject(SysMenu entity);

    /**
     * 根据id查找菜单
     */
    SysMenu findObjectById(Integer id);

    /**
     * 根据id更新菜单
     */
    int updateObject(SysMenu entity);
}
