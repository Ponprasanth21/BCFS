package com.bornfire.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class WalletMasterDetailID implements Serializable {

	private String bips_acct_no;
	private String tran_type;
	public String getBips_acct_no() {
		return bips_acct_no;
	}
	public void setBips_acct_no(String bips_acct_no) {
		this.bips_acct_no = bips_acct_no;
	}
	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}
	
	
}
