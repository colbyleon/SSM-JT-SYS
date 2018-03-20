package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        PageObject<SysRole> pageObject = sysRoleService.findPageObjects(pageCurrent, name);
        return new JsonResult(pageObject, "Query ok");
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
    public JsonResult doSaveObject(SysRole sysRole) {
        // 执行插入
        int i = sysRoleService.saveObject(sysRole);
        return new JsonResult("创建成功");
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysRole sysRole) {
        // 执行行更新
        int i = sysRoleService.updateObjct(sysRole);
        return new JsonResult("更新成功");
    }

    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects() {
        return new JsonResult(sysRoleService.findObjects(), "query Ok");
    }
}
