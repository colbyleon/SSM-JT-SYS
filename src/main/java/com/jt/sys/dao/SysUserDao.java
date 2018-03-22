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
     * @param startIndex    开始搜索下标
     * @param pageSize      每页用户条数
     */
    List<SysUser> findPageObjects(
            @Param("username") String username,
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize);

    /**
     * 依据条件查询总记录数
     * @param username      用户名
     * @return              记录数
     */
    int getRowCount(@Param("username") String username);

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

    /**
     * 根据username查询用户
     */
    SysUser findObjectByUsername(String username);

    /** 根据用户名查找权限信息 */
    List<String> findPermissionByUsername(String username);

}
