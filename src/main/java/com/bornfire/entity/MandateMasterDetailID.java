package com.bornfire.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class MandateMasterDetailID implements Serializable {
	@Id
	private String mand_account_no;
	@Id
	private String ben_account_no;
	public String getMand_account_no() {
		return mand_account_no;
	}
	public void setMand_account_no(String mand_account_no) {
		this.mand_account_no = mand_account_no;
	}
	public String getBen_account_no() {
		return ben_account_no;
	}
	public void setBen_account_no(String ben_account_no) {
		this.ben_account_no = ben_account_no;
	}
	
	
}
