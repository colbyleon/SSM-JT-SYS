package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 基于客户端请求，借助业务层对角访问菜单信息
 * 对菜单信息进行封装并返回
 */
@Controller
@RequestMapping("/menu/")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    /**
     * @return 菜单列表页
     */
    @RequestMapping("listUI")
    public String listUI(){
        return "sys/menu_list";
    }

    /**
     * @return 菜单编辑页面
     */
    @RequestMapping("editUI")
    public String editUI(){
        return "sys/menu_edit";
    }

    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects() {
        List<Map<String, Object>> map = sysMenuService.findObjects();
        JsonResult jsonResult = new JsonResult(map, "query ok");
        return jsonResult;
    }

    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer menuId){
        String message = sysMenuService.deleteObject(menuId);
        JsonResult jsonResult = new JsonResult(message);
        return jsonResult;
    }
    @RequestMapping("doFindZtreeMenuNodes")
    @ResponseBody
    public JsonResult doFindZtreeMenuNodes(){
        List<Node> nodes = sysMenuService.findZtreeMenuNodes();
        JsonResult jsonResult = new JsonResult(nodes, "Query Ok");
        return jsonResult;
    }

    @RequestMapping("doInsertObject")
    @ResponseBody
    public JsonResult doInsertObject(SysMenu entity) {
        String message = sysMenuService.insertObject(entity);
        JsonResult jsonResult = new JsonResult(message);
        return jsonResult;
    }
    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id) {
        SysMenu menu = sysMenuService.findObjectById(id);
        JsonResult jsonResult = new JsonResult(menu, "Query Ok");
        return jsonResult;
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysMenu entity) {
        String message = sysMenuService.updateObject(entity);
        return new JsonResult(message);
    }
}

