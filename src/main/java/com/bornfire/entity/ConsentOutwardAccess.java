package com.bornfire.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BIPS_CONSENT_OUTWARD_ACCESS_TABLE")
public class ConsentOutwardAccess {

	private String	X_REQUEST_ID;
	@Id
	private String	CONSENT_ID;
	private String	AUTH_ID;
	private String	SENDERPARTICIPANT_BIC;
	private String	SENDERPARTICIPANT_MEMBERID;
	private String	RECEIVERPARTICIPANT_BIC;
	private String	RECEIVERPARTICIPANT_MEMBERID;
	private String	PSU_DEVICE_ID;
	private String	PSU_IP_ADDRESS;
	private String	PSU_ID;
	private String	PSU_ID_COUNTRY;
	private String	PSU_ID_TYPE;
	private String	PHONE_NUMBER;
	private String	PUBLIC_KEY;
	private String	SCHM_NAME;
	private String	IDENTIFICATION;
	private String	PERMISSION;
	private String	READ_BALANCE;
	private String	READ_TRAN_DETAILS;
	private String	READ_ACCT_DETAILS;
	private String	READ_DEBIT_ACCT;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	EXPIRE_DATE;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	TRAN_FROM_DATE;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	TRAN_TO_DATE;
	private String	STATUS;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	STATUS_UPDATE_DATE;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	CREATE_DATE;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	ENTRY_TIME;
	private String	ENTRY_USER;
	private String	DEL_USER;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	DEL_TIME;
	private String	DEL_FLG;
	private String	ACCOUNT_STATUS;
	private String CUSTOM_DEVICE_ID;
	private String PRIVATE_KEY;
	public String getX_REQUEST_ID() {
		return X_REQUEST_ID;
	}
	public void setX_REQUEST_ID(String x_REQUEST_ID) {
		X_REQUEST_ID = x_REQUEST_ID;
	}
	public String getCONSENT_ID() {
		return CONSENT_ID;
	}
	public void setCONSENT_ID(String cONSENT_ID) {
		CONSENT_ID = cONSENT_ID;
	}
	public String getAUTH_ID() {
		return AUTH_ID;
	}
	public void setAUTH_ID(String aUTH_ID) {
		AUTH_ID = aUTH_ID;
	}
	public String getSENDERPARTICIPANT_BIC() {
		return SENDERPARTICIPANT_BIC;
	}
	public void setSENDERPARTICIPANT_BIC(String sENDERPARTICIPANT_BIC) {
		SENDERPARTICIPANT_BIC = sENDERPARTICIPANT_BIC;
	}
	public String getSENDERPARTICIPANT_MEMBERID() {
		return SENDERPARTICIPANT_MEMBERID;
	}
	public void setSENDERPARTICIPANT_MEMBERID(String sENDERPARTICIPANT_MEMBERID) {
		SENDERPARTICIPANT_MEMBERID = sENDERPARTICIPANT_MEMBERID;
	}
	public String getRECEIVERPARTICIPANT_BIC() {
		return RECEIVERPARTICIPANT_BIC;
	}
	public void setRECEIVERPARTICIPANT_BIC(String rECEIVERPARTICIPANT_BIC) {
		RECEIVERPARTICIPANT_BIC = rECEIVERPARTICIPANT_BIC;
	}
	public String getRECEIVERPARTICIPANT_MEMBERID() {
		return RECEIVERPARTICIPANT_MEMBERID;
	}
	public void setRECEIVERPARTICIPANT_MEMBERID(String rECEIVERPARTICIPANT_MEMBERID) {
		RECEIVERPARTICIPANT_MEMBERID = rECEIVERPARTICIPANT_MEMBERID;
	}
	public String getPSU_DEVICE_ID() {
		return PSU_DEVICE_ID;
	}
	public void setPSU_DEVICE_ID(String pSU_DEVICE_ID) {
		PSU_DEVICE_ID = pSU_DEVICE_ID;
	}
	public String getPSU_IP_ADDRESS() {
		return PSU_IP_ADDRESS;
	}
	public void setPSU_IP_ADDRESS(String pSU_IP_ADDRESS) {
		PSU_IP_ADDRESS = pSU_IP_ADDRESS;
	}
	public String getPSU_ID() {
		return PSU_ID;
	}
	public void setPSU_ID(String pSU_ID) {
		PSU_ID = pSU_ID;
	}
	public String getPSU_ID_COUNTRY() {
		return PSU_ID_COUNTRY;
	}
	public void setPSU_ID_COUNTRY(String pSU_ID_COUNTRY) {
		PSU_ID_COUNTRY = pSU_ID_COUNTRY;
	}
	public String getPSU_ID_TYPE() {
		return PSU_ID_TYPE;
	}
	public void setPSU_ID_TYPE(String pSU_ID_TYPE) {
		PSU_ID_TYPE = pSU_ID_TYPE;
	}
	public String getPHONE_NUMBER() {
		return PHONE_NUMBER;
	}
	public void setPHONE_NUMBER(String pHONE_NUMBER) {
		PHONE_NUMBER = pHONE_NUMBER;
	}
	public String getPUBLIC_KEY() {
		return PUBLIC_KEY;
	}
	public void setPUBLIC_KEY(String pUBLIC_KEY) {
		PUBLIC_KEY = pUBLIC_KEY;
	}
	public String getSCHM_NAME() {
		return SCHM_NAME;
	}
	public void setSCHM_NAME(String sCHM_NAME) {
		SCHM_NAME = sCHM_NAME;
	}
	public String getIDENTIFICATION() {
		return IDENTIFICATION;
	}
	public void setIDENTIFICATION(String iDENTIFICATION) {
		IDENTIFICATION = iDENTIFICATION;
	}
	public String getPERMISSION() {
		return PERMISSION;
	}
	public void setPERMISSION(String pERMISSION) {
		PERMISSION = pERMISSION;
	}
	public String getREAD_BALANCE() {
		return READ_BALANCE;
	}
	public void setREAD_BALANCE(String rEAD_BALANCE) {
		READ_BALANCE = rEAD_BALANCE;
	}
	public String getREAD_TRAN_DETAILS() {
		return READ_TRAN_DETAILS;
	}
	public void setREAD_TRAN_DETAILS(String rEAD_TRAN_DETAILS) {
		READ_TRAN_DETAILS = rEAD_TRAN_DETAILS;
	}
	public String getREAD_ACCT_DETAILS() {
		return READ_ACCT_DETAILS;
	}
	public void setREAD_ACCT_DETAILS(String rEAD_ACCT_DETAILS) {
		READ_ACCT_DETAILS = rEAD_ACCT_DETAILS;
	}
	public String getREAD_DEBIT_ACCT() {
		return READ_DEBIT_ACCT;
	}
	public void setREAD_DEBIT_ACCT(String rEAD_DEBIT_ACCT) {
		READ_DEBIT_ACCT = rEAD_DEBIT_ACCT;
	}
	public Date getEXPIRE_DATE() {
		return EXPIRE_DATE;
	}
	public void setEXPIRE_DATE(Date eXPIRE_DATE) {
		EXPIRE_DATE = eXPIRE_DATE;
	}
	public Date getTRAN_FROM_DATE() {
		return TRAN_FROM_DATE;
	}
	public void setTRAN_FROM_DATE(Date tRAN_FROM_DATE) {
		TRAN_FROM_DATE = tRAN_FROM_DATE;
	}
	public Date getTRAN_TO_DATE() {
		return TRAN_TO_DATE;
	}
	public void setTRAN_TO_DATE(Date tRAN_TO_DATE) {
		TRAN_TO_DATE = tRAN_TO_DATE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public Date getSTATUS_UPDATE_DATE() {
		return STATUS_UPDATE_DATE;
	}
	public void setSTATUS_UPDATE_DATE(Date sTATUS_UPDATE_DATE) {
		STATUS_UPDATE_DATE = sTATUS_UPDATE_DATE;
	}
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public Date getENTRY_TIME() {
		return ENTRY_TIME;
	}
	public void setENTRY_TIME(Date eNTRY_TIME) {
		ENTRY_TIME = eNTRY_TIME;
	}
	public String getENTRY_USER() {
		return ENTRY_USER;
	}
	public void setENTRY_USER(String eNTRY_USER) {
		ENTRY_USER = eNTRY_USER;
	}
	public String getDEL_USER() {
		return DEL_USER;
	}
	public void setDEL_USER(String dEL_USER) {
		DEL_USER = dEL_USER;
	}
	public Date getDEL_TIME() {
		return DEL_TIME;
	}
	public void setDEL_TIME(Date dEL_TIME) {
		DEL_TIME = dEL_TIME;
	}
	public String getDEL_FLG() {
		return DEL_FLG;
	}
	public void setDEL_FLG(String dEL_FLG) {
		DEL_FLG = dEL_FLG;
	}
	public String getACCOUNT_STATUS() {
		return ACCOUNT_STATUS;
	}
	public void setACCOUNT_STATUS(String aCCOUNT_STATUS) {
		ACCOUNT_STATUS = aCCOUNT_STATUS;
	}


	public ConsentOutwardAccess(String x_REQUEST_ID, String cONSENT_ID, String aUTH_ID, String sENDERPARTICIPANT_BIC,
			String sENDERPARTICIPANT_MEMBERID, String rECEIVERPARTICIPANT_BIC, String rECEIVERPARTICIPANT_MEMBERID,
			String pSU_DEVICE_ID, String pSU_IP_ADDRESS, String pSU_ID, String pSU_ID_COUNTRY, String pSU_ID_TYPE,
			String pHONE_NUMBER, String pUBLIC_KEY, String sCHM_NAME, String iDENTIFICATION, String pERMISSION,
			String rEAD_BALANCE, String rEAD_TRAN_DETAILS, String rEAD_ACCT_DETAILS, String rEAD_DEBIT_ACCT,
			Date eXPIRE_DATE, Date tRAN_FROM_DATE, Date tRAN_TO_DATE, String sTATUS, Date sTATUS_UPDATE_DATE,
			Date cREATE_DATE, Date eNTRY_TIME, String eNTRY_USER, String dEL_USER, Date dEL_TIME, String dEL_FLG,
			String aCCOUNT_STATUS, String cUSTOM_DEVICE_ID, String pRIVATE_KEY) {
		super();
		X_REQUEST_ID = x_REQUEST_ID;
		CONSENT_ID = cONSENT_ID;
		AUTH_ID = aUTH_ID;
		SENDERPARTICIPANT_BIC = sENDERPARTICIPANT_BIC;
		SENDERPARTICIPANT_MEMBERID = sENDERPARTICIPANT_MEMBERID;
		RECEIVERPARTICIPANT_BIC = rECEIVERPARTICIPANT_BIC;
		RECEIVERPARTICIPANT_MEMBERID = rECEIVERPARTICIPANT_MEMBERID;
		PSU_DEVICE_ID = pSU_DEVICE_ID;
		PSU_IP_ADDRESS = pSU_IP_ADDRESS;
		PSU_ID = pSU_ID;
		PSU_ID_COUNTRY = pSU_ID_COUNTRY;
		PSU_ID_TYPE = pSU_ID_TYPE;
		PHONE_NUMBER = pHONE_NUMBER;
		PUBLIC_KEY = pUBLIC_KEY;
		SCHM_NAME = sCHM_NAME;
		IDENTIFICATION = iDENTIFICATION;
		PERMISSION = pERMISSION;
		READ_BALANCE = rEAD_BALANCE;
		READ_TRAN_DETAILS = rEAD_TRAN_DETAILS;
		READ_ACCT_DETAILS = rEAD_ACCT_DETAILS;
		READ_DEBIT_ACCT = rEAD_DEBIT_ACCT;
		EXPIRE_DATE = eXPIRE_DATE;
		TRAN_FROM_DATE = tRAN_FROM_DATE;
		TRAN_TO_DATE = tRAN_TO_DATE;
		STATUS = sTATUS;
		STATUS_UPDATE_DATE = sTATUS_UPDATE_DATE;
		CREATE_DATE = cREATE_DATE;
		ENTRY_TIME = eNTRY_TIME;
		ENTRY_USER = eNTRY_USER;
		DEL_USER = dEL_USER;
		DEL_TIME = dEL_TIME;
		DEL_FLG = dEL_FLG;
		ACCOUNT_STATUS = aCCOUNT_STATUS;
		CUSTOM_DEVICE_ID = cUSTOM_DEVICE_ID;
		PRIVATE_KEY = pRIVATE_KEY;
	}
	public ConsentOutwardAccess() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
