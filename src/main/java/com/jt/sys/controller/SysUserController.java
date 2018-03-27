package com.jt.sys.controller;

import com.github.pagehelper.PageInfo;
import com.jt.common.util.DebugConfig;
import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.logging.Logger;

@RequestMapping("/user/")
@Controller
public class SysUserController {
    // jdk自带日志api
    private Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private SysUserService sysUserService;


    /**
     * 返回用户列表
     */
    @RequestMapping("listUI")
    public String listUI() {
        return "sys/user_list";
    }

    /**
     * 返回编辑页面
     */
    @RequestMapping("editUI")
    public String editUI(){
        return "sys/user_edit";
    }

    /**
     * 查询用户列表
     */
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
        PageInfo<SysUser> pageInfo = sysUserService.findPageObjects(username, pageCurrent);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(pageInfo);
        return jsonResult;
    }

    /**
     * 启用和禁用状态
     */
    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer id,Integer valid) {
        String modifiedUser = "admin";
        String message = sysUserService.validById(id, valid, modifiedUser);
        JsonResult jsonResult = new JsonResult(message);
        return jsonResult;
    }

    /**
     * 插入一条数据
     */
    @RequestMapping("doInsertObject")
    @ResponseBody
    public JsonResult doInsertObject(SysUser entity, String roleIds) {
        // 1. 验证数据的合法性
        /*
            system.out会引起full gc
            严重占用系统资源
            开发完成后要关闭
         */
        if(DebugConfig.DEGUG){
            System.out.println(entity);
            System.out.println(roleIds);
            log.info(entity.toString());
            log.info(roleIds);
        }
        String message = sysUserService.insertObject(entity,roleIds);
        JsonResult jsonResult = new JsonResult(message);
        return jsonResult;
    }

    /**
     * 更新一条数据
     */
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysUser entity, String roleIds) {
        String message = sysUserService.updateObject(entity, roleIds);
        JsonResult jsonResult = new JsonResult(message);
        return jsonResult;
    }

    /**
     * 查找用户的角色关系
     */
    @RequestMapping("doFindRoleIdById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer userId) {
        Map<String, Object> map = sysUserService.findObjectById(userId);
        JsonResult jsonResult = new JsonResult(map,"查询成功");
        return jsonResult;
    }


}
