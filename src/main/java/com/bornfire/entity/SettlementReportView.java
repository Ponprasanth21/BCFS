package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettlementReportView {
	private String bobMsgID;
	private String ipsxMsgID;
	private String msgType;
	private String remitterAcctNo;
	private String benAcctNo;
	private String tranCurrency;
	private BigDecimal tranAmt;
	private String totTranAmt;
	private String tranStatus;
	private String tran_type;
	private String bob_account_name;
	private String ipsx_account_name;
	private String tran_currency;
	private Date tran_date;
	
	
	
	
	
	
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
	
	
	public String getIpsx_account_name() {
		return ipsx_account_name;
	}
	public void setIpsx_account_name(String ipsx_account_name) {
		this.ipsx_account_name = ipsx_account_name;
	}
	public String getTran_currency() {
		return tran_currency;
	}
	public void setTran_currency(String tran_currency) {
		this.tran_currency = tran_currency;
	}
	public Date getTran_date() {
		return tran_date;
	}
	public void setTran_date(Date tran_date) {
		this.tran_date = tran_date;
	}
	public String getBobMsgID() {
		return bobMsgID;
	}
	public void setBobMsgID(String bobMsgID) {
		this.bobMsgID = bobMsgID;
	}
	public String getIpsxMsgID() {
		return ipsxMsgID;
	}
	public void setIpsxMsgID(String ipsxMsgID) {
		this.ipsxMsgID = ipsxMsgID;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getRemitterAcctNo() {
		return remitterAcctNo;
	}
	public void setRemitterAcctNo(String remitterAcctNo) {
		this.remitterAcctNo = remitterAcctNo;
	}
	public String getBenAcctNo() {
		return benAcctNo;
	}
	public void setBenAcctNo(String benAcctNo) {
		this.benAcctNo = benAcctNo;
	}
	public String getTranCurrency() {
		return tranCurrency;
	}
	public void setTranCurrency(String tranCurrency) {
		this.tranCurrency = tranCurrency;
	}
	public BigDecimal getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(BigDecimal tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getTotTranAmt() {
		return totTranAmt;
	}
	public void setTotTranAmt(String totTranAmt) {
		this.totTranAmt = totTranAmt;
	}
	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
		}
}
