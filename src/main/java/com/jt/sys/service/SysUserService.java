package com.jt.sys.service;

import com.github.pagehelper.PageInfo;
import com.jt.sys.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService {



    /**
     * 接收控制层数据并对对传入参数进行合法性判断
     * 调用dao层方法，依据条件获取当前数据以及总记录数
     * 封装数据并返回
     */
    PageInfo<SysUser> findPageObjects(String username, Integer pageCurrent);

    /**
     * 启用和禁用数据
     */
    String validById(Integer id, Integer vaild, String modifiedUser);

    /**
     * 插入一条数据
     */
    String insertObject(SysUser entity,String roleIds);

    /**
     * 更新一条数据
     */
    String updateObject(SysUser entity, String roleIds);

    /**
     * 根据用户id查找用户以及对应的多个角色id
     * @return map中封装sysUser对象，List<Integer>对象
     */
    Map<String, Object> findObjectById(Integer userId);

    /**
     * 处理登陆请求
     */
    void login(String username, String password);

    /**
     * 导出用户列表
     */
    List<SysUser> exportFileData();

}
