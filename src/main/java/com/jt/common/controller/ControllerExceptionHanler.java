package com.jt.common.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.JsonResult;

/**
 * 负责全局异常处理
 * 异常处理流程：控制层出现异常
 * 1）首先检测本类是否定义了能够处此异常的异常处理方法
 * 2）本类没有则检测是否有对应的全局异常处理方法
 */
@ControllerAdvice
public class ControllerExceptionHanler {
	
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public JsonResult handleServiceException(ServiceException e) {
		e.printStackTrace();
		return new JsonResult(e);
	}

	@ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult handleServiceException(Exception e){
	    e.printStackTrace();
        return new JsonResult(e);
    }

}
