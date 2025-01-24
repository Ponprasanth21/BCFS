package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name="BIPS_PARTICIPANT_FEES_TABLE")
@IdClass(ParticipantFessTableID.class)
public class ParticipantFeesTable {
	@Id
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	CREATION_DATE;
	private String	PAYER_AGENT;
	private String	PAYER_NAME;
	@Id
	private String	PAYEE_AGENT;
	private String	PAYEE_NAME;
	@Id
	private String	CATEGORY_NAME;
	private BigDecimal	QUANTITY;
	private BigDecimal	TOTAL_AMT;
	private BigDecimal	INVOICE_AMT;
	private String	PAID_FLG;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	PAID_DATE;
	private String	PAID_USER;
	private String	AUTH_USER;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	AUTH_TIME;
	private String	VERIFY_USER;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	VERIFY_TIME;
	public Date getCREATION_DATE() {
		return CREATION_DATE;
	}
	public void setCREATION_DATE(Date cREATION_DATE) {
		CREATION_DATE = cREATION_DATE;
	}
	public String getPAYER_AGENT() {
		return PAYER_AGENT;
	}
	public void setPAYER_AGENT(String pAYER_AGENT) {
		PAYER_AGENT = pAYER_AGENT;
	}
	public String getPAYER_NAME() {
		return PAYER_NAME;
	}
	public void setPAYER_NAME(String pAYER_NAME) {
		PAYER_NAME = pAYER_NAME;
	}
	public String getPAYEE_AGENT() {
		return PAYEE_AGENT;
	}
	public void setPAYEE_AGENT(String pAYEE_AGENT) {
		PAYEE_AGENT = pAYEE_AGENT;
	}
	public String getPAYEE_NAME() {
		return PAYEE_NAME;
	}
	public void setPAYEE_NAME(String pAYEE_NAME) {
		PAYEE_NAME = pAYEE_NAME;
	}
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}
	public void setCATEGORY_NAME(String cATEGORY_NAME) {
		CATEGORY_NAME = cATEGORY_NAME;
	}
	public BigDecimal getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(BigDecimal qUANTITY) {
		QUANTITY = qUANTITY;
	}
	public BigDecimal getTOTAL_AMT() {
		return TOTAL_AMT;
	}
	public void setTOTAL_AMT(BigDecimal tOTAL_AMT) {
		TOTAL_AMT = tOTAL_AMT;
	}
	public BigDecimal getINVOICE_AMT() {
		return INVOICE_AMT;
	}
	public void setINVOICE_AMT(BigDecimal iNVOICE_AMT) {
		INVOICE_AMT = iNVOICE_AMT;
	}
	public String getPAID_FLG() {
		return PAID_FLG;
	}
	public void setPAID_FLG(String pAID_FLG) {
		PAID_FLG = pAID_FLG;
	}
	public Date getPAID_DATE() {
		return PAID_DATE;
	}
	public void setPAID_DATE(Date pAID_DATE) {
		PAID_DATE = pAID_DATE;
	}
	public String getPAID_USER() {
		return PAID_USER;
	}
	public void setPAID_USER(String pAID_USER) {
		PAID_USER = pAID_USER;
	}
	public String getAUTH_USER() {
		return AUTH_USER;
	}
	public void setAUTH_USER(String aUTH_USER) {
		AUTH_USER = aUTH_USER;
	}
	public Date getAUTH_TIME() {
		return AUTH_TIME;
	}
	public void setAUTH_TIME(Date aUTH_TIME) {
		AUTH_TIME = aUTH_TIME;
	}
	public String getVERIFY_USER() {
		return VERIFY_USER;
	}
	public void setVERIFY_USER(String vERIFY_USER) {
		VERIFY_USER = vERIFY_USER;
	}
	public Date getVERIFY_TIME() {
		return VERIFY_TIME;
	}
	public void setVERIFY_TIME(Date vERIFY_TIME) {
		VERIFY_TIME = vERIFY_TIME;
	}
	public ParticipantFeesTable(Date cREATION_DATE, String pAYER_AGENT, String pAYER_NAME, String pAYEE_AGENT,
			String pAYEE_NAME, String cATEGORY_NAME, BigDecimal qUANTITY, BigDecimal tOTAL_AMT, BigDecimal iNVOICE_AMT,
			String pAID_FLG, Date pAID_DATE, String pAID_USER, String aUTH_USER, Date aUTH_TIME, String vERIFY_USER,
			Date vERIFY_TIME) {
		super();
		CREATION_DATE = cREATION_DATE;
		PAYER_AGENT = pAYER_AGENT;
		PAYER_NAME = pAYER_NAME;
		PAYEE_AGENT = pAYEE_AGENT;
		PAYEE_NAME = pAYEE_NAME;
		CATEGORY_NAME = cATEGORY_NAME;
		QUANTITY = qUANTITY;
		TOTAL_AMT = tOTAL_AMT;
		INVOICE_AMT = iNVOICE_AMT;
		PAID_FLG = pAID_FLG;
		PAID_DATE = pAID_DATE;
		PAID_USER = pAID_USER;
		AUTH_USER = aUTH_USER;
		AUTH_TIME = aUTH_TIME;
		VERIFY_USER = vERIFY_USER;
		VERIFY_TIME = vERIFY_TIME;
	}
	public ParticipantFeesTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ParticipantFeesTable [CREATION_DATE=" + CREATION_DATE + ", PAYER_AGENT=" + PAYER_AGENT + ", PAYER_NAME="
				+ PAYER_NAME + ", PAYEE_AGENT=" + PAYEE_AGENT + ", PAYEE_NAME=" + PAYEE_NAME + ", CATEGORY_NAME="
				+ CATEGORY_NAME + ", QUANTITY=" + QUANTITY + ", TOTAL_AMT=" + TOTAL_AMT + ", INVOICE_AMT=" + INVOICE_AMT
				+ ", PAID_FLG=" + PAID_FLG + ", PAID_DATE=" + PAID_DATE + ", PAID_USER=" + PAID_USER + ", AUTH_USER="
				+ AUTH_USER + ", AUTH_TIME=" + AUTH_TIME + ", VERIFY_USER=" + VERIFY_USER + ", VERIFY_TIME="
				+ VERIFY_TIME + "]";
	}
	
	

	
}
