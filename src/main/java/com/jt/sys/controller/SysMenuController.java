package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
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

    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects() {
        List<Map<String, Object>> map = sysMenuService.findObjects();
        JsonResult jsonResult = new JsonResult(map, "query ok");
        return jsonResult;
    }
}

