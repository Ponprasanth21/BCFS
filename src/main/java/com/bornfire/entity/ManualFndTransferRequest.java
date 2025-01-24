package com.bornfire.entity;

public class ManualFndTransferRequest {

	private String RemitterName;
	private String RemitterAcctNumber;
	private String BeneficiaryName;
	private String BeneficiaryAcctNumber;
	private String BeneficiaryBankCode;
	private String TrAmt;
	private String CurrencyCode;
	private String TrRmks;
	private String ReqUniqueID;
	
	public String getBeneficiaryName() {
		return BeneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		BeneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryAcctNumber() {
		return BeneficiaryAcctNumber;
	}
	public void setBeneficiaryAcctNumber(String beneficiaryAcctNumber) {
		BeneficiaryAcctNumber = beneficiaryAcctNumber;
	}
	public String getBeneficiaryBankCode() {
		return BeneficiaryBankCode;
	}
	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		BeneficiaryBankCode = beneficiaryBankCode;
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
	public ManualFndTransferRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRemitterName() {
		return RemitterName;
	}
	public void setRemitterName(String remitterName) {
		RemitterName = remitterName;
	}
	public String getRemitterAcctNumber() {
		return RemitterAcctNumber;
	}
	public void setRemitterAcctNumber(String remitterAcctNumber) {
		RemitterAcctNumber = remitterAcctNumber;
	}
	
	
	public String getTrRmks() {
		return TrRmks;
	}
	public void setTrRmks(String trRmks) {
		TrRmks = trRmks;
	}
	
	public String getReqUniqueID() {
		return ReqUniqueID;
	}
	public void setReqUniqueID(String reqUniqueID) {
		ReqUniqueID = reqUniqueID;
	}
	public ManualFndTransferRequest(String remitterName, String remitterAcctNumber, String beneficiaryName,
			String beneficiaryAcctNumber, String beneficiaryBankCode, String trAmt, String currencyCode) {
		super();
		RemitterName = remitterName;
		RemitterAcctNumber = remitterAcctNumber;
		BeneficiaryName = beneficiaryName;
		BeneficiaryAcctNumber = beneficiaryAcctNumber;
		BeneficiaryBankCode = beneficiaryBankCode;
		TrAmt = trAmt;
		CurrencyCode = currencyCode;
	}
	
	
}
