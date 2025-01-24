package com.bornfire.entity;


public class BenAccount {
	
	
	private String ReqUniqueId;
	
	private String BenName;
	
	
	private String BenAcctNumber;
	

	private String TrAmt;
	
	private String CurrencyCode;
	
	private String TrRmks;
	
	private String BankCode;

	public String getReqUniqueId() {
		return ReqUniqueId;
	}

	public void setReqUniqueId(String reqUniqueId) {
		ReqUniqueId = reqUniqueId;
	}

	public String getBenName() {
		return BenName;
	}

	public void setBenName(String benName) {
		BenName = benName;
	}

	public String getBenAcctNumber() {
		return BenAcctNumber;
	}

	public void setBenAcctNumber(String benAcctNumber) {
		BenAcctNumber = benAcctNumber;
	}

	public String getTrAmt() {
		return TrAmt;
	}

	public void setTrAmt(String trAmt) {
		TrAmt = trAmt;
	}

	public String getCurrencyCode() {
		return CurrencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}

	public String getTrRmks() {
		return TrRmks;
	}

	public void setTrRmks(String trRmks) {
		TrRmks = trRmks;
	}

	public String getBankCode() {
		return BankCode;
	}

	public void setBankCode(String bankCode) {
		BankCode = bankCode;
	}

	@Override
	public String toString() {
		return "BenAccount [ReqUniqueId=" + ReqUniqueId + ", BenName=" + BenName + ", BenAcctNumber=" + BenAcctNumber
				+ ", TrAmt=" + TrAmt + ", CurrencyCode=" + CurrencyCode + ", TrRmks=" + TrRmks + ", BankCode="
				+ BankCode + "]";
	}

	
	
	
}
