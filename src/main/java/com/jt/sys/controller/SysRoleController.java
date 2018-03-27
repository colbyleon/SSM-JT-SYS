package com.jt.sys.controller;

import com.github.pagehelper.PageInfo;
import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role/")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @RequestMapping("listUI")
    public String ListUI() {
        return "sys/role_list";
    }

    /**
     * dispatcherServlet会自动将请求参数传入到方法形参中
     *
     * @param pageCurrent 当前页面
     * @param name        搜索名字
     * @return JsonResult对象由jackson转换
     */
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(Integer pageCurrent, String name) {
        PageInfo<SysRole> pageInfo = sysRoleService.findPageObjects(pageCurrent, name);
        return new JsonResult(pageInfo, "Query ok");
    }



    @RequestMapping("editUI")
    public String editUI() {
        return "sys/role_edit";
    }

    /**
     * 插入一个角色
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysRole sysRole,String[] menuIds) {
        // 执行插入
        String message = sysRoleService.saveObject(sysRole,menuIds);
        return new JsonResult(message);
    }

    /**
     *  执行行更新
     *  同时更新角色对应的菜单
     */
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysRole sysRole,String menuIds) {
        String message = sysRoleService.updateObjct(sysRole,menuIds);
        return new JsonResult(message);
    }

    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects() {
        return new JsonResult(sysRoleService.findObjects(), "query Ok");
    }

    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id) {
        String message = sysRoleService.deleteObject(id);
        JsonResult jsonResult = new JsonResult(message);
        return jsonResult;
    }

    @RequestMapping("doFindRoleMenu")
    @ResponseBody
    public JsonResult doFindRoleMenu(Integer roleId) {
        List<Integer> menuIds = sysRoleService.findRoleMenu(roleId);
        JsonResult jsonResult = new JsonResult(menuIds, "查询成功");
        return jsonResult;
    }
}
