package com.bornfire.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="BIPS_WALLET_MASTER")
public class WalletMasterTable {
	private String acct_cat;
	private BigDecimal acct_limit;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date acct_opn_date;
	private String open_remarks;
	private String bips_acct_no;
	
	@Id
	private String acct_no;
	private String acct_name;
	private String acct_crncy;
	private BigDecimal acct_balance;
	private BigDecimal cum_dr_amt;
	private BigDecimal cum_cr_amt;
	private BigDecimal mobile_no;
	private String email_id;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date last_tran_date;
	private BigDecimal last_tran_amt;
	private String wallet_cat;
	private String cust_id;
	private String wallet_type;
	private BigDecimal deb_limit;;
	private BigDecimal cust_limit;
	private BigDecimal wallet_limit;
	private String entity_flg;
	private String del_flg;
	private String modify_flg;
	private String entry_user;
	private String modify_user;
	private String verify_user;
	private Date entry_time;
	private Date modify_time;
	private Date verify_time;
	public String getAcct_cat() {
		return acct_cat;
	}
	public void setAcct_cat(String acct_cat) {
		this.acct_cat = acct_cat;
	}
	public BigDecimal getAcct_limit() {
		return acct_limit;
	}
	public void setAcct_limit(BigDecimal acct_limit) {
		this.acct_limit = acct_limit;
	}
	public Date getAcct_opn_date() {
		return acct_opn_date;
	}
	public void setAcct_opn_date(Date acct_opn_date) {
		this.acct_opn_date = acct_opn_date;
	}
	public String getOpen_remarks() {
		return open_remarks;
	}
	public void setOpen_remarks(String open_remarks) {
		this.open_remarks = open_remarks;
	}
	public String getBips_acct_no() {
		return bips_acct_no;
	}
	public void setBips_acct_no(String bips_acct_no) {
		this.bips_acct_no = bips_acct_no;
	}
	public String getAcct_no() {
		return acct_no;
	}
	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	public String getAcct_crncy() {
		return acct_crncy;
	}
	public void setAcct_crncy(String acct_crncy) {
		this.acct_crncy = acct_crncy;
	}
	public BigDecimal getAcct_balance() {
		return acct_balance;
	}
	public void setAcct_balance(BigDecimal acct_balance) {
		this.acct_balance = acct_balance;
	}
	public BigDecimal getCum_dr_amt() {
		return cum_dr_amt;
	}
	public void setCum_dr_amt(BigDecimal cum_dr_amt) {
		this.cum_dr_amt = cum_dr_amt;
	}
	public BigDecimal getCum_cr_amt() {
		return cum_cr_amt;
	}
	public void setCum_cr_amt(BigDecimal cum_cr_amt) {
		this.cum_cr_amt = cum_cr_amt;
	}
	public BigDecimal getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(BigDecimal mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public Date getLast_tran_date() {
		return last_tran_date;
	}
	public void setLast_tran_date(Date last_tran_date) {
		this.last_tran_date = last_tran_date;
	}
	public BigDecimal getLast_tran_amt() {
		return last_tran_amt;
	}
	public void setLast_tran_amt(BigDecimal last_tran_amt) {
		this.last_tran_amt = last_tran_amt;
	}
	public String getEntity_flg() {
		return entity_flg;
	}
	public void setEntity_flg(String entity_flg) {
		this.entity_flg = entity_flg;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
	public String getModify_flg() {
		return modify_flg;
	}
	public void setModify_flg(String modify_flg) {
		this.modify_flg = modify_flg;
	}
	public String getEntry_user() {
		return entry_user;
	}
	public void setEntry_user(String entry_user) {
		this.entry_user = entry_user;
	}
	public String getModify_user() {
		return modify_user;
	}
	public void setModify_user(String modify_user) {
		this.modify_user = modify_user;
	}
	public String getVerify_user() {
		return verify_user;
	}
	public void setVerify_user(String verify_user) {
		this.verify_user = verify_user;
	}
	public Date getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public Date getVerify_time() {
		return verify_time;
	}
	public void setVerify_time(Date verify_time) {
		this.verify_time = verify_time;
	}
	public String getWallet_cat() {
		return wallet_cat;
	}
	public void setWallet_cat(String wallet_cat) {
		this.wallet_cat = wallet_cat;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getWallet_type() {
		return wallet_type;
	}
	public void setWallet_type(String wallet_type) {
		this.wallet_type = wallet_type;
	}
	public BigDecimal getDeb_limit() {
		return deb_limit;
	}
	public void setDeb_limit(BigDecimal deb_limit) {
		this.deb_limit = deb_limit;
	}
	public BigDecimal getCust_limit() {
		return cust_limit;
	}
	public void setCust_limit(BigDecimal cust_limit) {
		this.cust_limit = cust_limit;
	}
	public BigDecimal getWallet_limit() {
		return wallet_limit;
	}
	public void setWallet_limit(BigDecimal wallet_limit) {
		this.wallet_limit = wallet_limit;
	}
	
	
	
	}
