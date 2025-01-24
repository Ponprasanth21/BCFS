package com.bornfire.entity;

import org.springframework.stereotype.Component;

@Component
public class FolderRespModel {
	
	private String file_name;
	private String msg_type;
	private String source;
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public FolderRespModel(String file_name, String msg_type, String source) {
		super();
		this.file_name = file_name;
		this.msg_type = msg_type;
		this.source = source;
	}
	
	
	public FolderRespModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FolderRespModel [file_name=" + file_name + ", msg_type=" + msg_type + ", source=" + source + "]";
	}

}
