package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="NOTIFICATION_PARM_MASTER")
public class NotificationParms {

	@Id
	private String	RECORD_SRL_NO;
	//@DateTimeFormat(pattern = "dd-MM-yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date	RECORD_DATE;
	private String TRAN_CATEGORY;
	private String	NOTIFICATION_EVENT_NO;
	private String	NOTIFICATION_EVENT_DESC;
	private BigDecimal	NOTIFICATION_LIMIT;
	private String	NOTIFICATION_USER_1;
	private String	NOTIFICATION_USER_2;
	private String	NOTIFICATION_USER_3;
	private String	NOTIFICATION_SMS_FLG;
	private BigDecimal	NOTIFICATION_MOBILE_1;
	private BigDecimal	NOTIFICATION_MOBILE_2;
	private BigDecimal	NOTIFICATION_MOBILE_3;
	private String	NOTIFICATION_EMAIL_FLG;
	private String	NOTIFICATION_EMAIL_1;
	private String	NOTIFICATION_EMAIL_2;
	private String	NOTIFICATION_EMAIL_3;
	private String	ALERT_FLG;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	START_DATE;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	END_DATE;
	private String	ENTITY_FLG;
	private String	DEL_FLG;
	private Date	ENTRY_TIME;
	private Date	MODIFY_TIME;
	private Date	VERIFY_TIME;
	private String	ENTRY_USER;
	private String	MODIFY_USER;
	private String	VERIFY_USER;
	public String getRECORD_SRL_NO() {
		return RECORD_SRL_NO;
	}
	public void setRECORD_SRL_NO(String rECORD_SRL_NO) {
		RECORD_SRL_NO = rECORD_SRL_NO;
	}
	public Date getRECORD_DATE() {
		return RECORD_DATE;
	}
	public void setRECORD_DATE(Date rECORD_DATE) {
		RECORD_DATE = rECORD_DATE;
	}
	public String getTRAN_CATEGORY() {
		return TRAN_CATEGORY;
	}
	public void setTRAN_CATEGORY(String tRAN_CATEGORY) {
		TRAN_CATEGORY = tRAN_CATEGORY;
	}
	public String getNOTIFICATION_EVENT_NO() {
		return NOTIFICATION_EVENT_NO;
	}
	public void setNOTIFICATION_EVENT_NO(String nOTIFICATION_EVENT_NO) {
		NOTIFICATION_EVENT_NO = nOTIFICATION_EVENT_NO;
	}
	public String getNOTIFICATION_EVENT_DESC() {
		return NOTIFICATION_EVENT_DESC;
	}
	public void setNOTIFICATION_EVENT_DESC(String nOTIFICATION_EVENT_DESC) {
		NOTIFICATION_EVENT_DESC = nOTIFICATION_EVENT_DESC;
	}
	public BigDecimal getNOTIFICATION_LIMIT() {
		return NOTIFICATION_LIMIT;
	}
	public void setNOTIFICATION_LIMIT(BigDecimal nOTIFICATION_LIMIT) {
		NOTIFICATION_LIMIT = nOTIFICATION_LIMIT;
	}
	public String getNOTIFICATION_USER_1() {
		return NOTIFICATION_USER_1;
	}
	public void setNOTIFICATION_USER_1(String nOTIFICATION_USER_1) {
		NOTIFICATION_USER_1 = nOTIFICATION_USER_1;
	}
	public String getNOTIFICATION_USER_2() {
		return NOTIFICATION_USER_2;
	}
	public void setNOTIFICATION_USER_2(String nOTIFICATION_USER_2) {
		NOTIFICATION_USER_2 = nOTIFICATION_USER_2;
	}
	public String getNOTIFICATION_USER_3() {
		return NOTIFICATION_USER_3;
	}
	public void setNOTIFICATION_USER_3(String nOTIFICATION_USER_3) {
		NOTIFICATION_USER_3 = nOTIFICATION_USER_3;
	}
	public String getNOTIFICATION_SMS_FLG() {
		return NOTIFICATION_SMS_FLG;
	}
	public void setNOTIFICATION_SMS_FLG(String nOTIFICATION_SMS_FLG) {
		NOTIFICATION_SMS_FLG = nOTIFICATION_SMS_FLG;
	}
	public BigDecimal getNOTIFICATION_MOBILE_1() {
		return NOTIFICATION_MOBILE_1;
	}
	public void setNOTIFICATION_MOBILE_1(BigDecimal nOTIFICATION_MOBILE_1) {
		NOTIFICATION_MOBILE_1 = nOTIFICATION_MOBILE_1;
	}
	public BigDecimal getNOTIFICATION_MOBILE_2() {
		return NOTIFICATION_MOBILE_2;
	}
	public void setNOTIFICATION_MOBILE_2(BigDecimal nOTIFICATION_MOBILE_2) {
		NOTIFICATION_MOBILE_2 = nOTIFICATION_MOBILE_2;
	}
	public BigDecimal getNOTIFICATION_MOBILE_3() {
		return NOTIFICATION_MOBILE_3;
	}
	public void setNOTIFICATION_MOBILE_3(BigDecimal nOTIFICATION_MOBILE_3) {
		NOTIFICATION_MOBILE_3 = nOTIFICATION_MOBILE_3;
	}
	public String getNOTIFICATION_EMAIL_FLG() {
		return NOTIFICATION_EMAIL_FLG;
	}
	public void setNOTIFICATION_EMAIL_FLG(String nOTIFICATION_EMAIL_FLG) {
		NOTIFICATION_EMAIL_FLG = nOTIFICATION_EMAIL_FLG;
	}
	public String getNOTIFICATION_EMAIL_1() {
		return NOTIFICATION_EMAIL_1;
	}
	public void setNOTIFICATION_EMAIL_1(String nOTIFICATION_EMAIL_1) {
		NOTIFICATION_EMAIL_1 = nOTIFICATION_EMAIL_1;
	}
	public String getNOTIFICATION_EMAIL_2() {
		return NOTIFICATION_EMAIL_2;
	}
	public void setNOTIFICATION_EMAIL_2(String nOTIFICATION_EMAIL_2) {
		NOTIFICATION_EMAIL_2 = nOTIFICATION_EMAIL_2;
	}
	public String getNOTIFICATION_EMAIL_3() {
		return NOTIFICATION_EMAIL_3;
	}
	public void setNOTIFICATION_EMAIL_3(String nOTIFICATION_EMAIL_3) {
		NOTIFICATION_EMAIL_3 = nOTIFICATION_EMAIL_3;
	}
	public String getALERT_FLG() {
		return ALERT_FLG;
	}
	public void setALERT_FLG(String aLERT_FLG) {
		ALERT_FLG = aLERT_FLG;
	}
	public Date getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(Date sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public Date getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(Date eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getENTITY_FLG() {
		return ENTITY_FLG;
	}
	public void setENTITY_FLG(String eNTITY_FLG) {
		ENTITY_FLG = eNTITY_FLG;
	}
	public String getDEL_FLG() {
		return DEL_FLG;
	}
	public void setDEL_FLG(String dEL_FLG) {
		DEL_FLG = dEL_FLG;
	}
	public Date getENTRY_TIME() {
		return ENTRY_TIME;
	}
	public void setENTRY_TIME(Date eNTRY_TIME) {
		ENTRY_TIME = eNTRY_TIME;
	}
	public Date getMODIFY_TIME() {
		return MODIFY_TIME;
	}
	public void setMODIFY_TIME(Date mODIFY_TIME) {
		MODIFY_TIME = mODIFY_TIME;
	}
	public Date getVERIFY_TIME() {
		return VERIFY_TIME;
	}
	public void setVERIFY_TIME(Date vERIFY_TIME) {
		VERIFY_TIME = vERIFY_TIME;
	}
	public String getENTRY_USER() {
		return ENTRY_USER;
	}
	public void setENTRY_USER(String eNTRY_USER) {
		ENTRY_USER = eNTRY_USER;
	}
	public String getMODIFY_USER() {
		return MODIFY_USER;
	}
	public void setMODIFY_USER(String mODIFY_USER) {
		MODIFY_USER = mODIFY_USER;
	}
	public String getVERIFY_USER() {
		return VERIFY_USER;
	}
	public void setVERIFY_USER(String vERIFY_USER) {
		VERIFY_USER = vERIFY_USER;
	}
	@Override
	public String toString() {
		return "NotificationParms [RECORD_SRL_NO=" + RECORD_SRL_NO + ", RECORD_DATE=" + RECORD_DATE + ", TRAN_CATEGORY="
				+ TRAN_CATEGORY + ", NOTIFICATION_EVENT_NO=" + NOTIFICATION_EVENT_NO + ", NOTIFICATION_EVENT_DESC="
				+ NOTIFICATION_EVENT_DESC + ", NOTIFICATION_LIMIT=" + NOTIFICATION_LIMIT + ", NOTIFICATION_USER_1="
				+ NOTIFICATION_USER_1 + ", NOTIFICATION_USER_2=" + NOTIFICATION_USER_2 + ", NOTIFICATION_USER_3="
				+ NOTIFICATION_USER_3 + ", NOTIFICATION_SMS_FLG=" + NOTIFICATION_SMS_FLG + ", NOTIFICATION_MOBILE_1="
				+ NOTIFICATION_MOBILE_1 + ", NOTIFICATION_MOBILE_2=" + NOTIFICATION_MOBILE_2
				+ ", NOTIFICATION_MOBILE_3=" + NOTIFICATION_MOBILE_3 + ", NOTIFICATION_EMAIL_FLG="
				+ NOTIFICATION_EMAIL_FLG + ", NOTIFICATION_EMAIL_1=" + NOTIFICATION_EMAIL_1 + ", NOTIFICATION_EMAIL_2="
				+ NOTIFICATION_EMAIL_2 + ", NOTIFICATION_EMAIL_3=" + NOTIFICATION_EMAIL_3 + ", ALERT_FLG=" + ALERT_FLG
				+ ", START_DATE=" + START_DATE + ", END_DATE=" + END_DATE + ", ENTITY_FLG=" + ENTITY_FLG + ", DEL_FLG="
				+ DEL_FLG + ", ENTRY_TIME=" + ENTRY_TIME + ", MODIFY_TIME=" + MODIFY_TIME + ", VERIFY_TIME="
				+ VERIFY_TIME + ", ENTRY_USER=" + ENTRY_USER + ", MODIFY_USER=" + MODIFY_USER + ", VERIFY_USER="
				+ VERIFY_USER + "]";
	}
	

	
	
	
}
