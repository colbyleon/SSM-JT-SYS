package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping("indexUI")
	public String indexUI() {
		return "starter";
	}
	
	@RequestMapping("pageUI")
	public String pageUI(){
		return "common/page";
	}


}
