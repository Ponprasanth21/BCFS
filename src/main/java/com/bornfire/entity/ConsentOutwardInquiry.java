package com.bornfire.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BIPS_CONSENT_OUTWARD_INQUIRY_TABLE")
public class ConsentOutwardInquiry {

	@Id
	private String	X_REQUEST_ID;
	private String	CONSENT_ID;
	private String	ACCOUNT_ID;
	private String	INQUIRY_TYPE;
	private String	SENDERPARTICIPANT_BIC;
	private String	SENDERPARTICIPANT_MEMBERID;
	private String	RECEIVERPARTICIPANT_BIC;
	private String	RECEIVERPARTICIPANT_MEMBERID;
	private String	PSU_DEVICE_ID;
	private String	PSU_IP_ADDRESS;
	private String	PSU_ID;
	private String	PSU_ID_COUNTRY;
	private String	PSU_ID_TYPE;
	private String	STATUS;
	private String	STATUS_ERROR;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	ENTRY_TIME;
	private String	ENTRY_USER;
	private String	DEL_USER;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	DEL_TIME;
	private String	DEL_FLG;
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
	public String getACCOUNT_ID() {
		return ACCOUNT_ID;
	}
	public void setACCOUNT_ID(String aCCOUNT_ID) {
		ACCOUNT_ID = aCCOUNT_ID;
	}
	public String getINQUIRY_TYPE() {
		return INQUIRY_TYPE;
	}
	public void setINQUIRY_TYPE(String iNQUIRY_TYPE) {
		INQUIRY_TYPE = iNQUIRY_TYPE;
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
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getSTATUS_ERROR() {
		return STATUS_ERROR;
	}
	public void setSTATUS_ERROR(String sTATUS_ERROR) {
		STATUS_ERROR = sTATUS_ERROR;
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
	public ConsentOutwardInquiry(String x_REQUEST_ID, String cONSENT_ID, String aCCOUNT_ID, String iNQUIRY_TYPE,
			String sENDERPARTICIPANT_BIC, String sENDERPARTICIPANT_MEMBERID, String rECEIVERPARTICIPANT_BIC,
			String rECEIVERPARTICIPANT_MEMBERID, String pSU_DEVICE_ID, String pSU_IP_ADDRESS, String pSU_ID,
			String pSU_ID_COUNTRY, String pSU_ID_TYPE, String sTATUS, String sTATUS_ERROR, Date eNTRY_TIME,
			String eNTRY_USER, String dEL_USER, Date dEL_TIME, String dEL_FLG) {
		super();
		X_REQUEST_ID = x_REQUEST_ID;
		CONSENT_ID = cONSENT_ID;
		ACCOUNT_ID = aCCOUNT_ID;
		INQUIRY_TYPE = iNQUIRY_TYPE;
		SENDERPARTICIPANT_BIC = sENDERPARTICIPANT_BIC;
		SENDERPARTICIPANT_MEMBERID = sENDERPARTICIPANT_MEMBERID;
		RECEIVERPARTICIPANT_BIC = rECEIVERPARTICIPANT_BIC;
		RECEIVERPARTICIPANT_MEMBERID = rECEIVERPARTICIPANT_MEMBERID;
		PSU_DEVICE_ID = pSU_DEVICE_ID;
		PSU_IP_ADDRESS = pSU_IP_ADDRESS;
		PSU_ID = pSU_ID;
		PSU_ID_COUNTRY = pSU_ID_COUNTRY;
		PSU_ID_TYPE = pSU_ID_TYPE;
		STATUS = sTATUS;
		STATUS_ERROR = sTATUS_ERROR;
		ENTRY_TIME = eNTRY_TIME;
		ENTRY_USER = eNTRY_USER;
		DEL_USER = dEL_USER;
		DEL_TIME = dEL_TIME;
		DEL_FLG = dEL_FLG;
	}
	public ConsentOutwardInquiry() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
