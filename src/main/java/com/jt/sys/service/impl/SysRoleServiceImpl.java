package com.jt.sys.service.impl;

import com.jt.common.exception.ServiceException;
import com.jt.common.util.StringUtils;
import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service 
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;

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
	 *  接收控制层数据
	 *  对数据进行合法性检测
	 *  暂且先不考虑关系数据
	 *  直接进行删除操作
	 */


    @Override
    public int saveObject(SysRole entity) {
        // 1. 验证数据合法性
        if (entity == null) {
            throw new ServiceException("写入的值不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ServiceException("名字不能为空");
        }
        // 2. 将对象写入数据库
        int rows = sysRoleDao.insertObject(entity);
        // 3. 返回数据
        return rows;
    }

    @Override
    public int updateObjct(SysRole entity) {
        // 1. 验证数据合法性
        if (entity == null) {
            throw new ServiceException("写入的值不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new ServiceException("名字不能为空");
        }
        // 2. 将对象写入数据库
        int rows = sysRoleDao.updateObject(entity);
        // 3. 返回数据
        return rows;
    }

}
