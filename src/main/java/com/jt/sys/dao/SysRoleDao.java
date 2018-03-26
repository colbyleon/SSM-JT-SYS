package com.jt.sys.dao;

import java.util.List;

import javafx.scene.control.CheckBox;
import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysRole;

public interface SysRoleDao {

	/** 分页查询角色信息*/
	List<SysRole> findPageObjects(	@Param("name")String name);

    /**添加一个角色*/
    int insertObject(SysRole sysRole);

    /**
     * 更新一个角色
     */
    int updateObject(SysRole entity);

    /**
     * 查询所有角色的id和名字
     */
    List<com.jt.common.vo.CheckBox> findObjects();

    /**
     * 根据id删除一个角色
     */
    int deleteObject(Integer id);
}
