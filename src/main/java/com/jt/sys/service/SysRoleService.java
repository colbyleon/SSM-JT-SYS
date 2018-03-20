package com.jt.sys.service;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;

import java.util.List;

public interface SysRoleService {

	/**
	 * 本方法中要分页查询角色信息，并查询角色总记录数
	 * 根据当前页面返回页面数据对象，包括实体数据和分页信息
	 */
	PageObject<SysRole> findPageObjects(Integer pageCurrent, String name);



    /**
     * 插入一个角色
     */
    int saveObject(SysRole entity);

    /**
     * 更新一个角色
     */
    int updateObjct(SysRole entity);

    /**
     * @return 所有角色的id,name
     */
    List<CheckBox> findObjects();

}
