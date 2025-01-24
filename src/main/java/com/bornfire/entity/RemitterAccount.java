package com.bornfire.entity;


public class RemitterAccount {
	
	private String AcctName;
	
	private String AcctNumber;
	
	private String BankCode;
	
	private String CurrencyCode;
	
	public String getAcctName() {
		return AcctName;
	}
	public void setAcctName(String acctName) {
		AcctName = acctName;
	}
	public String getAcctNumber() {
		return AcctNumber;
	}
	public void setAcctNumber(String acctNumber) {
		AcctNumber = acctNumber;
	}
	
	public String getCurrencyCode() {
		return CurrencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}
	
	
	public String getBankCode() {
		return BankCode;
	}
	public void setBankCode(String bankCode) {
		BankCode = bankCode;
	}
	@Override
	public String toString() {
		return "RemitterAccount [AcctName=" + AcctName + ", AcctNumber=" + AcctNumber + ", CurrencyCode=" + CurrencyCode
				+ "]";
	}
	
	
	
}
