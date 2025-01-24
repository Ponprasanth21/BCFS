package com.bornfire.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SettlementReportDetailID implements Serializable{
	private String msg_id;
	private String stat_id;
	private String entry_acct_svcr_ref;
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getStat_id() {
		return stat_id;
	}
	public void setStat_id(String stat_id) {
		this.stat_id = stat_id;
	}
	public String getEntry_acct_svcr_ref() {
		return entry_acct_svcr_ref;
	}
	public void setEntry_acct_svcr_ref(String entry_acct_svcr_ref) {
		this.entry_acct_svcr_ref = entry_acct_svcr_ref;
	}

	


}
