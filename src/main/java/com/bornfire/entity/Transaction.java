package com.bornfire.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TRANS_DETAILS_HISTORY")
/* @SecondaryTable(name="BENEFICIARY_DETAILS_TABLE") */

public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
	private String tran_status;
	private String acct_num;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date tran_date;
	private String tran_ref_no;
	private BigDecimal part_tran_id;
	private String part_tran_type;
	private BigDecimal tran_amt;
	private String beneficiary_name;



	

	public String getTran_status() {
		return tran_status;
	}

	public void setTran_status(String tran_status) {
		this.tran_status = tran_status;
	}

	public String getTran_ref_no() {
		return tran_ref_no;
	}

	public void setTran_ref_no(String tran_ref_no) {
		this.tran_ref_no = tran_ref_no;
	}

	public BigDecimal getPart_tran_id() {
		return part_tran_id;
	}

	public void setPart_tran_id(BigDecimal part_tran_id) {
		this.part_tran_id = part_tran_id;
	}

	public String getPart_tran_type() {
		return part_tran_type;
	}

	public void setPart_tran_type(String part_tran_type) {
		this.part_tran_type = part_tran_type;
	}

	public BigDecimal getTran_amt() {
		return tran_amt;
	}

	public void setTran_amt(BigDecimal tran_amt) {
		this.tran_amt = tran_amt;
	}

	public String getAcct_num() {
		return acct_num;
	}

	public void setAcct_num(String acct_num) {
		this.acct_num = acct_num;
	}

	public Date getTran_date() {
		return tran_date;
	}

	public void setTran_date(Date tran_date) {
		this.tran_date = tran_date;
	}
	
	/*
	 * public String getBeneficiary_name() { return beneficiary_name; }
	 * 
	 * public void setBeneficiary_name(String beneficiary_name) {
	 * this.beneficiary_name = beneficiary_name; }
	 */

	public String getBeneficiary_name() {
		return beneficiary_name;
	}

	public void setBeneficiary_name(String beneficiary_name) {
		this.beneficiary_name = beneficiary_name;
	}

	public Transaction(String beneficiary_name,String acct_num, Date tran_date, BigDecimal tran_amt) {
		this.beneficiary_name=beneficiary_name; 
		this.acct_num = acct_num;
		this.tran_date = tran_date;
		this.tran_amt = tran_amt;
	}


	
}