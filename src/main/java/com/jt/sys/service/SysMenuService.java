package com.jt.sys.service;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * 1）定义菜单业务接口，负责处理菜单模块业务
 * 2) 定义业务方法，访问dao层方法获取菜单信息
 */
public interface SysMenuService {


    /**
     * 根据菜单ID进行菜单及用角色关联关系删除
     */
    String deleteObject(Integer menuId);


    List<Map<String, Object>> findObjects();

    List<Node> findZtreeMenuNodes();

    /**
     * 插入一个菜单
     */
    String insertObject(SysMenu entity);
}
