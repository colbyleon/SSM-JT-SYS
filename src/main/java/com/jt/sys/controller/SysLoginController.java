package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class SysLoginController {
    @Autowired
    private SysUserService sysUserService;
    /**
     * 返回登陆页面
     */
    @RequestMapping("loginUI")
    public String loginUI(){
        return "login";
    }

    /**
     * 处理登陆请求
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public JsonResult doLogin(String username, String password){
        System.out.println("SysLoginController.doLogin");
        sysUserService.login(username,password);
        return new JsonResult("ok");
    }
}
