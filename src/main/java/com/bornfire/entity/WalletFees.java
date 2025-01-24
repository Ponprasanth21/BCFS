package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name="WALLET_FEES_CHARGES_PARM")
public class WalletFees {

	@Id
	private String	FEE_SRL_NO;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	FEE_DATE;
	private String	WALLET_TRAN_TYPE;
	private String TRAN_CATEGORY;
	private String FEE_TYPE;
	private String FEE_PERCENTAGE;
	private BigDecimal	FEE_FIXED;
	private String	FEE_LOGIC;
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
	public String getFEE_SRL_NO() {
		return FEE_SRL_NO;
	}
	public void setFEE_SRL_NO(String fEE_SRL_NO) {
		FEE_SRL_NO = fEE_SRL_NO;
	}
	
	public Date getFEE_DATE() {
		return FEE_DATE;
	}
	public void setFEE_DATE(Date fEE_DATE) {
		FEE_DATE = fEE_DATE;
	}
	public String getWALLET_TRAN_TYPE() {
		return WALLET_TRAN_TYPE;
	}
	public void setWALLET_TRAN_TYPE(String wALLET_TRAN_TYPE) {
		WALLET_TRAN_TYPE = wALLET_TRAN_TYPE;
	}
	public String getTRAN_CATEGORY() {
		return TRAN_CATEGORY;
	}
	public void setTRAN_CATEGORY(String tRAN_CATEGORY) {
		TRAN_CATEGORY = tRAN_CATEGORY;
	}
	public String getFEE_TYPE() {
		return FEE_TYPE;
	}
	public void setFEE_TYPE(String fEE_TYPE) {
		FEE_TYPE = fEE_TYPE;
	}
	public String getFEE_PERCENTAGE() {
		return FEE_PERCENTAGE;
	}
	public void setFEE_PERCENTAGE(String fEE_PERCENTAGE) {
		FEE_PERCENTAGE = fEE_PERCENTAGE;
	}
	public BigDecimal getFEE_FIXED() {
		return FEE_FIXED;
	}
	public void setFEE_FIXED(BigDecimal fEE_FIXED) {
		FEE_FIXED = fEE_FIXED;
	}
	public String getFEE_LOGIC() {
		return FEE_LOGIC;
	}
	public void setFEE_LOGIC(String fEE_LOGIC) {
		FEE_LOGIC = fEE_LOGIC;
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
		return "WalletFees [FEE_SRL_NO=" + FEE_SRL_NO + ", FEE_DATE=" + FEE_DATE + ", WALLET_TRAN_TYPE=" + WALLET_TRAN_TYPE
				+ ", TRAN_CATEGORY=" + TRAN_CATEGORY + ", FEE_TYPE=" + FEE_TYPE + ", FEE_PERCENTAGE=" + FEE_PERCENTAGE
				+ ", FEE_FIXED=" + FEE_FIXED + ", FEE_LOGIC=" + FEE_LOGIC + ", START_DATE=" + START_DATE + ", END_DATE="
				+ END_DATE + ", ENTITY_FLG=" + ENTITY_FLG + ", DEL_FLG=" + DEL_FLG + ", ENTRY_TIME=" + ENTRY_TIME
				+ ", MODIFY_TIME=" + MODIFY_TIME + ", VERIFY_TIME=" + VERIFY_TIME + ", ENTRY_USER=" + ENTRY_USER
				+ ", MODIFY_USER=" + MODIFY_USER + ", VERIFY_USER=" + VERIFY_USER + "]";
	}

	
	
	
	
	
	
	}

	
	
	

