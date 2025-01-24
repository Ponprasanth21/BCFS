package com.bornfire.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class ManualTransactionID implements
Serializable{

	private String doc_ref_id;
	private String doc_sub_id;
	
	public String getDoc_ref_id() {
		return doc_ref_id;
	}
	public String getDoc_sub_id() {
		return doc_sub_id;
	}
	public void setDoc_ref_id(String doc_ref_id) {
		this.doc_ref_id = doc_ref_id;
	}
	public void setDoc_sub_id(String doc_sub_id) {
		this.doc_sub_id = doc_sub_id;
	}
	
	
}
