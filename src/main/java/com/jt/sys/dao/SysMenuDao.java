package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

public interface SysMenuDao {
    /**
     * 一个记录对应一个map
     */
    List<Map<String,Object>> findObjects();
}
