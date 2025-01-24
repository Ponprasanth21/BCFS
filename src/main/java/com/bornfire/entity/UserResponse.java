package com.bornfire.entity;

import java.math.BigDecimal;

public class UserResponse {
	private String status;
	private String msg;
	
	
	public UserResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserResponse(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
