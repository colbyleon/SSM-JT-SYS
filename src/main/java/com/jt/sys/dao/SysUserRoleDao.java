package com.jt.sys.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleDao {

    /**
     * 接受用户和角色的关系数据，并将数据写入到中间表中
     */
    int insertObject(
            @Param("userId") Integer userId,
            @Param("roleIds") String[] roleIds);

    /**
     * 根据用户id查找对应的角色id
     */
    List<Integer> findRoleIdById(Integer userId);


    /**
     * 中间表更新数据只能先删除用户id对应的数据并重新插入
     */
    int deleteObjects(Integer userId);
}
