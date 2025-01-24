package com.bornfire.entity;

import java.io.Serializable;

import javax.persistence.Id;


public class MerchantID implements Serializable {

@Id	
	private String account_no;

public String getAccount_no() {
	return account_no;
}

public void setAccount_no(String account_no) {
	this.account_no = account_no;
}




}
