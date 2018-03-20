package com.jt.common.vo;

import java.io.Serializable;

public class JsonResult implements Serializable{
	private static final long serialVersionUID = -4288035597433684397L;
	
	/**状态码(1:表示正常数据，0：异常数据)*/
	private int state = 1; // ok
	/**消息*/
	private String message = "ok";
	/**正常的数据*/
	private Object data;
	
	public JsonResult(){};

	/**接收到异常时状态自动设为0*/
	public JsonResult(Throwable t){
		this.state = 0;
		this.message = t.getMessage();
	}
	
	public JsonResult(Object data ,String message){
		this(message);
		this.data = data;
	};
	
	public JsonResult(String message){
		this.message = message;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
