package com.jt.sys.service.impl;

import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public List<Map<String, Object>> findObjects() {
        return sysMenuDao.findObjects();
    }
}
