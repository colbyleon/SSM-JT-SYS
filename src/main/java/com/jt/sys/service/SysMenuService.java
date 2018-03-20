package com.jt.sys.service;

import java.util.List;
import java.util.Map;

/**
 * 1）定义菜单业务接口，负责处理菜单模块业务
 * 2) 定义业务方法，访问dao层方法获取菜单信息
 */
public interface SysMenuService {
    List<Map<String, Object>> findObjects();
}
