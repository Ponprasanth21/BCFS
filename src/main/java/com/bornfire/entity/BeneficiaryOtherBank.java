package com.bornfire.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

//@Entity
//@Table(name="BENEFICIARY_DETAILS_TABLE")
//@DynamicUpdate
public class BeneficiaryOtherBank implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Id  
	
	private String  seq_num;
    private String  user_id;
	private String  user_account_number;
	private String	beneficiary_name;
    private String	beneficiary_account_number;
	private String	beneficiary_bank_name;
	private String	beneficiary_branch_name;
	private String	beneficiary_nick_name;
	
	private Date	entity_cre_flg;
	private String	entity_cre_date;
	private String  beneficiary_ifsc_code;
    private String  del_flg;
    private String  beneficiary_bank_code;
    private String  account_type;





;
	
	
	
	public BeneficiaryOtherBank() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSeq_num() {
		return seq_num;
	}
	public void setSeq_num(String seq_num) {
		this.seq_num = seq_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_account_number() {
		return user_account_number;
	}
	public void setUser_account_number(String user_account_number) {
		this.user_account_number = user_account_number;
	}
	public String getBeneficiary_name() {
		return beneficiary_name;
	}
	public void setBeneficiary_name(String beneficiary_name) {
		this.beneficiary_name = beneficiary_name;
	}
	public String getBeneficiary_account_number() {
		return beneficiary_account_number;
	}
	public void setBeneficiary_account_number(String beneficiary_account_number) {
		this.beneficiary_account_number = beneficiary_account_number;
	}
	public String getBeneficiary_bank_name() {
		return beneficiary_bank_name;
	}
	public void setBeneficiary_bank_name(String beneficiary_bank_name) {
		this.beneficiary_bank_name = beneficiary_bank_name;
	}
	public String getBeneficiary_branch_name() {
		return beneficiary_branch_name;
	}
	public void setBeneficiary_branch_name(String beneficiary_branch_name) {
		this.beneficiary_branch_name = beneficiary_branch_name;
	}
	public String getBeneficiary_nick_name() {
		return beneficiary_nick_name;
	}
	public void setBeneficiary_nick_name(String beneficiary_nick_name) {
		this.beneficiary_nick_name = beneficiary_nick_name;
	}
	public Date getEntity_cre_flg() {
		return entity_cre_flg;
	}
	public void setEntity_cre_flg(Date entity_cre_flg) {
		this.entity_cre_flg = entity_cre_flg;
	}
	public String getEntity_cre_date() {
		return entity_cre_date;
	}
	public void setEntity_cre_date(String entity_cre_date) {
		this.entity_cre_date = entity_cre_date;
	}

	public String getBeneficiary_ifsc_code() {
		return beneficiary_ifsc_code;
	}
	public void setBeneficiary_ifsc_code(String beneficiary_ifsc_code) {
		this.beneficiary_ifsc_code = beneficiary_ifsc_code;
	}
	
	
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getBeneficiary_bank_code() {
		return beneficiary_bank_code;
	}
	public void setBeneficiary_bank_code(String beneficiary_bank_code) {
		this.beneficiary_bank_code = beneficiary_bank_code;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	/*
	 * public BeneficiaryOtherBank(String user_id, String user_account_number,
	 * String beneficiary_name, String beneficiary_account_number, String
	 * beneficiary_bank_name, String beneficiary_branch_name, String
	 * beneficiary_nick_name, Date entity_cre_flg, String entity_cre_date,
	 * BigDecimal del_flg) { super(); this.user_id = user_id;
	 * this.user_account_number = user_account_number; this.beneficiary_name =
	 * beneficiary_name; this.beneficiary_account_number =
	 * beneficiary_account_number; this.beneficiary_bank_name =
	 * beneficiary_bank_name; this.beneficiary_branch_name =
	 * beneficiary_branch_name; this.beneficiary_nick_name = beneficiary_nick_name;
	 * this.entity_cre_flg = entity_cre_flg; this.entity_cre_date = entity_cre_date;
	 * this.del_flg = del_flg; }
	 */
	public BeneficiaryOtherBank(String beneficiary_name, String beneficiary_account_number,String beneficiary_nick_name) {
		this.beneficiary_name = beneficiary_name;
		this.beneficiary_account_number = beneficiary_account_number;
		this.beneficiary_nick_name = beneficiary_nick_name;
		
	}
	public BeneficiaryOtherBank(String beneficiary_account_number, String beneficiary_name,String beneficiary_bank_name, String beneficiary_ifsc_code,String beneficiary_nick_name) 
	{
		this.beneficiary_name = beneficiary_name;
		this.beneficiary_account_number = beneficiary_account_number;
		this.beneficiary_bank_name = beneficiary_bank_name;
		this.beneficiary_ifsc_code = beneficiary_ifsc_code;
		this.beneficiary_nick_name = beneficiary_nick_name;
	}
	
	/*
	 * public BeneficiaryOtherBank(String user_id,String beneficiary_name) {
	 * this.user_id = user_id; this.beneficiary_name = beneficiary_name; }
	 */
	
	
	
}