package com.jt.sys.dao;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysUser;
import javafx.scene.control.CheckBox;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserDao {
    /**
     * 获取用户列表
     * @param username          搜索名
     */
    List<SysUser> findPageObjects(
            @Param("username") String username);


    /**
     * 启用和禁用状态
     */
    int validById(
            @Param("id") Integer id,
            @Param("valid") Integer valid,
            @Param("modifiedUser") String modifiedUser);

    /**
     * 插入一条用户信息到数据库
     */
    int insertObject(SysUser entity);

    /**
     * 更新一条数据
     */
    int updateObject(SysUser entity);

    /**
     * 根据id查询用户
     */
    SysUser findObjectById(Integer id);

    /** 根据username查询用户*/
    SysUser findObjectByUsername(String username);

    /** 根据邮箱查找权限用户 */
    SysUser findObjectByEmail(String email);

    /** 根据邮箱查找权限用户 */
    SysUser findObjectByMobile(String mobile);

    /** 根据用户名查找权限信息 */
    List<String> findPermissionByUsername(String username);

}
