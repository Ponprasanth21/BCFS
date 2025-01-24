package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TransationMonitorPojo {
	private Date tran_date;
	private String sequence_unique_id;
	private String tran_audit_number;
	private String	bob_account;
	private String msg_type;
	private BigDecimal tran_amount;
	private String tran_currency;
	private String reason;
	private String tran_status;
	private String cbs_status;
	private String tranStatusSeq;
	private String tran_type;
	private String bob_account_name;
	private String ipsx_account;
	private String ipsx_account_name;
	private String tran_particulars;
	private Date value_date;
	private Date reversalDate;
	private String entry_user;
	private String master_ref_id;
	private String benBank;
	private String channel;
	private String remBank;
	private String initBank;
	private String reservedField1;
	
	public Date getTran_date() {
		return tran_date;
	}
	public void setTran_date(Date tran_date) {
		this.tran_date = tran_date;
	}
	
	
	
	public String getTranStatusSeq() {
		return tranStatusSeq;
	}
	public void setTranStatusSeq(String tranStatusSeq) {
		this.tranStatusSeq = tranStatusSeq;
	}
	public String getSequence_unique_id() {
		return sequence_unique_id;
	}
	public void setSequence_unique_id(String sequence_unique_id) {
		this.sequence_unique_id = sequence_unique_id;
	}
	public String getTran_audit_number() {
		return tran_audit_number;
	}
	public void setTran_audit_number(String tran_audit_number) {
		this.tran_audit_number = tran_audit_number;
	}
	public String getBob_account() {
		return bob_account;
	}
	public void setBob_account(String bob_account) {
		this.bob_account = bob_account;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public BigDecimal getTran_amount() {
		return tran_amount;
	}
	public void setTran_amount(BigDecimal tran_amount) {
		this.tran_amount = tran_amount;
	}
	public String getTran_currency() {
		return tran_currency;
	}
	public void setTran_currency(String tran_currency) {
		this.tran_currency = tran_currency;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTran_status() {
		return tran_status;
	}
	public void setTran_status(String tran_status) {
		this.tran_status = tran_status;
	}

	public String getCbs_status() {
		return cbs_status;
	}
	public void setCbs_status(String cbs_status) {
		this.cbs_status = cbs_status;
	}
	
	
	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}
	
	
	public String getBob_account_name() {
		return bob_account_name;
	}
	public void setBob_account_name(String bob_account_name) {
		this.bob_account_name = bob_account_name;
	}
	public String getIpsx_account() {
		return ipsx_account;
	}
	public void setIpsx_account(String ipsx_account) {
		this.ipsx_account = ipsx_account;
	}
	public String getIpsx_account_name() {
		return ipsx_account_name;
	}
	public void setIpsx_account_name(String ipsx_account_name) {
		this.ipsx_account_name = ipsx_account_name;
	}
	public String getTran_particulars() {
		return tran_particulars;
	}
	public void setTran_particulars(String tran_particulars) {
		this.tran_particulars = tran_particulars;
	}
	
	
	public Date getValue_date() {
		return value_date;
	}
	public void setValue_date(Date value_date) {
		this.value_date = value_date;
	}
	
	
	public String getMaster_ref_id() {
		return master_ref_id;
	}
	public void setMaster_ref_id(String master_ref_id) {
		this.master_ref_id = master_ref_id;
	}
	public Date getReversalDate() {
		return reversalDate;
	}
	public void setReversalDate(Date reversalDate) {
		this.reversalDate = reversalDate;
	}
	
	
	public String getBenBank() {
		return benBank;
	}
	public void setBenBank(String benBank) {
		this.benBank = benBank;
	}
	public String getEntry_user() {
		return entry_user;
	}
	public void setEntry_user(String entry_user) {
		this.entry_user = entry_user;
	}
	
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	public String getRemBank() {
		return remBank;
	}
	public void setRemBank(String remBank) {
		this.remBank = remBank;
	}
	public TransationMonitorPojo(Date tran_date, String sequence_unique_id, String tran_audit_number, String bob_account,
			String msg_type, BigDecimal tran_amount, String tran_currency, String reason, String tran_status) {
		super();
		this.tran_date = tran_date;
		this.sequence_unique_id = sequence_unique_id;
		this.tran_audit_number = tran_audit_number;
		this.bob_account = bob_account;
		this.msg_type = msg_type;
		this.tran_amount = tran_amount;
		this.tran_currency = tran_currency;
		this.reason = reason;
		this.tran_status = tran_status;
	}
	
	public TransationMonitorPojo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getInitBank() {
		return initBank;
	}
	public void setInitBank(String initBank) {
		this.initBank = initBank;
	}
	public String getReservedField1() {
		return reservedField1;
	}
	public void setReservedField1(String reservedField1) {
		this.reservedField1 = reservedField1;
	}
	
	
	
}
